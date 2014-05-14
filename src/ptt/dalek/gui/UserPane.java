package ptt.dalek.gui;

import ptt.dalek.main.Client;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class UserPane extends Pane {
	public static final double WIDTH = 200;
	public static final double HEIGHT = 60;
	public static final double CONTENTS_HGAB = 2;
	public static final double CONTENTS_VGAB = 2;
	
	private String userName;
	private App main;

	UserPane me;
	private Label l_name;
	private Label l_data;
	private Button b_remove;
	//private ImageView i_user;
	private GridPane contents;
	
	public UserPane(String userName, App main) {
		this.userName = userName;
		this.main = main;
		me = this;
		this.setId("user_pane");
		setup();
		setData();
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
	   
		final Timeline timeline = new Timeline(new KeyFrame(Duration.millis(300), 
			new KeyValue(me.layoutXProperty(), -UserPane.WIDTH), 
			new KeyValue(me.opacityProperty(), 0))
		);
	    timeline.setOnFinished(new EventHandler<ActionEvent>() {
	    
	           @Override
	           public void handle(ActionEvent event) {
	        	   parentbox.getChildren().remove(me);
	        	   main.unloadContent();
	        	   main.message.displayMessage("You are not observing " + userName + " any longer.");
	           }

	        });
	    
	    timeline.play();
	}
}
