package ptt.dalek.ui;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;

import ptt.dalek.github.User;
import ptt.dalek.gui.App;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.OverrunStyle;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
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

	private static final String IMAGE_CLIPBOARD = "file:res/clipboard.png";
	
	private static final String DELETE_BUTTON_TEXT = "x";

	private App app;

	private Label lLogin;
	private Label lFullName;
	private Label lEmail;
	private Label lWebsite;
	private ImageView iCopyWebsite;
	private ImageView iCopyMail;

	private Button bDelete;
	private GridPane gpContents;

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
		lEmail = new Label();
		lWebsite = new Label();
		
		lFullName.setId("l_data");
		lEmail.setId("l_data");
		lWebsite.setId("l_data");

		lFullName.setMaxWidth(WIDTH);
		lEmail.setMaxWidth(WIDTH);
		lWebsite.setMaxWidth(WIDTH);
		
		lFullName.setTranslateX(10);
		lEmail.setTranslateX(25);
		lWebsite.setTranslateX(25);
		
		lFullName.setTextOverrun(OverrunStyle.ELLIPSIS);
		lEmail.setTextOverrun(OverrunStyle.ELLIPSIS);
		lWebsite.setTextOverrun(OverrunStyle.ELLIPSIS);

		bDelete = new Button(DELETE_BUTTON_TEXT);
		bDelete.getStyleClass().add("userPaneButton");
		bDelete.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				removeUser();
			}
		});
		
		iCopyWebsite = new ImageView(IMAGE_CLIPBOARD);
		iCopyWebsite.setTranslateX(10);
		iCopyWebsite.setId("clickable_image");
		
		iCopyMail = new ImageView(IMAGE_CLIPBOARD);
		iCopyMail.setTranslateX(10);
		iCopyMail.setId("clickable_image");
		
		iCopyWebsite.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				String blog = user.getBlog();
				if(blog != "") {
					app.onCopyToClipbaord(blog);
					Toolkit.getDefaultToolkit().getSystemClipboard().setContents(
                        new StringSelection(blog), null);
				}
			}
		});
		
		iCopyMail.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				String mail = user.getEmail();
				if(mail != "") {
					app.onCopyToClipbaord(mail);
					Toolkit.getDefaultToolkit().getSystemClipboard().setContents(
                        new StringSelection(mail), null);
				}
			}
		});
		
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				Tooltip.install(iCopyWebsite, new Tooltip("Copy URL to clipboard."));
				Tooltip.install(iCopyMail, new Tooltip("Copy address to clipboard."));
			}
		});

		GridPane.setConstraints(lLogin, 1, 2);
		GridPane.setConstraints(bDelete, 1, 1);
		GridPane.setConstraints(lFullName, 1, 3);
		GridPane.setConstraints(lEmail, 1, 4);
		GridPane.setConstraints(iCopyMail, 1, 4);
		GridPane.setConstraints(lWebsite, 1, 5);
		GridPane.setConstraints(iCopyWebsite, 1, 5);
		GridPane.setHalignment(bDelete, HPos.RIGHT);

		gpContents.getChildren().addAll(lLogin, bDelete, lFullName, lEmail, lWebsite, iCopyWebsite, iCopyMail);

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
		this.lFullName.setText("Name: " + user.getName());
		this.lEmail.setText("Email: " + user.getEmail());
		this.lWebsite.setText("Website: " + user.getBlog());
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
}
