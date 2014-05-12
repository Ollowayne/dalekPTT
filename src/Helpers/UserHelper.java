package Helpers;

import java.io.InputStream;
import java.util.LinkedList;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

import Github.Repository;
import Github.User;
import Main.Parser;
import Main.Request;


public class UserHelper extends APIHelper {
	
	public static final String REPOSITORIES_STRING = "%s/users/%s";
		
	public static User getUser(String name) {
		InputStream stream = new Request(String.format(REPOSITORIES_STRING, GITHUB_API_URL, name)).send();
		if(stream != null) {
			JsonReader reader = Json.createReader(stream);
			JsonObject jsonObj = reader.readObject();
			reader.close();
			
			return Parser.parseUser(jsonObj);
		}

		return new User();
	}
}
