package ptt.dalek.gui;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class RepositoryPane extends Pane {
	
	public static final double HEIGHT = UserPane.HEIGHT / 2;
	
	private String name;
	private boolean isOpen = false;
	
	private VBox components;
	private RepositoryHeaderPane header;
	private RepositoryContentPane content;
	
	private RepositoryPane me;
	
	public RepositoryPane(String repoName) {
		this.name = repoName;
		this.setId("repositoryPane");
		setup();
		setData();
		
		me = this;
	}
		
		
	public void setup() {		
		
		setPrefHeight(HEIGHT);
		setMinHeight(HEIGHT);
			
		// initialize components	
		
		components = new VBox(4);
		components.setPadding(new Insets(4, 4, 4, 4));
		
		header = new RepositoryHeaderPane(name);
		content = new RepositoryContentPane();
		
		components.getChildren().addAll(header, content);
		
		this.getChildren().add(components);
		
		this.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				if (!isOpen) {
				    final Timeline open = new Timeline();
				    final KeyValue kv = new KeyValue(me.minHeightProperty(), 5*HEIGHT);
				    final KeyFrame kf = new KeyFrame(Duration.millis(200), kv);
				    open.getKeyFrames().add(kf);
				    open.setOnFinished(new EventHandler<ActionEvent>() {
					    
				           @Override
				           public void handle(ActionEvent event) {
				        	   content.invertOpacity();
				        	   header.toggleOpenStatus();
				           }

				        });
				    
				    open.play();
				}
				else {
					content.invertOpacity();
					
				    final Timeline close = new Timeline();
				    final KeyValue kv = new KeyValue(me.minHeightProperty(), HEIGHT);
				    final KeyFrame kf = new KeyFrame(Duration.millis(150), kv);
				    close.getKeyFrames().add(kf);
				    close.setOnFinished(new EventHandler<ActionEvent>() {
					    
				           @Override
				           public void handle(ActionEvent event) {
				        	   header.toggleOpenStatus();
				           }

				        });
				    close.play();
				}
				isOpen = !isOpen;
			}
	    });
	}
		
		//set Data of repository panes
	public void setData() {
		
	}
}
