package ptt.dalek.ui;

import ptt.dalek.github.Repository;
import ptt.dalek.gui.App;
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
	public static final double HEIGHT = UserPane.HEIGHT / 3;
	
	private Repository repository;
	private boolean isOpen = false;
	
	private App app;
	private VBox vbComponents;
	private RepositoryHeaderPane rpHeader;
	private RepositoryContentPane rpContent;

	public RepositoryPane(Repository repository, App app) {
		this.app = app;
		this.repository = repository;
		this.setId(repository.getFullName());
		this.getStyleClass().add("repositoryPane");
		setup();
		setData();
	}
		
	public void setup() {		
		
		setPrefHeight(HEIGHT);
		setMinHeight(HEIGHT);
			
		// initialize components	
		
		vbComponents = new VBox(4);
		vbComponents.setPadding(new Insets(4, 4, 4, 4));
		
		rpHeader = new RepositoryHeaderPane(repository.getName());
		rpContent = new RepositoryContentPane();
		rpContent.setContent(String.format("Repository: %s (%s)\nOwner: %s\nGithub: %s\nWatchers: %s\nOpen Issues: %s\nForks: %s\nSize: %dkb",
				repository.getName(), repository.getFullName(), repository.getOwner().getLogin(),
				repository.getUrl(), repository.getWatchers(), repository.getOpenIssues(), repository.getForksCount(), repository.getSize()
				));
		
		vbComponents.getChildren().addAll(rpHeader, rpContent);
		
		this.getChildren().add(vbComponents);
		
		this.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				if (!isOpen) {
					rpHeader.toggleIcon();
					
					final Timeline open = new Timeline( new KeyFrame(Duration.millis(200), 
														new KeyValue(minHeightProperty(), 10*HEIGHT)));
					open.setOnFinished(new EventHandler<ActionEvent>() {
							@Override
							public void handle(ActionEvent event) {
								rpContent.toggleVisibility();
							}
					});
					
					open.play();
				}
				else {
					rpContent.toggleVisibility();
					rpHeader.toggleIcon();
					final Timeline close = new Timeline(new KeyFrame(Duration.millis(150), 
														new KeyValue(minHeightProperty(), HEIGHT)));

					close.play();
				}
				
				isOpen = !isOpen;
				app.onToggleRepository(getId());
			}
		});
	}
		
	//set Data of repository panes
	public void setData() {
		//TODO set content on content
	}
}
