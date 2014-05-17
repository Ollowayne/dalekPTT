package ptt.dalek.ui;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import ptt.dalek.github.Commit;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.OverrunStyle;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class CommitPane extends Pane {
	private VBox components;
	private HBox row1;
	private HBox row2;
	private HBox row3;
	
	private Label lAuthor;
	private Label lMessage;
	private ImageView iCopySha;
	private Label lSha;
	private Label lDate;
	private Label lCommiter;
	
	private String sha;
	
	public CommitPane (Commit commit) {
		this.sha = commit.getSha();
		init();
		setData(commit);
	}
	
	private void init() {
		this.setMaxHeight(40);
		components = new VBox();
		lAuthor = new Label();
		lMessage = new Label();
		lSha = new Label();
		lDate = new Label();
		lCommiter = new Label();
		
		iCopySha = new ImageView(ClipboardPane.IMAGE_CLIPBOARD);
		iCopySha.setId("clickable_image");
		
		iCopySha.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				Toolkit.getDefaultToolkit().getSystemClipboard().setContents(
                    new StringSelection(sha), null);
			}
		});
	
		final CommitPane cp = this;
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				Tooltip.install(cp, new Tooltip("Copy to clipboard."));
			}
		});
		
		lAuthor.setId("commit_highlight");
		lMessage.setId("commit");
		lSha.setId("commit_pale");
		lDate.setId("commit");
		lCommiter.setId("commit_highlight");
		
		lMessage.setMaxWidth(400);
		lMessage.setMaxHeight(20);
		lMessage.setTextOverrun(OverrunStyle.ELLIPSIS);
		
		row1 = new HBox(5);
		row1.getChildren().addAll(lAuthor, lMessage);
		row2 = new HBox(5);
		row2.getChildren().addAll(lDate, lCommiter);
		row3 = new HBox(5);
		row3.getChildren().addAll(iCopySha, lSha);
		
		components.getChildren().addAll(row1, row2, row3);
		this.getChildren().add(components);
	}
	
	public void setData(Commit commit) {
		lAuthor.setText(commit.getCommitData().getAuthor().getName() + ":");
		lMessage.setText(commit.getCommitData().getMessage().replaceAll("/n", ""));
		
		lSha.setText("Sha: " + commit.getSha());
		Date date = new Date((long)commit.getCommitData().getCommitter().getDate()*1000);
		DateFormat formatter = new SimpleDateFormat();
		String dateString = "commited " + formatter.format( date );
		
		lDate.setText(dateString);
		
		lCommiter.setText("by " + commit.getCommitData().getCommitter().getName());
	}
}
