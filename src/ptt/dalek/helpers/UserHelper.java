package ptt.dalek.helpers;

import java.io.InputStream;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

import ptt.dalek.github.User;
import ptt.dalek.main.Parser;
import ptt.dalek.main.Request;


public class UserHelper extends BasicHelper {

	public static final String USER_STRING = "%s/users/%s";

	public static User getUser(String name) {
		InputStream stream = new Request(String.format(USER_STRING, GITHUB_API_URL, name)).send();
		if(stream != null) {
			JsonReader reader = Json.createReader(stream);
			JsonObject jsonObj = reader.readObject();
			reader.close();

			return Parser.parseUser(jsonObj);
		}

		return new User();
	}
}
