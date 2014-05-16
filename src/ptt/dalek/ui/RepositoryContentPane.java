package ptt.dalek.ui;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;

public class RepositoryContentPane extends Pane {
	private VBox vbComponents;
	private Label lRepositoryName;
	
	public RepositoryContentPane() {
		vbComponents = new VBox();
		lRepositoryName = new Label("DEMO CONTENT");
		vbComponents.getChildren().addAll(lRepositoryName);
		this.getChildren().add(vbComponents);
		
		setVisible(false);
	}
	
	public void toggleVisibility() {
		setVisible(!visibleProperty().get());
	}
}
