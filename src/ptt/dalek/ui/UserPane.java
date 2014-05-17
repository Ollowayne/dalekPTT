package ptt.dalek.ui;

import ptt.dalek.github.User;
import ptt.dalek.gui.App;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.scene.Parent;
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

	private App app;

	private Label lLogin;
	private Label lFullName;
	private ClipboardPane cpEmail;
	private ClipboardPane cpWebsite;

	private Button bDelete;
	private GridPane gpContents;
	private VBox userData;

	private boolean isHighlighted;
	private User user;

	public UserPane(User user, App app) {
		this.app = app;
		this.user = user;
		this.getStyleClass().add("userPane");
		
		setup();
		update(user);
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
		lLogin.setMinWidth(WIDTH);
		lLogin.setId("l_name");
		lLogin.setTranslateX(6);
		lLogin.setTextOverrun(OverrunStyle.ELLIPSIS);

		lFullName = new Label();
		cpEmail = new ClipboardPane("Email", app);
		cpWebsite = new ClipboardPane("Website", app);
		
		lFullName.setId("l_data");

		lFullName.setMaxWidth(WIDTH);
		cpEmail.setMaxWidth(WIDTH);
		cpWebsite.setMaxWidth(WIDTH);
		
		lFullName.setTranslateX(10);
		cpEmail.setTranslateX(10);
		cpWebsite.setTranslateX(10);
		
		lFullName.setTextOverrun(OverrunStyle.ELLIPSIS);

		bDelete = new Button(DELETE_BUTTON_TEXT);
		bDelete.getStyleClass().add("userPaneButton");
		bDelete.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				removeUser();
			}
		});
		
		userData = new VBox();
		GridPane.setConstraints(lLogin, 1, 2);
		GridPane.setConstraints(bDelete, 1, 1);	
		GridPane.setConstraints(userData, 1, 3);
		
		GridPane.setHalignment(bDelete, HPos.RIGHT);
		
		gpContents.getChildren().addAll(lLogin, bDelete, userData);

		setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				app.onSelectUser(user.getLogin());
				setHighlighted(true);
			}
		});

		getChildren().add(gpContents);
	}

	public void update(User user) {
		this.setId(user.getLogin());
		this.user = user;
		this.lLogin.setText(user.getLogin());
		updateUserData(user.getName(), user.getEmail(), user.getBlog());
	}

	private void removeUser() {
		app.onRemoveUser(user.getLogin());

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
		if(value) {
			//Update highlight for UserPaneGroup
			Parent parent = getParent();
			if(parent instanceof UserPaneGroup) {
				UserPaneGroup group = (UserPaneGroup)parent;
				group.onSelectedChange(this);
			}
		}

		isHighlighted = value;
		updateHighlight();
	}

	public User getUser() {
		return user;
	}
	
	public void updateHighlight() {
		if(isHighlighted) {
			setStyle("-fx-border-color: #e880e7");
			return;
		}
		setStyle("-fx-border-color: #9c339a");
	}
	
	private void updateUserData(String name, String email, String website) {
		userData.getChildren().clear();
		if(!name.equals("")) {
				userData.getChildren().add(lFullName);
				lFullName.setText("Name: " + name);
		}
		if(!email.equals("")) {
			userData.getChildren().add(cpEmail);
			cpEmail.updateData(email);
		}
		if(!website.equals("")) {
			userData.getChildren().add(cpWebsite);
			cpWebsite.updateData(website);
		}
	}
}
