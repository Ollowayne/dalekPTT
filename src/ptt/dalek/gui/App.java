package ptt.dalek.gui;

import java.awt.Point;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ptt.dalek.github.Commit;
import ptt.dalek.github.Repository;
import ptt.dalek.github.User;
import ptt.dalek.main.Client;
import ptt.dalek.ui.RepositoryPane;
import ptt.dalek.ui.UserPane;
import ptt.dalek.ui.UserPaneGroup;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class App extends Application {

	private static final String NAME = "GitObserve";
	private static final String VERSION = "v0.1";

	private static final String PROMPT_STRING = "Add a new user..";
	public static final String WELCOME = "Welcome to " + NAME + " " + VERSION + "!";
	public static final String GETTING_STARTED = "Get started by adding users!";

	public static final String NOW_WATCHING_STRING = "You are now watching '%s'.";
	public static final String INVALID_USER_STRING = "User '%s' could not be found.";
	public static final String ALREADY_WATCHING_STRING = "You are already watching '%s'.";
	public static final String STOP_WATCHING_STRING = "You are no longer watching '%s'.";
	public static final String COPIED_BLOG_URL = "Copied to clipboard: '%s'.";
	public static final String REPOSITORIES_NOT_LOADED_STRING = "Repositories not loaded yet. Please wait.";
	public static final String ADDING_USER_IN_PROGRESS = "Adding user %s. Please wait...";

	private static final int DEFAULT_WIDTH = 800;
	private static final int DEFAULT_HEIGHT = 600;

	public static final int USERSP_PADDING_RIGHT = 6;
	public static final int USERSP_PADDING_LEFT = 6;
	public static final int USERSP_PADDING_TOP = 6;
	public static final int USERSP_PADDING_BOTTOM = 42;
	public static final int USERSP_SPACING = 6;
	
	private Map<String, Long> newUpdates;
	private Map<String, Long> lastUpdates;
	
	private BorderPane bpLayout;
	private ScrollPane spUserList;
	private UserPaneGroup upgUserlist;
	private TextField tfAddUser;
	private Button bAddUser;
	private GridPane topbar;
	private ImageView loadingAnimation;
	private Image loadingImage;

	private ScrollPane spRepositories;
	private VBox vbRepository;

	public Messenger topbarHint;

	private Updater updater;
	private Client client;

	@Override
	public void init() {
		newUpdates = new HashMap<String, Long>();
		lastUpdates = new HashMap<String, Long>();
		
		bpLayout = new BorderPane();

		loadingAnimation = new ImageView();
		loadingImage = new Image("file:res/loading.gif");
		loadingAnimation.setImage(loadingImage);
		loadingAnimation.setOpacity(0);

		// VBox containing repositories
		vbRepository = new VBox(USERSP_SPACING);
		vbRepository.setPadding(new Insets(USERSP_PADDING_TOP, USERSP_PADDING_RIGHT, USERSP_PADDING_BOTTOM, 0));

		// scroll pane, contains repository VBox
		spRepositories = new ScrollPane();
		spRepositories.setContent(vbRepository);
		spRepositories.setFitToWidth(true);

		// scroll pane which contains the VBox
		spUserList = new ScrollPane();
		spUserList.setMinWidth(UserPane.WIDTH + USERSP_PADDING_RIGHT + USERSP_PADDING_LEFT);
		spUserList.setPrefWidth(UserPane.WIDTH + USERSP_PADDING_RIGHT + USERSP_PADDING_LEFT);
		spUserList.setVbarPolicy(ScrollBarPolicy.NEVER);

		// UserPaneGroup containing all observed users
		upgUserlist = new UserPaneGroup();
		spUserList.setContent(upgUserlist);

		// text field to add users
		tfAddUser = new TextField();
		tfAddUser.setPromptText(PROMPT_STRING);
		tfAddUser.setPrefWidth(UserPane.WIDTH * (0.75) + USERSP_PADDING_RIGHT + USERSP_PADDING_LEFT);
		tfAddUser.setMinWidth(UserPane.WIDTH * (0.75) + USERSP_PADDING_RIGHT + USERSP_PADDING_LEFT);  
		tfAddUser.setMaxWidth(UserPane.WIDTH * (0.75) + USERSP_PADDING_RIGHT + USERSP_PADDING_LEFT);  
		tfAddUser.setPrefHeight(20);
		tfAddUser.setMinHeight(20); 
		tfAddUser.setId("tf_addUser");
		tfAddUser.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				addUser();
			}
		 });

		// button to add users
		bAddUser = new Button("+");
		bAddUser.setId("button_addUser");
		bAddUser.setPrefWidth(UserPane.WIDTH - tfAddUser.getPrefWidth());
		bAddUser.setMinWidth(UserPane.WIDTH - tfAddUser.getMinWidth());
		bAddUser.setPrefHeight(tfAddUser.getPrefHeight());
		bAddUser.setMinHeight(tfAddUser.getMinHeight());
		bAddUser.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				addUser();
			}
		});

		// bar on top, contains add button, text field and status label
		topbar = new GridPane();
		topbar.setAlignment(Pos.CENTER_LEFT);
		topbar.setPadding(new Insets(USERSP_PADDING_LEFT, USERSP_PADDING_LEFT, USERSP_PADDING_LEFT, USERSP_PADDING_LEFT));
		topbar.setId("topbar");

		GridPane.setConstraints(tfAddUser, 0, 0);
		GridPane.setConstraints(bAddUser, 1, 0);
		GridPane.setConstraints(loadingAnimation, 3, 0);
		topbar.getChildren().addAll(bAddUser, tfAddUser, loadingAnimation); 

		GridPane.setHgrow(loadingAnimation, Priority.ALWAYS);
		GridPane.setHalignment(loadingAnimation, HPos.RIGHT);

		// Messenger for addRespone Label
		topbarHint = new Messenger(topbar, new Point(10, 0), 2000, 500);

		// setup componentLayout BorderPane
		bpLayout.setCenter(spRepositories);
		bpLayout.setLeft(spUserList);
		bpLayout.setTop(topbar);

		// loads saved users from file
		client = Client.getInstance();
		client.init();
		setupUserList();

		updater = new Updater(this);
		(new Thread(updater)).start();
	}

	@Override
	public void start(final Stage stage) {
		stage.setTitle(NAME + " " + VERSION);

		Scene scene = new Scene(bpLayout, DEFAULT_WIDTH, DEFAULT_HEIGHT);
		stage.setScene(scene);
		scene.getStylesheets().add(App.class.getResource("style.css").toExternalForm());
		stage.show();

		topbarHint.displayMessage(WELCOME, 3000, 500);
		if(upgUserlist.getChildren().isEmpty()) {
			topbarHint.displayMessage(GETTING_STARTED);
		}
	}

	@Override
	public void stop() {
		updater.cancel();
	}
	
	public Client getClient() {
		return client;
	}

	// clears repository VBox
	public void clearContent() {
		vbRepository.getChildren().clear();
	}

	// loads repositories for user 
	private void loadContent(String user) {
		if(client.hasRepositories(user)) {
			List<Repository> repositories = client.getRepositories(user);
			if(repositories != null) {
				loadContent(repositories);
				return;
			}
		}

		topbarHint.displayMessage(REPOSITORIES_NOT_LOADED_STRING);
	}
	
	private void loadContent(List<Repository> repositories) {
		clearContent();
		
		for(Repository repository : repositories) {
			RepositoryPane pane = new RepositoryPane(repository, this);
			vbRepository.getChildren().add(pane);
		}
	}
	
	// clears and (re)sets the content of vbox_userList, using Client.getWatchedUsers()
	public void setupUserList() {
		upgUserlist.clear();

		for(User user : Client.getInstance().getWatchedUsers()) {
			onAddUser(user.getLogin(), Client.USER_ADD_SILENTLY);
		}
	}

	public void setLoading(boolean loading) {
		loadingAnimation.setOpacity(loading ? 1 : 0);
	}

	// reads tf_addUser and searches for user
	// if found, creates user pane and updates user list
	private void addUser() {
		String userName = tfAddUser.getText();
		if(userName.length() == 0)
			return;
		topbarHint.displayMessage(String.format(ADDING_USER_IN_PROGRESS, userName));
		updater.addUser(userName);
		tfAddUser.clear();
	}

	public void onSelectUser(String userName) {
		loadContent(userName);
	}
	
	public UserPane getSelectedUser() {
		return upgUserlist.getSelected();
	}
	
	public void onAddUser(final String name, final int result) {
		if(result == Client.USER_ALREADY_WATCHED) {
			topbarHint.displayMessage(String.format(ALREADY_WATCHING_STRING, name));
			return;
		} else if(result == Client.USER_INVALID) {
			topbarHint.displayMessage(String.format(INVALID_USER_STRING, name));
			return;
		}

		for(User user : Client.getInstance().getWatchedUsers()) {;
			if(user.getLogin().equalsIgnoreCase(name)) {
				final UserPane newPane = new UserPane(user, this);
				newPane.setOpacity(0);

				final Timeline swipeIn = new Timeline(new KeyFrame(Duration.millis(200), 
						new KeyValue(newPane.layoutXProperty(), USERSP_PADDING_LEFT))
				);

				final Timeline set = new Timeline(new KeyFrame(Duration.millis(50), 
						new KeyValue(newPane.layoutXProperty(), -UserPane.WIDTH))
				);
				set.setOnFinished(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						newPane.setOpacity(1);
						swipeIn.play();
					}
				});

				set.play();
				upgUserlist.getChildren().add(newPane);
				if(result != Client.USER_ADD_SILENTLY) {
					topbarHint.displayMessage(String.format(NOW_WATCHING_STRING, user.getLogin()));
				}
			}
		}
	}

	public void onRemoveUser(String userName) {
		UserPane selected = getSelectedUser();
		if(selected != null) {
			if(selected.getUser().getLogin().equalsIgnoreCase(userName)) {
				clearContent();
			}
		}
		
		updater.removeUser(userName);
 		topbarHint.displayMessage(String.format(App.STOP_WATCHING_STRING, userName));
	}
	
	public void onCopyToClipbaord(String value) {
		topbarHint.displayMessage(String.format(App.COPIED_BLOG_URL, value));
	}

	public void onUpdateWatchedUser(User user, final User addUser) {
		Node node = upgUserlist.lookup("#" + user.getLogin());
		if(node != null) {
			if(node instanceof UserPane) {
				final UserPane userPane = (UserPane)node;
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						userPane.update(addUser);
					}
				});
			}
		}
		
		//TODO 
		// compare, alert
	}

	public void onToggleRepository(String repositoryName) {
//		List<Commit> commits = client.getCommits(repositoryName);
//		
//		for(Commit c : commits) {
//			System.out.println("Commit: " + c.getSha() + " : " + c.getCommitData().getMessage());
//		}
	}
	
	public List<Commit> getMyCommits(String repositoryName) {
		return client.getCommits(repositoryName);
	}

	public void onAddUserRepository(User user, Repository repository) {
		if(!getSelectedUser().getUser().getLogin().equalsIgnoreCase(user.getLogin()))
			return;
		
		final RepositoryPane pane = new RepositoryPane(repository, this);
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				vbRepository.getChildren().add(pane);
			}
		});
	}

	public void onRemoveUserRepository(User user, Repository repository) {
			try {
				final Node node = vbRepository.lookup("#" + repository.getFullName());
				if(node == null)
					return;
				
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						vbRepository.getChildren().remove(node);
					}
				});
			} catch (IndexOutOfBoundsException e) {
				return;
			}
	}

	public void onUpdateUserRepository(User user, Repository repository) {
		try {
			final Node node = vbRepository.lookup("#" + repository.getFullName());
			if(node == null)
				return;
			
			final Repository temp = repository;
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					((RepositoryPane) node).update(temp);
				}
			});
		} catch (IndexOutOfBoundsException e) {
			return;
		}
	}

	public void onNewCommits(String userName, String repositoryName) {
		try {
			final Node node = vbRepository.lookup("#" + repositoryName);
			if(node == null)
				return;
			
	
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					((RepositoryPane)node).updateCommits();
				}
			});
		} catch (IndexOutOfBoundsException e) {
			return;
		}
	}
	
	public void setNewUpdate(String repoName, long timestamp) {
		resetUpdate(repoName);
		Long ts = new Long(timestamp);
		newUpdates.put(repoName, ts);
	}
	
	public long getLastUpdate(String repoName) {
		try {
			return lastUpdates.get(repoName).longValue();
		} catch (NullPointerException e) {
			return -1;
		}
	}
	
	public long getNewUpdate(String repoName) {
		try {
			return newUpdates.get(repoName).longValue();
		} catch (NullPointerException e) {
			return -1;
		}
	}
	
	public boolean isUpdated(String repoName) {
		if(getNewUpdate(repoName) == -1 || getLastUpdate(repoName) == -1) {
			// error case, or initialization
			return false;
		}
		
		return getNewUpdate(repoName) > getLastUpdate(repoName);
	}
	
	public void resetUpdate(String repoName) {
		lastUpdates.put(repoName, newUpdates.get(repoName));
	}
}
