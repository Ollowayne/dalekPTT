package ptt.dalek.ui;

import java.util.List;

import ptt.dalek.github.Commit;
import javafx.geometry.Insets;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;

public class RepositoryContentPane extends Pane {
	private VBox vbComponents;
	private VBox vbCommits;
	private VBox vbInfo;
	
	private TitledLabelPair tlpName;
	private TitledLabelPair tlpFullName;
	private TitledLabelPair tlpowner;
	private TitledLabelPair tlpUrl;
	private TitledLabelPair tlpwatchers;
	private TitledLabelPair tlpOpenIssues;
	private TitledLabelPair tlpForks;
	private TitledLabelPair tlpSize;
	
	public RepositoryContentPane() {
		vbComponents = new VBox();
		vbCommits = new VBox(20);
		vbInfo = new VBox();
		vbCommits.setPadding(new Insets(20, 10, 10, 0));
		vbComponents.getChildren().addAll(vbInfo, vbCommits);
		this.getChildren().add(vbComponents);
		
		setVisible(false);
	}
	
	public void setInfo(String name, String fullName, String owner, String url, int watchers, int openIssues, int forks, int size) {
		tlpName = new TitledLabelPair("Repository: ", name);
		tlpFullName = new TitledLabelPair("", "(" + fullName + ")");
		tlpowner = new TitledLabelPair("Owner: ", owner);
		tlpUrl = new TitledLabelPair("GitHub: ", url);
		tlpwatchers = new TitledLabelPair("Watchers: ", String.valueOf(watchers));
		tlpOpenIssues = new TitledLabelPair("Open Issues: ", String.valueOf(openIssues));
		tlpForks = new TitledLabelPair("Forks: ", String.valueOf(forks));
		tlpSize = new TitledLabelPair("Size: ", String.valueOf(size));
		
		vbInfo.getChildren().addAll(tlpName, tlpFullName, tlpowner, tlpUrl, tlpwatchers, tlpOpenIssues, tlpForks, tlpSize);
	}
	
	public void setCommits(List<Commit> commits) {
		vbCommits.getChildren().clear();
		int size = commits.size();
		for(int i = 0; i < 10; i++) {
			if(i >= size)
				return;
			CommitPane temp = new CommitPane(commits.get(i));
			vbCommits.getChildren().add(temp);
		}
	}
	
	public void toggleVisibility() {
		setVisible(!visibleProperty().get());
	}
}
