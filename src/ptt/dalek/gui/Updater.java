package ptt.dalek.gui;

import java.util.LinkedList;

import ptt.dalek.main.Client;
import javafx.concurrent.Task;

public class Updater extends Task<Void> {

	private static final int UPDATE_TIME = 30000;
	
	private LinkedList<String> updateUsers = new LinkedList<String>();
	private App app;
	
	private int nextUpdate;
	
	public void addUser(String name) {
		updateUsers.add(name);
	}
	
	public Updater(App app) {
		super();
		this.app = app;
		
		nextUpdate = 0;
	}
	
	public App getApp() {
		return app;
	}

	public void setApp(App app) {
		this.app = app;
	}

	@Override
	protected Void call() throws Exception {
		while(!isCancelled()) {
			if(updateUsers.size() > 0) {
				for(String name : updateUsers) {
					Client.getInstance().addWatchedUser(name);
					app.updateUserList();
				}
				updateUsers.clear();
			}
			
			if(nextUpdate <= 0) {
				Client.getInstance().updateWatchedUsers();
				app.updateUserList();
				nextUpdate = UPDATE_TIME;
			}

			nextUpdate -= 100;
			Thread.sleep(100);
		}

		return null;
	}

	
}
