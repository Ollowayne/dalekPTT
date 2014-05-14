package ptt.dalek.main;

public class Util {

	// Let's debug some streams
	// @
	// http://stackoverflow.com/questions/309424/read-convert-an-inputstream-to-a-string
	public static String convertStreamToString(java.io.InputStream is) {
		java.util.Scanner s = new java.util.Scanner(is);
		s.useDelimiter("\\A");
		String ret = s.hasNext() ? s.next() : "";
		s.close();
		return ret;
	}
}
