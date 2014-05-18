package ptt.dalek.main;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import ptt.dalek.github.Commit;
import ptt.dalek.github.Repository;
import ptt.dalek.github.User;
import ptt.dalek.gui.App;
import ptt.dalek.helpers.CommitHelper;
import ptt.dalek.helpers.RepositoryHelper;
import ptt.dalek.helpers.UserHelper;

public class Client {

	private static Client instance;

	private LinkedList<User> watchedUsers;
	private Map<String, LinkedList<Repository>> repositoryMap;
	private Map<String, LinkedList<Commit>> commitMap;
	private Map<String, String> commitStatusMap;

	public static final int USER_ALREADY_WATCHED = 100;
	public static final int USER_INVALID = 101;
	public static final int USER_ADDED = 102;
	public static final int USER_ADD_SILENTLY = 999;
	
	public static final int REPOSITORY_UPDATED = 200;
	public static final int REPOSITORY_ADDED = 201;
	public static final int REPOSITORY_REMOVED = 202;

	public static Client getInstance() {
		if(instance == null)
			instance = new Client();

		return instance;
	}

	private Client() {
		watchedUsers = new LinkedList<User>();
		repositoryMap = new HashMap<String, LinkedList<Repository>>();
		commitMap = new HashMap<String, LinkedList<Commit>>();
		commitStatusMap = new HashMap<String, String>();
	}

	public void init() {
		Settings.initializeFolders();
		watchedUsers = Settings.loadUserList();
		repositoryMap = Settings.loadRepositoryMap();
		commitStatusMap = Settings.loadCommitStatus();
		commitMap = Settings.loadCommits(commitStatusMap.keySet());
	}

	public String getCommitStatuc(String name) {
		return commitStatusMap.get(name);
	}
	
	public void updateCommitStatus(String repository, long time) {
		commitStatusMap.put(repository, ISO8601.fromUnix(time));
	}
	
	public void updateWatchedUser(String name, App app) {
		int index = getWatchedUserIndex(name);
		if(index == -1)
			return;

		User user = watchedUsers.get(index);
		User newUser = UserHelper.getUser(user.getLogin());
		if(newUser.getId() != -1) {
			if(app != null)
				app.onUpdateWatchedUser(user, newUser);

			watchedUsers.set(index, newUser);
			user = newUser;
			Settings.saveUserList(watchedUsers);
		}

		String userName = user.getLogin();
		LinkedList<Repository> repositories = RepositoryHelper.getRepositories(userName);
		boolean success = true;
		for(Repository repo : repositories) {
			if(repo.getId() == -1) {
				success = false;
				break;
			}
		}

		if(success) {
			if(app != null && repositoryMap.get(userName) != null) {
				Map<String, Integer> oldRepositories = new HashMap<String, Integer>();
				Map<String, Integer> newRepositories = new HashMap<String, Integer>();
				Set<String> repositoryNames = new HashSet<String>();

				for(int i = 0; i < repositoryMap.get(userName).size(); ++i) {
					Repository repo = repositoryMap.get(userName).get(i);
					repositoryNames.add(repo.getFullName());
					oldRepositories.put(repo.getFullName(), i);
				}
				for(int i = 0; i < repositories.size(); ++i) {
					Repository repo = repositories.get(i);
					repositoryNames.add(repo.getFullName());
					newRepositories.put(repo.getFullName(), i);
				}

				for(String repositoryName : repositoryNames) {
					boolean oldContains = oldRepositories.containsKey(repositoryName);
					boolean newContains = newRepositories.containsKey(repositoryName);

					if(oldContains && newContains)
						app.onUpdateUserRepository(user, repositories.get(newRepositories.get(repositoryName)));
					else if(oldContains)
						app.onRemoveUserRepository(user, repositoryMap.get(userName).get(oldRepositories.get(repositoryName)));
					else if(newContains)
						app.onAddUserRepository(user, repositories.get(newRepositories.get(repositoryName)));
				}
			}

			repositoryMap.put(userName, repositories);
			Settings.saveRepositoryMap(repositoryMap);
		}
		
		for(Repository repository : repositories) {
			String status = getCommitStatuc(repository.getFullName());
			LinkedList<Commit> commits;
			if(status != null)
				commits = CommitHelper.getCommits(userName, repository.getName(), status);
			else
				commits = CommitHelper.getCommits(userName, repository.getName());

			for(int i = 0; i < commits.size(); ++i) {
				Commit commit = commits.get(i);
				if(i == 0) {
					updateCommitStatus(repository.getFullName(), commit.getCommitData().getCommitter().getDate() + 1);
				}

				LinkedList<Commit> commitList = commitMap.get(repository.getFullName());
				if(commitList == null) {
					commitMap.put(repository.getFullName(), new LinkedList<Commit>());
					commitList = commitMap.get(repository.getFullName());
				}
				commitList.add(i, commit);
			}
			if(app != null)
				app.onNewCommits(userName, repository.getFullName());
		}

		Settings.saveCommitStatus(commitStatusMap);
		Settings.saveCommits(commitMap);
	}
	
	public void updateWatchedUsers(App app) {
		for(User user : watchedUsers) {
			updateWatchedUser(user.getLogin(), app);
		}
	}

	public int getWatchedUserIndex(String name) {
		for(int i = 0; i < watchedUsers.size(); ++i) {
			User user = watchedUsers.get(i);
			if(user.getLogin().equalsIgnoreCase(name))
				return i;
		}

		return -1;
	}
	
	public User getWatchedUser(String name) {
		int index = getWatchedUserIndex(name);
		if(index == -1)
			return null;
		
		return watchedUsers.get(index);
	}
	
	public boolean hasRepositories(String name) {
		return repositoryMap.get(name) != null;
	}

	public boolean hasWatchedUser(String name) {
		return getWatchedUserIndex(name) != -1;
	}

	public boolean hasWatchedUsers() {
		return watchedUsers.size() > 0;
	}

	public List<User> getWatchedUsers() {
		return Collections.unmodifiableList(watchedUsers);
	}

	public List<Repository> getRepositories(String name) {
		return Collections.unmodifiableList(repositoryMap.get(name));
	}
	
	public List<Commit> getCommits(String fullName) {
		LinkedList<Commit> commits = commitMap.get(fullName);
		if(commits == null)
			return Collections.unmodifiableList(new LinkedList<Commit>());
		
		return Collections.unmodifiableList(commits);
	}

	public int addWatchedUser(String name, App app) {
		if(hasWatchedUser(name))
			return USER_ALREADY_WATCHED;

		User user = UserHelper.getUser(name);
		if(user.getId() != -1) {
			watchedUsers.add(user);
			onWatchedUsersChange();
			updateWatchedUser(name, app);
			return USER_ADDED;
		}

		return USER_INVALID;
	}

	public boolean removeWatchedUser(String name) {
		User user = getWatchedUser(name);
		if(user == null)
			return false;

		watchedUsers.remove(user);
		LinkedList<Repository> repositories = repositoryMap.get(user.getLogin());
		if(repositories != null) {
			for(Repository repo : repositories) {
				commitStatusMap.remove(repo.getFullName());
				commitMap.remove(repo.getFullName());
			}
		}
		repositoryMap.remove(name);

		Settings.saveRepositoryMap(repositoryMap);
		Settings.saveCommitStatus(commitStatusMap);
		Settings.saveCommits(commitMap);
		onWatchedUsersChange();
		return true;	
	}

	private void onWatchedUsersChange() {
		Settings.saveUserList(watchedUsers);		
	}
}
