package ptt.dalek.gui;

import java.awt.Point;
import java.util.LinkedList;
import java.util.Queue;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class Messenger {
	public class QueuedMessage {
		public String message;
		public int duration;
		public int fadeTime;
		public QueuedMessage(String message, int duration, int fadeTime) {
			this.message = message;
			this.duration = duration;
			this.fadeTime = fadeTime;
		}
	}
	
	private Pane parent;
	private Label label;
	private int duration;
	private int durationFade;
	private boolean isDisplaying = false;

	Queue<QueuedMessage> queue = new LinkedList<QueuedMessage>();
	
	public Messenger(Pane parent, Point offset, int duration, int durationFade) {
		this.parent = parent;
		this.duration = duration;
		this.durationFade = durationFade;
		
		this.label = new Label();
		this.label.setTranslateX(offset.x);
		this.label.setTranslateY(offset.y);
		this.label.setTranslateZ(10);
		this.label.setId("addResponse");
        GridPane.setConstraints(this.label, 2, 0);
		this.parent.getChildren().add(this.label);
	}
	
	public void clearMessage() {
		this.label.setText("");
	}
	
	public void displayMessage(String message) {
		displayMessage(message, duration, durationFade);
	}
	
	public void displayMessage(String message, int duration, int fadeTime) {
		if(!isDisplaying) {
			display(message, duration, fadeTime);
			return;
		}
		
		queue.offer(new QueuedMessage(message, duration, fadeTime));
	}
	
	private void update() {
		if(isDisplaying)
			return;
		
		if(queue.isEmpty())
			return;
		
		QueuedMessage nextMessage = queue.poll();
		displayMessage(nextMessage.message, nextMessage.duration, nextMessage.fadeTime);
	}
	
	private void display(String message, int durationShow, int durationFade) {
		this.label.setText(message);
    	this.label.setOpacity(1);
    	this.isDisplaying = true;
    	
	    final Timeline tMessageFade = new Timeline(
			    new KeyFrame(
				    Duration.millis(durationFade),
				    new EventHandler<ActionEvent>() {
				    @Override public void handle(ActionEvent actionEvent) {
				    	label.setText("");
				    	label.setOpacity(1);
				    	isDisplaying = false;
				    	update();
				    }
			    }
				, new KeyValue(label.opacityProperty(), 0)));
	    
	    final Timeline tMessageShow = new Timeline(
		    new KeyFrame(
			    Duration.millis(durationShow),
			    new EventHandler<ActionEvent>() {
			    @Override public void handle(ActionEvent actionEvent) {
			    	tMessageFade.play();
			    }
		    }
	    ));
	    tMessageShow.play();
	}
}
