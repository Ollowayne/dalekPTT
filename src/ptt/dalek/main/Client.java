package ptt.dalek.main;

import java.util.List;
import java.util.Collections;
import java.util.LinkedList;

import ptt.dalek.github.User;
import ptt.dalek.helpers.UserHelper;

public class Client {
	private static Client instance;
	
	private LinkedList<User> watchedUsers;
	
	public static final int USER_ALREADY_WATCHED = 100;
	public static final int USER_INVALID = 101;
	public static final int USER_ADDED = 102;
	
	public static Client getInstance() {
		if(instance == null)
			instance = new Client();
		
		return instance;
	}
	
	private Client() {}
	
	public void loadWatchedUsers() {
		watchedUsers = Settings.loadUserList();
	}
	
	public List<User> getWatchedUsers() {
		return Collections.unmodifiableList(watchedUsers);
	}

	public int getWatchedUserIndex(String name) {
		String lowerName = name.toLowerCase();
		for(int i = 0; i < watchedUsers.size(); ++i) {
			if(watchedUsers.get(i).getLogin().toLowerCase().equals(lowerName))
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
		User user = UserHelper.getUser(name);
		if(user.getId() != -1) {
			if(!hasWatchedUser(user.getLogin())) {
				watchedUsers.add(user);
				onWatchedUsersChange();
				return USER_ADDED;
			} else {
				return USER_ALREADY_WATCHED;
			}
		}
		
		return USER_INVALID;
	}

	public boolean removeWatchedUser(String name) {
		int index = getWatchedUserIndex(name);
		if(index == -1)
			return false;
		
		watchedUsers.remove(index);
		onWatchedUsersChange();
		return true;	
	}

	private void onWatchedUsersChange() {
		Settings.saveUserList(watchedUsers);		
	}
}
