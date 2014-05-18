package ptt.dalek.ui;

import java.util.List;

import ptt.dalek.github.Commit;
import ptt.dalek.gui.App;
import javafx.geometry.Insets;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;

public class RepositoryContentPane extends Pane {
	private VBox vbComponents;
	private VBox vbCommits;
	private VBox vbInfo;
	
	private TitledLabelPair tlpName;
	private TitledLabelPair tlpFullName;
	private TitledLabelPair tlpOwner;
	private TitledLabelPair tlpUrl;
	private TitledLabelPair tlpWatchers;
	private TitledLabelPair tlpOpenIssues;
	private TitledLabelPair tlpForks;
	private TitledLabelPair tlpSize;
	
	private String fullName;
	
	private App app;
	
	public RepositoryContentPane(App app) {
		this.app = app;
		
		vbComponents = new VBox();
		vbCommits = new VBox(20);
		vbInfo = new VBox();
		init();
		vbCommits.setPadding(new Insets(20, 10, 10, 0));
		vbComponents.getChildren().addAll(vbInfo, vbCommits);
		this.getChildren().add(vbComponents);
		setVisible(false);
		
		fullName = "";
	}
	
	//initialize content pane and its children
	public void init() {
		tlpName = new TitledLabelPair(": ", "");
		tlpFullName = new TitledLabelPair("", "");
		tlpOwner = new TitledLabelPair(": ", "");
		tlpUrl = new TitledLabelPair(": ", "");
		tlpWatchers = new TitledLabelPair(": ", "");
		tlpOpenIssues = new TitledLabelPair(" Issues: ", "");
		tlpForks = new TitledLabelPair(": ", "");
		tlpSize = new TitledLabelPair(": ", "");
		
		vbInfo.getChildren().addAll(tlpName, tlpFullName, tlpOwner, tlpUrl, tlpWatchers, tlpOpenIssues, tlpForks, tlpSize);
	}
	
	//setting up the content pane with all information
	public void setInfo(String name, String fullName, String owner, String url, int watchers, int openIssues, int forks, int size) {
		tlpName.setText("Repository: ", name);
		tlpFullName.setText("", "(" + fullName + ")");
		tlpOwner.setText("Owner: ", owner);
		tlpUrl.setText("GitHub: ", url);
		tlpWatchers.setText("Watchers: ", String.valueOf(watchers));
		tlpOpenIssues.setText("Open Issues: ", String.valueOf(openIssues));
		tlpForks.setText("Forks: ", String.valueOf(forks));
		tlpSize.setText("Size: ", String.valueOf(size) + "kb");
		
		this.fullName = fullName;
	}
	
	public void setCommits(List<Commit> commits) {
		vbCommits.getChildren().clear();
		int size = commits.size();
		for(int i = 0; i < 10; i++) {
			if(i >= size)
				return;
			CommitPane temp = new CommitPane(commits.get(i));
			vbCommits.getChildren().add(temp);
			if(i == 9) {
				app.setNewUpdate(fullName, commits.get(i).getCommitData().getCommitter().getDate());
			}
		}
	}
	
	public void toggleVisibility() {
		setVisible(!visibleProperty().get());
	}
}
