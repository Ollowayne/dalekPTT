package ptt.dalek.ui;

import java.util.List;

import ptt.dalek.github.Commit;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;

public class RepositoryContentPane extends Pane {
	private VBox vbComponents;
	private VBox vbCommits;
	private Label lRepositoryName;
	
	public RepositoryContentPane() {
		vbComponents = new VBox();
		vbCommits = new VBox(20);
		vbCommits.setPadding(new Insets(20, 10, 10, 0));
		lRepositoryName = new Label();
		lRepositoryName.setId("commit");
		vbComponents.getChildren().addAll(lRepositoryName, vbCommits);
		this.getChildren().add(vbComponents);
		
		setVisible(false);
	}
	
	public void setContent(String content) {
		lRepositoryName.setText(content);
	}
	
	public void setCommits(List<Commit> commits) {
		vbCommits.getChildren().clear();
		int size = commits.size();
		for(int i = size-1; i >= size-10; i--) {
			if(i<0)
				return;
			CommitPane temp = new CommitPane(commits.get(i));
			vbCommits.getChildren().add(temp);
		}
	}
	
	public void toggleVisibility() {
		setVisible(!visibleProperty().get());
	}
}
