package ptt.dalek.gui;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class RepositoryPane extends Pane {
	
	public static final double height=30;
	public static final double width=500;
	
	private String repoName;
	private Label l_repo;
	private Label l_info;
	private boolean isClicked = false; //to enable reclickable pane for sizing
	
	RepositoryPane rep;
	
	public RepositoryPane(String repoName) {
		this.repoName = repoName;
		rep = this;
		this.setId("repo_pane");
		setup();
		setData();
	}
		
		
	public void setup() {		
			
		// setup size
		//setPrefWidth(width);
		//setMinWidth(width);
		setPrefHeight(height);
		setMinHeight(height);
			
		// initialize components
			
		l_repo = new Label();
		l_repo.setMaxWidth(width);
		l_info = new Label();
		l_info.setMaxWidth(width);
		
		// just for testing and visibility
		rep.setStyle("-fx-border-color: black");
		//rep.setStyle("-fx-background-color: white");
		
		// needs reworking or redesign or re-alignment
		rep.getChildren().add(l_repo);
		rep.getChildren().add(l_info);
		
		
		this.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				if (!isClicked) {
					setPrefHeight(2*height);
					setMinHeight(2*height);
					
					l_repo.setText("v " +repoName);
					isClicked = true;
				}
				else {
					setPrefHeight(height);
					setMinHeight(height);
					
					l_repo.setText("> " +repoName);
					isClicked = false;
				}
			}
	    });
	}
		
		//set Data of repository panes
	public void setData() {
		l_repo.setText("> " +repoName);
		// testing
		l_info.setText("Just some sample data having fun. Is it working?");
	
	}
}
