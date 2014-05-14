package ptt.dalek.gui;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;

public class RepositoryContentPane extends Pane {
	private VBox components;
	private Label repoName;
	
	private boolean isVisible;

	public RepositoryContentPane() {
		components = new VBox();
		repoName = new Label("DEMO CONTENT");
		components.getChildren().addAll(repoName);
		this.getChildren().add(components);
		
		setInvisible();
	}
	
	public void invertOpacity() {
		if(isVisible) {
			setInvisible();
		}
		else {
			setVisible();
		}
		
		isVisible = !isVisible;
	}
	
	public void setInvisible() {
		setAllOpacity(0);
	}
	
	public void setVisible() {
		setAllOpacity(1);
	}
	
	private void setAllOpacity(int value) {
		this.setOpacity(value);
		repoName.setOpacity(value);
	}

}
