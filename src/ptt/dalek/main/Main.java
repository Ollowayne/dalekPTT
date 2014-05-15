package ptt.dalek.main;

import ptt.dalek.gui.App;
import javafx.application.Application;

public class Main {
	
	//TODO
	public static String convertStreamToString(java.io.InputStream is) {
		java.util.Scanner s = new java.util.Scanner(is);
		s.useDelimiter("\\A");
		String ret = s.hasNext() ? s.next() : "";
		s.close();
		return ret;
	}
	
	public static void main(String[] args) {
		Application.launch(App.class, args);
	}
}