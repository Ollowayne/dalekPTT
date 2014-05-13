package ptt.dalek.gui;

import java.util.LinkedList;
import java.util.Queue;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class Messenger {
	private Label boundTo;
	private int durationOn;
	private int durationFade;
	private boolean isEmpty = true;
	// queue not yet implemented!
	Queue<String> messageQueue = new LinkedList<String>();
	
	public Messenger(Label boundTo, int durationOn, int durationFade) {
		this.boundTo = boundTo;
		this.durationOn = durationOn;
		this.durationFade = durationFade;
	}
	
	public void emptyLabel() {
		boundTo.setText("");
	}
	
	public void displayMessage(String message) {
		displayMessage(message, durationOn, durationFade);
	}
	
	public void displayMessage(String message, int durationShow, int durationFade) {
		if(isEmpty) {
			display(message, durationShow, durationFade);
		}
		else {
			messageQueue.offer(message);
		}
	}
	
	private void display(String message, int durationShow, int durationFade) {
		boundTo.setText("   " + message);
		
	    final Timeline t_messageFade = new Timeline(
			    new KeyFrame(
				    Duration.millis(durationFade),
				    new EventHandler<ActionEvent>() {
				    @Override public void handle(ActionEvent actionEvent) {
				    	boundTo.setText("");
				    	boundTo.setOpacity(1);
				    	
				    }
			    }
				, new KeyValue(boundTo.opacityProperty(), 0)));
	    
	    final Timeline t_message = new Timeline(
		    new KeyFrame(
			    Duration.millis(durationShow),
			    new EventHandler<ActionEvent>() {
			    @Override public void handle(ActionEvent actionEvent) {
			    	boundTo.setOpacity(1);
			    	t_messageFade.play();
			    }
		    }
	    ));
	    t_message.play();
	}
}
