package ptt.dalek.gui;

import java.awt.Point;

import ptt.dalek.github.User;
import ptt.dalek.main.Client;
import ptt.dalek.ui.RepositoryPane;
import ptt.dalek.ui.UserPane;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
    
	private static final String NAME = "GitObserv";
	private static final String VERSION = "v0.1";

    private static final String PROMPT_STRING = "Add a new user..";
    public static final String NOW_WATCHING_STRING = "You are now watching '%s'.";
    public static final String INVALID_USER_STRING = "User '%s' could not be found.";
    public static final String ALREADY_WATCHING_STRING = "You are already watching '%s'.";
    public static final String STOP_WATCHING_STRING = "You are no longer watching '%s'.";
    
    private static final int DEFAULT_WIDTH = 600;
    private static final int DEFAULT_HEIGHT = 400;
    
    public static final int USERSP_PADDING_RIGHT = 6;
    public static final int USERSP_PADDING_LEFT = 6;
    public static final int USERSP_PADDING_TOP = 6;
    public static final int USERSP_PADDING_BOTTOM = 42;
    public static final int USERSP_SPACING = 6;

    private BorderPane bpLayout;
    private ScrollPane spUserList;
    private VBox vbUserlist;
    private TextField tfAddUser;
    private Button bAddUser;
    private GridPane topbar;
    private ImageView loadingAnimation;
    private Image loadingImage;
    
    private ScrollPane spRepositories;
    private VBox vbRepository;
        
    public Messenger topbarHint;

    private Updater updater;
        
    @Override
    public void init() {
        bpLayout = new BorderPane();
        
        loadingAnimation = new ImageView();
        loadingImage = new Image("file:res/loading.gif");
        loadingAnimation.setImage(loadingImage);
        
        stopLoadingAnimation();
        
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
	    
	    // VBox containing all observed users
	    vbUserlist = new VBox();
	    vbUserlist.setSpacing(USERSP_SPACING);
	    vbUserlist.setPadding(new Insets(USERSP_PADDING_TOP, USERSP_PADDING_RIGHT, USERSP_PADDING_BOTTOM, USERSP_PADDING_LEFT));
	    spUserList.setContent(vbUserlist);
	    
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
	    Client.getInstance().init();
	    Client.getInstance().loadWatchedUsers();
        updateUserList();
	    
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

        topbarHint.displayMessage("Welcome to GitObserve v1.0", 3000, 500);
        if(vbUserlist.getChildren().isEmpty()) {
            topbarHint.displayMessage("Get started by adding users!");
        }
    }
	
	@Override
	public void stop() {
		updater.cancel();
	}

	// clears repository VBox
    public void unloadContent() {
    	vbRepository.getChildren().clear();
    }
    
    // loads repositories for user 
    public void loadContent(String user) {
    	unloadContent();
    	RepositoryPane rp1 = new RepositoryPane("Hans");
    	RepositoryPane rp2 = new RepositoryPane("Jürgen");
    	RepositoryPane rp3 = new RepositoryPane("Peter");
    	
    	vbRepository.getChildren().addAll(rp1, rp2, rp3);
    }
    
    // clears and (re)sets the content of vbox_userList, using Client.getWatchedUsers()
    public void updateUserList() {
    	try {
    	vbUserlist.getChildren().clear();
    	} catch(Exception e) {e.printStackTrace();}
    	for(User user : Client.getInstance().getWatchedUsers()) {
    	    final UserPane newPane = new UserPane(user.getLogin(), this);
    		vbUserlist.getChildren().add(newPane);
    	}
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
	    	    final UserPane newPane = new UserPane(user.getLogin(), this);
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
				vbUserlist.getChildren().add(newPane);

		    	topbarHint.displayMessage(String.format(NOW_WATCHING_STRING, user.getLogin()));
    		}
    	}
	}  
	
	public void startLoadingAnimation() {
		loadingAnimation.setOpacity(1);
	}
	
	public void stopLoadingAnimation() {
		loadingAnimation.setOpacity(0);
	}
	
    // reads tf_addUser and searches for user
    // if found, creates user pane and updates user list
    private void addUser() {    	
    	String userName = tfAddUser.getText();
    	if(userName.length() == 0)
    		return;
    	
    	updater.addUser(userName);
	    tfAddUser.clear();	    
    }
}
