package ptt.dalek.ui;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import ptt.dalek.github.Commit;
import javafx.scene.control.Label;
import javafx.scene.control.OverrunStyle;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class CommitPane extends Pane {
	private GridPane components;

	private Label lAuthor;
	private ImageView iCopyMessage;
	private Label lMessage;
	private ImageView iCopySha;
	private Label lSha;
	private Label lDate;
	private Label lCommiter;
	
	private Commit commit;
	
	public CommitPane (Commit commit) {
		init();
		setData(commit);
		this.commit = commit;
	}
	
	private void init() {
		this.setMaxHeight(40);
		components = new GridPane();
		lAuthor = new Label();
		lMessage = new Label();
		lMessage.setMaxWidth(100);
		lMessage.setMaxHeight(20);
		lMessage.setTextOverrun(OverrunStyle.ELLIPSIS);
		
		lSha = new Label();
		lSha.setMaxWidth(50);
		lSha.setTextOverrun(OverrunStyle.ELLIPSIS);
		lDate = new Label();
		lCommiter = new Label();
		
		GridPane.setConstraints(lAuthor, 0, 0);
		GridPane.setConstraints(lMessage, 0, 1);
		GridPane.setConstraints(lDate, 1, 0);
		GridPane.setConstraints(lCommiter, 1, 0);
		
		
		components.getChildren().addAll(lAuthor, lMessage, lDate, lCommiter, lSha);
		this.getChildren().add(components);
	}
	
	public void setData(Commit commit) {
		lAuthor.setText(commit.getCommitData().getAuthor().getName() + ": ");
		lMessage.setText(commit.getCommitData().getMessage().replaceAll("/n", ""));
		
		lSha.setText(commit.getSha());
		Date date = new Date((long)commit.getCommitData().getCommitter().getDate()*1000);
		DateFormat formatter = new SimpleDateFormat();
		String dateString = "commited " + formatter.format( date );
		
		lDate.setText(dateString);
		
		lCommiter.setText(" by " + commit.getCommitData().getCommitter().getName());
	}
}
