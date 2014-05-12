package Interface;

import Main.Client;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class UserPane extends Pane {
	
		// width = 4*x
	public static final double WIDTH = 200;
	public static final double HEIGHT = 60;
	
	private String userName;
	private App main;

	UserPane me;
	// note that max user name length is 39 characters
	private Label l_name;
	private Label l_data;
	private Button b_remove;
//	private ImageView i_user;
	private GridPane contents;
	
	public UserPane(String userName, App main ) {
		this.userName = userName;
		this.main = main;
		me = this;
		this.setId("user_pane");
		setup();
		setData();

		// example, remove later
//        userListSP.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
//
//            @Override
//            public void handle(MouseEvent event) {
//                System.out.println(">> Mouse Clicked");
//                event.consume();
//            }
//        });
	}
	
	public void setup() {		
		
		// setup size
		setPrefWidth(WIDTH);
		setMinWidth(WIDTH);
		setPrefHeight(HEIGHT);
		setMinHeight(HEIGHT);
		
		// initialize components
		contents = new GridPane();	
		l_name = new Label();
		l_name.setMinWidth(WIDTH);
		l_data = new Label();
		b_remove = new Button("x");
		b_remove.setId("remove_userpane_button");
        b_remove.setOnAction(new EventHandler<ActionEvent>() {
        
           @Override
           public void handle(ActionEvent event) {
        	   removeUser();
           }

        });

        GridPane.setConstraints(l_name, 1, 2);
        GridPane.setConstraints(b_remove, 1, 1);
        GridPane.setConstraints(l_data, 1, 3);
        contents.getChildren().addAll(l_name, b_remove, l_data);
        
        GridPane.setHalignment(b_remove, HPos.RIGHT);
        
        this.setOnMousePressed(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				main.loadContent(userName);
			}
        });
        
        getChildren().add(contents);
	}
	
	public void setData() {
		l_name.setText("  " + userName);
		l_data.setText("  some sample data");
	}
	
	public String getUserName() {
		return userName;
	}
	
	private void removeUser() {
	    Client.getInstance().removeWatchedUser(userName);
	   
	    final VBox parentbox = (VBox) getParent();
	   
		final Timeline timeline = new Timeline();
		final KeyValue kv = new KeyValue(me.layoutXProperty(), -UserPane.WIDTH);
		final KeyFrame kf = new KeyFrame(Duration.millis(200), kv);
		timeline.getKeyFrames().add(kf);
	    timeline.setOnFinished(new EventHandler<ActionEvent>() {
	    
	           @Override
	           public void handle(ActionEvent event) {
	        	   parentbox.getChildren().remove(me);
	        	   main.unloadContent();
	        	   main.displayMessage("You are not observing " + userName + " any longer.");
	           }

	        });
	    
	    timeline.play();
	}
}
