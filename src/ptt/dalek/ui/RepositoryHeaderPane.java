package ptt.dalek.ui;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class RepositoryHeaderPane extends Pane {
	
	private static final String IMAGE_ARROW_OPENED = "file:res/open.png";
	private static final String IMAGE_ARROW_CLOSED = "file:res/closed.png";
	
	private HBox hbComponents;
	private TitledLabelPair lRepoName;
	
	private ImageView ivIcon;
	private Image iArrowOpen;
	private Image iArrowClosed;
	
	private boolean isOpened;
	
	private String name;
		
	public RepositoryHeaderPane(String name) {
		this.name = name;
		
		setPrefHeight(RepositoryPane.HEIGHT);
		setMinHeight(RepositoryPane.HEIGHT);
		
		hbComponents = new HBox(4);
		lRepoName = new TitledLabelPair("", name);
		hbComponents.setAlignment(Pos.CENTER);

		iArrowOpen = new Image(IMAGE_ARROW_OPENED);
		iArrowClosed = new Image(IMAGE_ARROW_CLOSED);

		ivIcon = new ImageView();
		ivIcon.setImage(iArrowClosed);
		
		hbComponents.getChildren().addAll(ivIcon, lRepoName);

		this.getChildren().add(hbComponents);
	}
	
	public void toggleIcon() {
		ivIcon.setImage(isOpened ? iArrowClosed : iArrowOpen);
		isOpened = !isOpened;
	}

	public String getName() {
		return name;
	}	
	
	public void markUpdated() {
		lRepoName.setText("NEW UPDATE!", name);
	}
	
	public void unmarkUpdated() {
		lRepoName.setText("", name);
	}
}
