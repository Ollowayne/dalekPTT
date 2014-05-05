
public class Util {
	
	// Let's debug some streams
	// @ http://stackoverflow.com/questions/309424/read-convert-an-inputstream-to-a-string
	static String convertStreamToString(java.io.InputStream is) {
	    java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
	    return s.hasNext() ? s.next() : "";
	}
	
}
