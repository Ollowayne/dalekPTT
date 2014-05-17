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
import javafx.scene.layout.VBox;

public class CommitPane extends Pane {
	private VBox components;
	private HBox row1;
	private HBox row2;
	
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
		components = new VBox();
		lAuthor = new Label();
		lMessage = new Label();
		lSha = new Label();
		lDate = new Label();
		lCommiter = new Label();
		
		lMessage.setMaxWidth(400);
		lMessage.setMaxHeight(20);
		lMessage.setTextOverrun(OverrunStyle.ELLIPSIS);
		lSha.setMaxWidth(50);
		lSha.setTextOverrun(OverrunStyle.ELLIPSIS);
		
		row1 = new HBox(5);
		row1.getChildren().addAll(lAuthor, lMessage);
		row2 = new HBox(5);
		row2.getChildren().addAll(lDate, lCommiter);
		
		components.getChildren().addAll(row1, row2);
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
		
		lCommiter.setText("by " + commit.getCommitData().getCommitter().getName());
	}
}
