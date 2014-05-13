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
    
    public static final int USERSP_PADDING_RIGHT = 4;
    public static final int USERSP_PADDING_LEFT = 4;
    public static final int USERSP_PADDING_TOP = 4;
    public static final int USERSP_PADDING_BOTTOM = 42;
    public static final int USERSP_SPACING = USERSP_PADDING_TOP;
    
    private static final String ADD_PROMPT_TEXT = "observe new user...";

    private BorderPane componentLayout;
    private ScrollPane userListSP;
    private VBox vbox_userlist;
    private TextField tf_addUser;
    private Button addUser;
    private HBox topbar;
    
    private Label test;
    
    private Label addResponse;
    
    public Messenger message;
    
    double initialX;
	double initialY;
        
    @Override
    public void init() {
        componentLayout = new BorderPane();	
        
        test = new Label();
        componentLayout.setCenter(test);

    	// scroll pane which contains the VBox
	    userListSP = new ScrollPane();
	    userListSP.setMinWidth(UserPane.WIDTH + USERSP_PADDING_RIGHT + USERSP_PADDING_LEFT);
	    userListSP.setPrefWidth(UserPane.WIDTH + USERSP_PADDING_RIGHT + USERSP_PADDING_LEFT);
	    userListSP.setVbarPolicy(ScrollBarPolicy.NEVER);
	    componentLayout.setLeft(userListSP);
	    
	    // VBox containing all observed users
	    vbox_userlist = new VBox();
	    vbox_userlist.setSpacing(USERSP_SPACING);
	    vbox_userlist.setPadding(new Insets(USERSP_PADDING_TOP, USERSP_PADDING_RIGHT, USERSP_PADDING_BOTTOM, USERSP_PADDING_LEFT));
	    userListSP.setContent(vbox_userlist);
	    
	    // text field to add users
	    tf_addUser = new TextField();
	    tf_addUser.setPromptText(ADD_PROMPT_TEXT);
	    tf_addUser.setPrefWidth(168 + USERSP_PADDING_RIGHT + USERSP_PADDING_LEFT);
	    tf_addUser.setMinWidth(168 + USERSP_PADDING_RIGHT + USERSP_PADDING_LEFT);  
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
	    addUser.setPrefWidth(32);
	    addUser.setMinWidth(32);
	    addUser.setOnAction(new EventHandler<ActionEvent>() {
	       @Override
	       public void handle(ActionEvent event) {
	    	   addUser();
	       }
	    });
	   
	    topbar = new HBox();
	    topbar.setAlignment(Pos.CENTER_LEFT);
	    componentLayout.setTop(topbar);
	  
	    topbar.getChildren().add(tf_addUser);
	    topbar.getChildren().add(addUser);
	    
	    addResponse = new Label();
	    addResponse.setId("addResponse");
	    topbar.getChildren().add(addResponse);
	    
	    Client.getInstance().loadWatchedUsers();
	    
	    message = new Messenger(addResponse, 2000, 500);
    }
    
	@Override
    public void start(final Stage stage) {
		stage.setTitle(NAME + " " + VERSION);
		
        unloadContent();
        
        Scene scene = new Scene(componentLayout, DEFAULT_WIDTH, DEFAULT_HEIGHT);
        stage.setScene(scene);
        scene.getStylesheets().add(App.class.getResource("client.css").toExternalForm());
        stage.show();
        
        message.displayMessage("Welcom to GitObserve v1.0", 3000, 500);        

        updateUserList();
    }
    
    public void unloadContent() {
    	test.setText("No User Selected");
    }
    
    public void loadContent(String user) {
    	unloadContent();
    	test.setText(user);
    }
    
    // clears and (re)sets the content of vbox_userList, using *.getUsers()
    private void updateUserList() {
    	vbox_userlist.getChildren().clear();
    	
    	
    	for(User user : Client.getInstance().getWatchedUsers()) {
    	    final UserPane newPane = new UserPane(user.getLogin(), this);
    		vbox_userlist.getChildren().add(newPane);
    	}
    	
    		// debug
    	consolePrintUserList();
    }
    
    // reads tf_addUser and searches for user
    // if found, creates user pane and updates user list
    private void addUser() {
	   	// set w. API Data
    	updateUserList();
    	
    	String userName = tf_addUser.getText();
    	if(userName.length() == 0)
    		return;
    	
    	Client c = Client.getInstance();
    	int returnCode = c.addWatchedUser(userName);
	    if(returnCode == Client.USER_ADDED) {
	    	if(c.hasWatchedUsers())
	    		userName = c.getLatestUser().getLogin();

	    	message.displayMessage("You are now observing " + userName + ".");
		    
		    final UserPane newPane = new UserPane(userName, this);
		    newPane.setOpacity(0);
		    
		    final Timeline swipeIn = new Timeline();
		    final KeyValue kv2 = new KeyValue(newPane.layoutXProperty(), USERSP_PADDING_LEFT);
		    final KeyFrame kf2 = new KeyFrame(Duration.millis(200), kv2);
		    swipeIn.getKeyFrames().add(kf2);
		    
		    final Timeline set = new Timeline();
		    final KeyValue kv = new KeyValue(newPane.layoutXProperty(), -UserPane.WIDTH);
		    final KeyFrame kf = new KeyFrame(Duration.millis(50), kv);
		    set.getKeyFrames().add(kf);
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
    
    // DEBUG
    private void consolePrintUserList() {
    	System.out.println("<");
    	for(User user : Client.getInstance().getWatchedUsers()) {
    	    System.out.println(user.getLogin());
    	}
    	System.out.println("/>");
    }
}


/// links http://docs.oracle.com/javafx/2/layout/builtin_layouts.htm
// JAVAFX CONTROL DIALOGS http://fxexperience.com/controlsfx/ 


//primaryStage.setTitle("Hello World!");
//Button btn = new Button();
//Button btn2 = new Button("Stuff");
//btn.setText("Say 'Hello World'");
//btn.setOnAction(new EventHandler<ActionEvent>() {
//
//    @Override
//    public void handle(ActionEvent event) {
//        System.out.println("Hello World!");
//    }
//});
//
//final Timeline timeline = new Timeline();
//final KeyValue kv = new KeyValue(btn.layoutXProperty(),150);
//final KeyFrame kf = new KeyFrame(Duration.millis(1000), kv);
//timeline.getKeyFrames().add(kf);
//timeline.play();
//StackPane root = new StackPane();
//root.getChildren().add(btn);
//root.getChildren().add(btn2);
//
//primaryStage.setScene(new Scene(root, 300, 250));
//primaryStage.show();


// IMAGE LOAD TEST CODE
//BufferedImage bimage = null;
//try {
//    URL url = new URL("https://avatars.githubusercontent.com/u/2455012?");
//    bimage = ImageIO.read(url);
//} catch (IOException e) {
//}
//
//Image image = SwingFXUtils.toFXImage(bimage, null);
//ImageView iv1 = new ImageView();
//iv1.setImage(image);

//componentLayout.setCenter(iv1);



//Controller mainContainer = new Controller();
//
//// initialize all views and the respective controller (IControlled ViewN)
//mainContainer.loadView(Main.view1ID, Main.view1File);
//mainContainer.loadView(Main.view2ID, Main.view2File);
//
//// sets starting view
//mainContainer.setView(Main.view1ID);   
//
//// JAVAFX Application initialization
//Group root = new Group();
//root.getChildren().addAll(mainContainer);
//Scene scene = new Scene(root);
//primaryStage.setScene(scene);
//primaryStage.show();