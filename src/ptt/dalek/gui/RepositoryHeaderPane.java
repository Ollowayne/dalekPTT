package ptt.dalek.gui;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class RepositoryHeaderPane extends Pane {
	private HBox components;
		private Label repoName;
		
		private ImageView openStatus;
		private Image open;
		private Image closed;
		
		private boolean isOpen;
		
		String name;
		
	public RepositoryHeaderPane(String name) {
		//TEST VERSION
		this.name = name;
		
		setPrefHeight(RepositoryPane.HEIGHT);
		setMinHeight(RepositoryPane.HEIGHT);
		
		components = new HBox(4);
		repoName = new Label(name);
		components.setAlignment(Pos.CENTER);

		closed = new Image("file:res/closedLila.png");
		open = new Image("file:res/openLila.png");
		openStatus = new ImageView();
		openStatus.setImage(closed);
		
		components.getChildren().addAll(openStatus, repoName);

		this.getChildren().add(components);
	}
	
	public void toggleOpenStatus() {
		if(isOpen) {
			openStatus.setImage(closed);
		}
		else {
			openStatus.setImage(open);
		}
		
		isOpen = !isOpen;
	}
	
	
}
