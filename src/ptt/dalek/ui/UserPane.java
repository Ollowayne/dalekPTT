package ptt.dalek.ui;

import ptt.dalek.gui.App;
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
	
	private static final String DELETE_BUTTON_TEXT = "x";
	
	private String userName;
	private App app;

	private Label lLogin;
		// rename
	private Label lFullName;
	private Label lEmail;
	private Label lWebsite;
	
	private Button bDelete;
	//private ImageView i_user;
	private GridPane gpContents;
	
	private boolean isHighlighted;
	
	public UserPane(String userName, App app) {
		this.userName = userName;
		this.app = app;
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
		gpContents = new GridPane();	
		lLogin = new Label();
		lLogin.setMaxWidth(WIDTH);
		lLogin.setId("l_name");
		lLogin.setTranslateX(6);
		lLogin.setTextOverrun(OverrunStyle.ELLIPSIS);
		
		lFullName = new Label();
		lEmail = new Label();
		lWebsite = new Label();
		lFullName.setId("l_data");
		lEmail.setId("l_data");
		lWebsite.setId("l_data");
		
		lFullName.setMaxWidth(WIDTH);
		lEmail.setMaxWidth(WIDTH);
		lWebsite.setMaxWidth(WIDTH);
		
		lFullName.setTextOverrun(OverrunStyle.ELLIPSIS);
		lEmail.setTextOverrun(OverrunStyle.ELLIPSIS);
		lWebsite.setTextOverrun(OverrunStyle.ELLIPSIS);
		
		bDelete = new Button(DELETE_BUTTON_TEXT);
		bDelete.setId("remove_userpane_button");
        bDelete.setOnAction(new EventHandler<ActionEvent>() {
           @Override
           public void handle(ActionEvent event) {
        	   removeUser();
           }
        });

        GridPane.setConstraints(lLogin, 1, 2);
        GridPane.setConstraints(bDelete, 1, 1);
        GridPane.setConstraints(lFullName, 1, 3);
        GridPane.setConstraints(lEmail, 1, 4);
        GridPane.setConstraints(lWebsite, 1, 5);
        GridPane.setHalignment(bDelete, HPos.RIGHT);
        
        gpContents.getChildren().addAll(lLogin, bDelete, lFullName, lEmail, lWebsite);
        
        setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				app.onSelectUser(userName);
				setHighlighted(true);
			}
        });
        
        getChildren().add(gpContents);
	}
	
	private void setData() {
		lLogin.setText(userName);
		lFullName.setText("  Real Name");
		lEmail.setText("  www.somesamplestuff.example");
		lWebsite.setText("  more sample data, shows how the overrunStyle labels work");
	}
	
	public void update(String userName) {
		this.userName = userName;
		setData();
	}
	
	public String getUserName() {
		return userName;
	}
	
	private void removeUser() {
	    app.onRemoveUser(userName);

		final UserPane self = this;
		final Timeline timeline = new Timeline (new KeyFrame(Duration.millis(300), 
												new KeyValue(layoutXProperty(), -WIDTH), 
												new KeyValue(opacityProperty(), 0)));

	    timeline.setOnFinished(new EventHandler<ActionEvent>() {
	           @Override
	           public void handle(ActionEvent event) {
	        	   ((VBox)self.getParent()).getChildren().remove(self);
	           }
	    });
	    timeline.play();
	}
	
	public boolean getHighlighted() {
		return isHighlighted;
	}
	
	public void setHighlighted(boolean value) {
		isHighlighted = value;
		updateHighlight();
	}
	
	public void updateHighlight() {
		if(isHighlighted) {
			setStyle("-fx-border-color: #e880e7");
			return;
		}
		
		setStyle("-fx-border-color: #9c339a");
	}
}
