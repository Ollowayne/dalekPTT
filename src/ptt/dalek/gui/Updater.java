package ptt.dalek.gui;

import java.util.LinkedList;

import ptt.dalek.main.Client;
import javafx.application.Platform;
import javafx.concurrent.Task;

public class Updater extends Task<Void> {

	private static final int UPDATE_TIME = 30000;

	private LinkedList<String> addUsers = new LinkedList<String>();
	private LinkedList<String> removeUsers = new LinkedList<String>();
	private App app;

	private int nextUpdate;

	public void addUser(String name) {
		addUsers.add(name);
	}
	
	public void removeUser(String name) {
		removeUsers.add(name);
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
			try {
			if(removeUsers.size() > 0) {
				app.setLoading(true);

				for(String name : removeUsers) {
					app.getClient().removeWatchedUser(name);
				}

				removeUsers.clear();
				app.setLoading(false);
			}

			if(addUsers.size() > 0) {
				app.setLoading(true);

				for(final String name : addUsers) {
					final int result = Client.getInstance().addWatchedUser(name);
					Platform.runLater(new Runnable(){
						@Override
						public void run() {
							app.onAddUser(name, result);
						}
					});
				}

				addUsers.clear();
				app.setLoading(false);
			}

			if(nextUpdate <= 0) {
				app.setLoading(true);

				app.getClient().updateWatchedUsers(app);

				app.setLoading(false);	
				nextUpdate = UPDATE_TIME;
			}

			nextUpdate -= 100;
		} catch(Exception e) {e.printStackTrace();}
			Thread.sleep(100);
		}

		return null;
	}

}
