package ptt.dalek.gui;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
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
					header.toggleOpenStatus();
					
				    final Timeline open = new Timeline(new KeyFrame(Duration.millis(200), 
				    	new KeyValue(me.minHeightProperty(), 5*HEIGHT))
				    );
				    open.setOnFinished(new EventHandler<ActionEvent>() {
					    
				           @Override
				           public void handle(ActionEvent event) {
				        	   content.invertOpacity();
				           }

				        });
				    
				    open.play();
				}
				else {
					content.invertOpacity();
		        	 header.toggleOpenStatus();
				    final Timeline close = new Timeline(new KeyFrame(Duration.millis(150), 
				    		new KeyValue(me.minHeightProperty(), HEIGHT))
				    );

				    close.play();
				}
				
				isOpen = !isOpen;
			}
	    });
	}
		
	//set Data of repository panes
	public void setData() {
		//TODO set content on content
	}
}
