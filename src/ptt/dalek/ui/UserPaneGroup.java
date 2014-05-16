package ptt.dalek.ui;

import ptt.dalek.github.User;
import ptt.dalek.gui.App;
import javafx.geometry.Insets;
import javafx.scene.layout.VBox;

public class UserPaneGroup extends VBox {

	private static final int PADDING_TOP = 6;
	private static final int PADDING_LEFT = 6;
	private static final int PADDING_RIGHT = 6;
	private static final int PADDING_BOTTOM = 42;
	private static final int SPACING = 6;
	
	private UserPane selected;
	
	public UserPaneGroup() {
		this.setSpacing(SPACING);
		this.setPadding(new Insets(PADDING_TOP, PADDING_RIGHT, PADDING_BOTTOM, PADDING_LEFT));
	}
	
	public void addUser(User user, App app) {
		this.getChildren().add(new UserPane(user, app));
	}
	
	public UserPane getSelected() {
		return selected;
	}

	public void clear() {
		this.getChildren().clear();
		selected = null;
	}

	public void onSelectedChange(UserPane newSelection) {
		if(selected != null)
			selected.setHighlighted(false);

		selected = newSelection;
	}
}
