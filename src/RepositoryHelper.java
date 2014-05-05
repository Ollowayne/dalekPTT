import java.io.IOException;
import java.util.HashMap;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParser.Event;

public class RepositoryHelper {

	public static final String GITHUB_API_URL = "https://api.github.com";
	
	public static final String REPOSITORIES_STRING = "%s/users/%s/repos";
		
	public static void /*Array of repos*/ getRepositories(String name) {
		Request r = new Request(String.format(REPOSITORIES_STRING, GITHUB_API_URL, name));
		
		try {
			JsonReader reader = Json.createReader(r.send());
			
			JsonArray j = reader.readArray();
			System.out.println("array: " + j);
			reader.close();
			

		} catch (IOException e) {
			// return smth..
		}
	}
	
	
}
