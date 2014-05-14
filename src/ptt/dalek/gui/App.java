package ptt.dalek.gui;

import ptt.dalek.github.User;
import ptt.dalek.main.Client;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class App extends Application {
    
	private static final String NAME = "GitObserv";
	private static final String VERSION = "v0.1";
	
    private static final int DEFAULT_WIDTH = 600;
    private static final int DEFAULT_HEIGHT = 400;
    
    public static final int USERSP_PADDING_RIGHT = 6;
    public static final int USERSP_PADDING_LEFT = 6;
    public static final int USERSP_PADDING_TOP = 6;
    public static final int USERSP_PADDING_BOTTOM = 42;
    public static final int USERSP_SPACING = USERSP_PADDING_TOP;
    
    private static final String ADD_PROMPT_TEXT = "observe new user...";

    private BorderPane componentLayout;
    private ScrollPane userListSP;
    private VBox vbox_userlist;
    private TextField tf_addUser;
    private Button addUser;
    private HBox topbar;
    
    private VBox repos;
    private ScrollPane repoScroll;
    
    private Label addResponse;
    
    public Messenger message;
    
    double initialX;
	double initialY;
        
    @Override
    public void init() {
        componentLayout = new BorderPane();
        
        // VBox containing repositories
        repos = new VBox(USERSP_SPACING);
        repos.setPadding(new Insets(USERSP_PADDING_TOP, USERSP_PADDING_RIGHT, USERSP_PADDING_BOTTOM, 0));
        
        // scroll pane, contains repository VBox
        repoScroll = new ScrollPane();
        repoScroll.setContent(repos);
        repoScroll.setFitToWidth(true);
        
    	// scroll pane which contains the VBox
	    userListSP = new ScrollPane();
	    userListSP.setMinWidth(UserPane.WIDTH + USERSP_PADDING_RIGHT + USERSP_PADDING_LEFT);
	    userListSP.setPrefWidth(UserPane.WIDTH + USERSP_PADDING_RIGHT + USERSP_PADDING_LEFT);
	    userListSP.setVbarPolicy(ScrollBarPolicy.NEVER);
	    
	    // VBox containing all observed users
	    vbox_userlist = new VBox();
	    vbox_userlist.setSpacing(USERSP_SPACING);
	    vbox_userlist.setPadding(new Insets(USERSP_PADDING_TOP, USERSP_PADDING_RIGHT, USERSP_PADDING_BOTTOM, USERSP_PADDING_LEFT));
	    userListSP.setContent(vbox_userlist);
	    
	    // text field to add users
	    tf_addUser = new TextField();
	    tf_addUser.setPromptText(ADD_PROMPT_TEXT);
	    tf_addUser.setPrefWidth(UserPane.WIDTH * (0.75) + USERSP_PADDING_RIGHT + USERSP_PADDING_LEFT);
	    tf_addUser.setMinWidth(UserPane.WIDTH * (0.75) + USERSP_PADDING_RIGHT + USERSP_PADDING_LEFT);  
	    tf_addUser.setPrefHeight(20);
	    tf_addUser.setMinHeight(20); 
	    tf_addUser.setId("tf_addUser");
	    tf_addUser.setOnAction(new EventHandler<ActionEvent>() {
	        @Override
	        public void handle(ActionEvent event) {
	     	   addUser();
	        }
	     });
	    
	    // button to add users
	    addUser = new Button("+");
	    addUser.setId("button_addUser");
	    addUser.setPrefWidth(UserPane.WIDTH - tf_addUser.getPrefWidth());
	    addUser.setMinWidth(UserPane.WIDTH - tf_addUser.getMinWidth());
	    addUser.setPrefHeight(tf_addUser.getPrefHeight());
	    addUser.setMinHeight(tf_addUser.getMinHeight());
	    addUser.setOnAction(new EventHandler<ActionEvent>() {
	       @Override
	       public void handle(ActionEvent event) {
	    	   addUser();
	       }
	    });
	   
	    // bar on top, contains add button, text field and status label
	    topbar = new HBox();
	    topbar.setAlignment(Pos.CENTER_LEFT);
	    topbar.setPadding(new Insets(USERSP_PADDING_LEFT, 0, USERSP_PADDING_LEFT, USERSP_PADDING_LEFT));
	    topbar.setId("topbar");
	  
	    topbar.getChildren().add(tf_addUser);
	    topbar.getChildren().add(addUser);
	    
	    // Label, displays status message 
	    addResponse = new Label();
	    addResponse.setId("addResponse");
	    topbar.getChildren().add(addResponse);
	    
	    // Messenger for addRespone Label
	    message = new Messenger(addResponse, 2000, 500);
	    
	    // setup componentLayout BorderPane
        componentLayout.setCenter(repoScroll);
	    componentLayout.setLeft(userListSP);
	    componentLayout.setTop(topbar);
	    
	    // loads saved users from file
	    Client.getInstance().loadWatchedUsers();
    }
    
	@Override
    public void start(final Stage stage) {
		stage.setTitle(NAME + " " + VERSION);

        Scene scene = new Scene(componentLayout, DEFAULT_WIDTH, DEFAULT_HEIGHT);
        stage.setScene(scene);
        scene.getStylesheets().add(App.class.getResource("client.css").toExternalForm());
        stage.show();
        
        message.displayMessage("Welcom to GitObserve v1.0", 3000, 500);        

        updateUserList();
    }
	
	// clears repository VBox
    public void unloadContent() {
    	repos.getChildren().clear();
    }
    
    // loads repositories for user 
    public void loadContent(String user) {
    	unloadContent();
    	RepositoryPane rp1 = new RepositoryPane("Hans");
    	RepositoryPane rp2 = new RepositoryPane("Jürgen");
    	RepositoryPane rp3 = new RepositoryPane("Peter");
    	
    	repos.getChildren().addAll(rp1, rp2, rp3);
    }
    
    // clears and (re)sets the content of vbox_userList, using Client.getWatchedUsers()
    private void updateUserList() {
    	vbox_userlist.getChildren().clear();

    	for(User user : Client.getInstance().getWatchedUsers()) {
    	    final UserPane newPane = new UserPane(user.getLogin(), this);
    		vbox_userlist.getChildren().add(newPane);
    	}
    }
    
    // reads tf_addUser and searches for user
    // if found, creates user pane and updates user list
    private void addUser() {
    	updateUserList();
    	
    	String userName = tf_addUser.getText();
    	if(userName.length() == 0)
    		return;
    	
    	Client c = Client.getInstance();
    	int returnCode = c.addWatchedUser(userName);
	    if(returnCode == Client.USER_ADDED) {
	    	if(c.hasWatchedUsers()) {
	    		userName = c.getLatestUser().getLogin();
	    	}
	    	message.displayMessage("You are now observing " + userName + ".");
		    
		    final UserPane newPane = new UserPane(userName, this);
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
		    
			vbox_userlist.getChildren().add(newPane);
	    } else if(returnCode == Client.USER_ALREADY_WATCHED) {
	    	message.displayMessage("You are already watching " + userName + ".");
	    } else if(returnCode == Client.USER_INVALID) {
	    	message.displayMessage("User " + userName + " does not exist.");
	    }

	    tf_addUser.clear();	    
    }  
}
