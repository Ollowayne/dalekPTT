package Main;

import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedList;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

import Github.Repository;


public class RepositoryHelper {

	public static final String GITHUB_API_URL = "https://api.github.com";
	
	public static final String REPOSITORIES_STRING = "%s/users/%s/repos";
		
	public static LinkedList<Repository> getRepositories(String name) {
		LinkedList<Repository> repositories = new LinkedList<Repository>();
		InputStream stream = new Request(String.format(REPOSITORIES_STRING, GITHUB_API_URL, name)).send();
		if(stream != null) {
			JsonReader reader = Json.createReader(stream);
			JsonArray jsonRepositories = reader.readArray();
			reader.close();
			
			for(int i = 0; i < jsonRepositories.size(); ++i)
				repositories.add(Parser.parseRepository(jsonRepositories.getJsonObject(i)));
		}

		return repositories;
	}
	
	
}
