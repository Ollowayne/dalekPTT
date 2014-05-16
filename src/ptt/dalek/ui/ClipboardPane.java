package ptt.dalek.ui;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;

import ptt.dalek.gui.App;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.OverrunStyle;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class ClipboardPane extends Pane {
	private static final String IMAGE_CLIPBOARD = "file:res/clipboard.png";
	
	private Label lData;
	private ImageView iIcon;
	
	private String key;
	private String data;
	private App app;
	
	private HBox components;
	
	public ClipboardPane(String key, App app) {
		this.key = key;
		this.app = app;
		this.data = "";
		
		setup();
		updateData(this.data);
	}
	
	private void setup() {
		components = new HBox(5);
		lData = new Label();
		lData.setTextOverrun(OverrunStyle.ELLIPSIS);
		this.iIcon = new ImageView(IMAGE_CLIPBOARD);
		components.getChildren().addAll(iIcon, lData);
		
		iIcon.setId("clickable_image");
		lData.setId("l_data");
		
		iIcon.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				if(!data.equals("")) {
					app.onCopyToClipbaord(data);
					Toolkit.getDefaultToolkit().getSystemClipboard().setContents(
                        new StringSelection(data), null);
				}
			}
		});
	
		final ClipboardPane cp = this;
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				Tooltip.install(cp, new Tooltip("Copy to clipboard."));
			}
		});
		
		this.getChildren().add(components);
	}
	
	public void updateData(String data) {
		lData.setText(key + ": " + data);
		this.data = data;
	}
}
