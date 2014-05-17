package ptt.dalek.ui;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class TitledLabelPair extends HBox {
	private Label lTitle;
	private Label lContent;
	
	public TitledLabelPair(String title, String content) {
		lTitle = new Label(title);
		lContent = new Label(content);
		lTitle.setId("commit_highlight");
		this.getChildren().addAll(lTitle, lContent);
	}
}
