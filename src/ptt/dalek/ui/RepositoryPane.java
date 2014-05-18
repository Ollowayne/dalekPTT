package ptt.dalek.ui;

import java.util.List;

import ptt.dalek.github.Commit;
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
	public static final double HEIGHT = UserPane.HEIGHT / 4;
	private double openedHeight;
	
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
		
		this.setMinHeight(HEIGHT);
	}
		
	public void setup() {		
		
		setPrefHeight(HEIGHT);
		setMinHeight(HEIGHT);
			
		// initialize components	
		
		vbComponents = new VBox(4);
		vbComponents.setPadding(new Insets(4, 4, 4, 4));
		
		rpHeader = new RepositoryHeaderPane(repository.getName());
		rpContent = new RepositoryContentPane(app);
		
		vbComponents.getChildren().addAll(rpHeader, rpContent);
		
		this.getChildren().add(vbComponents);
		
		this.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				rpHeader.toggleIcon();
				rpHeader.setUpdated(app.isUpdated(repository.getFullName()));
				app.resetUpdate(repository.getFullName());
				//animation for opening the pane
				if (!isOpen) {			
					final Timeline open = new Timeline( new KeyFrame(Duration.millis(200), 
														new KeyValue(minHeightProperty(), openedHeight)));
					open.setOnFinished(new EventHandler<ActionEvent>() {
							@Override
							public void handle(ActionEvent event) {
								rpContent.toggleVisibility();
							}
					});
					
					open.play();
				}
				//makes the invisible content visible for the open pane
				else {
					rpContent.toggleVisibility();
					final Timeline close = new Timeline(new KeyFrame(Duration.millis(150), 
														new KeyValue(minHeightProperty(), HEIGHT)));

					close.play();
				}
				
				isOpen = !isOpen;
				app.onToggleRepository(getId());
			}
		});
		
		rpHeader.setUpdated(app.isUpdated(repository.getFullName()));
	}
	
	public void updateCommits() {
		final List<Commit> commits = app.getMyCommits(getId());
		//adjusts size of opened pane according to existing commits
		if(commits.size() > 9) {
			openedHeight = 8*HEIGHT + 10 * 60;
		}
		else {
			openedHeight = 8*HEIGHT + commits.size() * 60;
		}
		rpContent.setCommits(commits);
		rpHeader.setUpdated(app.isUpdated(repository.getFullName()));
	}
	
	public void setData() {
		rpContent.setInfo(repository.getName(), repository.getFullName(), repository.getOwner().getLogin(),
				repository.getUrl(), repository.getWatchers(), repository.getOpenIssues(), repository.getForksCount(), repository.getSize());
		
		updateCommits();
	}
	
	public void update(Repository repository) {
		this.repository = repository;
		setData();
		rpHeader.setUpdated(app.isUpdated(repository.getFullName()));
	}
}
