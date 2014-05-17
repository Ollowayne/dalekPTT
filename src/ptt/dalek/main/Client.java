package ptt.dalek.main;

import java.util.HashMap;
import java.util.List;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Map;

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
	
	public void updateWatchedUsers(App app) {
		LinkedList<User> updatedUsers = new LinkedList<User>();
		for(User user : watchedUsers) {
			User newUser = UserHelper.getUser(user.getLogin());

			boolean isValid = (newUser.getId() != -1);
			User addUser = (isValid ? newUser : user);
			updatedUsers.add(addUser);

			if(app != null && isValid)
				app.onUpdateWatchedUser(user, addUser);
		}

		Map<String, LinkedList<Repository>> updatedRepositoryMap = new HashMap<String, LinkedList<Repository>>();
		for(User user : updatedUsers) {
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
				LinkedList<Repository> oldRepositories = updatedRepositoryMap.get(userName);
				updatedRepositoryMap.put(user.getLogin(), repositories);

				if(app != null)
					app.onUpdateUserRepositories(user, oldRepositories, repositories);
			}
		}

		watchedUsers = updatedUsers;
		repositoryMap = updatedRepositoryMap;
		Settings.saveRepositoryMap(repositoryMap);

		for(String username : repositoryMap.keySet()) {
			for(Repository repository : repositoryMap.get(username)) {
				String status = getCommitStatuc(repository.getFullName());
				LinkedList<Commit> commits;
				if(status != null)
					commits = CommitHelper.getCommits(username, repository.getName(), status);
				else
					commits = CommitHelper.getCommits(username, repository.getName());

				for(int i = 0; i < commits.size(); ++i) {
					Commit commit = commits.get(i);
					if(i == 0) {
						commitStatusMap.put(repository.getFullName(), ISO8601.fromUnix(commit.getCommitData().getCommitter().getDate() + 1));
					}

					LinkedList<Commit> commitList = commitMap.get(repository.getFullName());
					if(commitList == null) {
						commitMap.put(repository.getFullName(), new LinkedList<Commit>());
						commitList = commitMap.get(repository.getFullName());
					}
					commitList.addFirst(commit);
				}
			}
		}

		Settings.saveCommitStatus(commitStatusMap);
		Settings.saveCommits(commitMap);
	}

	public boolean hasRepositories(String name) {
		return repositoryMap.get(name) != null;
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

	public int getWatchedUserIndex(String name) {
		for(int i = 0; i < watchedUsers.size(); ++i) {
			if(watchedUsers.get(i).getLogin().equalsIgnoreCase(name))
				return i;
		}

		return -1;
	}

	public boolean hasWatchedUser(String name) {
		return getWatchedUserIndex(name) != -1;
	}

	public boolean hasWatchedUsers() {
		return watchedUsers.size() > 0;
	}

	public User getLatestUser() {
		return watchedUsers.get(watchedUsers.size() - 1);
	}

	public int addWatchedUser(String name) {
		if(hasWatchedUser(name))
			return USER_ALREADY_WATCHED;

		User user = UserHelper.getUser(name);
		if(user.getId() != -1) {
			watchedUsers.add(user);
			onWatchedUsersChange();
			return USER_ADDED;
		}

		return USER_INVALID;
	}

	public boolean removeWatchedUser(String name) {
		int index = getWatchedUserIndex(name);
		if(index == -1)
			return false;

		watchedUsers.remove(index);
		repositoryMap.remove(name);
		Settings.saveRepositoryMap(repositoryMap);
		onWatchedUsersChange();
		return true;	
	}

	private void onWatchedUsersChange() {
		Settings.saveUserList(watchedUsers);		
	}
}
