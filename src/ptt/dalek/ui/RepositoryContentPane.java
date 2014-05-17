package ptt.dalek.ui;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import ptt.dalek.github.Commit;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;

public class RepositoryContentPane extends Pane {
	public static final String COMMIT_STRING = "\nAuthor: %s\nMessage: %s\nSha: %s \n\nCommited %s by %s.";
	private VBox vbComponents;
	//private VBox vbCommits;
	private Label lRepositoryName;
	private Label lCommits;
	private String commitsString;
	
	public RepositoryContentPane() {
		vbComponents = new VBox();
		lCommits = new Label();
		lRepositoryName = new Label();
		vbComponents.getChildren().addAll(lRepositoryName, lCommits);
		this.getChildren().add(vbComponents);
		
		setVisible(false);
	}
	
	public void setContent(String content) {
		lRepositoryName.setText(content);
	}
	
	public void setCommits(List<Commit> commits) {
		for(Commit c : commits) {
			commitsString += newCommit(c);
		}
		lCommits.setText(commitsString);
	}
	
	private String newCommit(Commit commit) {
		String result = "\n - - -";
		Date date = new Date((long)commit.getCommitData().getCommitter().getDate()*1000);
		DateFormat formatter = new SimpleDateFormat();
		String dateString = formatter.format( date );
		result += String.format(COMMIT_STRING, 
				commit.getCommitData().getAuthor().getName(), 
				commit.getCommitData().getMessage(), 
				commit.getSha(),
				dateString,
				commit.getCommitData().getCommitter().getName());
		return result;
	}
	
	public void toggleVisibility() {
		setVisible(!visibleProperty().get());
	}
}
