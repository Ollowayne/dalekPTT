package ptt.dalek.ui;

import ptt.dalek.gui.App;
import ptt.dalek.main.Client;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.OverrunStyle;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class UserPane extends Pane {
	public static final double WIDTH = 270;
	public static final double HEIGHT = 90;
	public static final double CONTENTS_HGAB = 2;
	public static final double CONTENTS_VGAB = 2;
	
	private String userName;
	private App app;

	UserPane me;
	private Label l_name;
		// rename
	private Label l_data1;
	private Label l_data2;
	private Label l_data3;
	
	private Button b_remove;
	//private ImageView i_user;
	private GridPane contents;
	
	private boolean isHighlighted;
	
	public UserPane(String userName, App main) {
		this.userName = userName;
		this.app = main;
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
		l_name.setMaxWidth(WIDTH);
		l_name.setId("l_name");
		l_name.setTextOverrun(OverrunStyle.ELLIPSIS);
		
		l_data1 = new Label();
		l_data2 = new Label();
		l_data3 = new Label();
		l_data1.setId("l_data");
		l_data2.setId("l_data");
		l_data3.setId("l_data");
		
		l_data1.setMaxWidth(WIDTH);
		l_data2.setMaxWidth(WIDTH);
		l_data3.setMaxWidth(WIDTH);
		
		l_data1.setTextOverrun(OverrunStyle.ELLIPSIS);
		l_data2.setTextOverrun(OverrunStyle.ELLIPSIS);
		l_data3.setTextOverrun(OverrunStyle.ELLIPSIS);
		
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
        GridPane.setConstraints(l_data1, 1, 3);
        GridPane.setConstraints(l_data2, 1, 4);
        GridPane.setConstraints(l_data3, 1, 5);
        
        contents.getChildren().addAll(l_name, b_remove, l_data1, l_data2, l_data3);
        
        GridPane.setHalignment(b_remove, HPos.RIGHT);
        
        this.setOnMousePressed(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				app.loadContent(userName);
				me.setHighlighted(true);
			}
        });
        
        getChildren().add(contents);
	}
	
	private void setData() {
		l_name.setText("  " + userName);
		l_data1.setText("  Real Name");
		l_data2.setText("  www.somesamplestuff.example");
		l_data3.setText("  more sample data, shows how the overrunStyle labels work");
	}
	
	public void update(String userName) {
		this.userName = userName;
		
		setData();
	}
	
	public String getUserName() {
		return userName;
	}
	
	private void removeUser() {
	    Client.getInstance().removeWatchedUser(userName);
	   
	    final VBox parentbox = (VBox)getParent();
	   
		final Timeline timeline = new Timeline(new KeyFrame(Duration.millis(300), 
			new KeyValue(me.layoutXProperty(), -UserPane.WIDTH), 
			new KeyValue(me.opacityProperty(), 0))
		);
	    timeline.setOnFinished(new EventHandler<ActionEvent>() {
	    
	           @Override
	           public void handle(ActionEvent event) {
	        	   parentbox.getChildren().remove(me);
	        	   app.unloadContent();
	        	   app.topbarHint.displayMessage(String.format(App.STOP_WATCHING_STRING, userName));
	           }

	        });
	    
	    timeline.play();
	}
	
	public boolean getHighlighted() {
		return isHighlighted;
	}
	
	public void setHighlighted(boolean value) {
		isHighlighted = value;
	}
	
	public void highlight() {
		if(isHighlighted) {
			setStyle("-fx-border-color: #e880e7");
		}
		else {
			setStyle("-fx-border-color: #9c339a");
		}
	}
}
