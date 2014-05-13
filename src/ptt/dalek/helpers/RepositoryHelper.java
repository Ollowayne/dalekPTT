package ptt.dalek.helpers;

import java.io.InputStream;
import java.util.LinkedList;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonReader;

import ptt.dalek.github.Repository;
import ptt.dalek.main.Parser;
import ptt.dalek.main.Request;


public class RepositoryHelper extends APIHelper {
	
	public static final String REPOSITORIES_STRING = "%s/users/%s/repos";
		
	public static LinkedList<Repository> getRepositories(String name) {
		InputStream stream = new Request(String.format(REPOSITORIES_STRING, GITHUB_API_URL, name)).send();
		if(stream != null) {
			JsonReader reader = Json.createReader(stream);
			JsonArray jsonRepositories = reader.readArray();
			reader.close();
			
			return Parser.parseRepositories(jsonRepositories);
		}

		return new LinkedList<Repository>();
	}
}
