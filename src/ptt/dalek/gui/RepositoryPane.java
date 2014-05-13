package ptt.dalek.gui;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class RepositoryPane extends Pane {
	
	public static final double HEIGHT = UserPane.HEIGHT / 2;
	
	private String repoName;
	private Label l_repo;
	private Label l_info;
	private boolean isOpen = false;
	
	private RepositoryPane me;
	
	public RepositoryPane(String repoName) {
		this.repoName = repoName;
		this.setId("repositoryPane");
		setup();
		setData();
		
		me = this;
	}
		
		
	public void setup() {		
		
		setPrefHeight(HEIGHT);
		setMinHeight(HEIGHT);
			
		// initialize components
			
		l_repo = new Label();
		l_info = new Label();
		
		// needs reworking or redesign or re-alignment
		this.getChildren().add(l_repo);
		this.getChildren().add(l_info);
		
		
		this.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				if (!isOpen) {
				    final Timeline open = new Timeline();
				    final KeyValue kv = new KeyValue(me.minHeightProperty(), 5*HEIGHT);
				    final KeyFrame kf = new KeyFrame(Duration.millis(300), kv);
				    open.getKeyFrames().add(kf);
				    open.setOnFinished(new EventHandler<ActionEvent>() {
					    
				           @Override
				           public void handle(ActionEvent event) {
				        	   
				        	   l_repo.setText("v " +repoName);
				           }

				        });
				    
				    open.play();
				}
				else {
				    final Timeline close = new Timeline();
				    final KeyValue kv = new KeyValue(me.minHeightProperty(), HEIGHT);
				    final KeyFrame kf = new KeyFrame(Duration.millis(200), kv);
				    close.getKeyFrames().add(kf);
				    close.setOnFinished(new EventHandler<ActionEvent>() {
					    
				           @Override
				           public void handle(ActionEvent event) {
								
								l_repo.setText("> " +repoName);
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
		l_repo.setText("> " +repoName);
		l_info.setText("");
	
	}
}
