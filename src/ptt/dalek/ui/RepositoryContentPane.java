package ptt.dalek.ui;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;

public class RepositoryContentPane extends Pane {
	private VBox vbComponents;
	private Label lRepositoryName;
	
	public RepositoryContentPane() {
		vbComponents = new VBox();
		lRepositoryName = new Label();
		vbComponents.getChildren().addAll(lRepositoryName);
		this.getChildren().add(vbComponents);
		
		setVisible(false);
	}
	
	public void setContent(String content) {
		lRepositoryName.setText(content);
	}
	
	public void toggleVisibility() {
		setVisible(!visibleProperty().get());
	}
}
