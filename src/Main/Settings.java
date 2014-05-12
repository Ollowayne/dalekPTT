package Main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonReader;
import javax.json.stream.JsonParsingException;

import Github.User;

public class Settings {
	
	private static final String WATCHED_USERS_FILE = "data/watched_users.json";
	
	public static boolean saveUserList(LinkedList<User> users) {
		JsonArrayBuilder builder = Json.createArrayBuilder();
		
		for(User user : users)
			builder.add(user.toJsonObject());
		
		String content = builder.build().toString();
		File file = new File(WATCHED_USERS_FILE);
		try {
			if(!file.exists()) {
				file.createNewFile();
			}
	
			FileWriter filewriter = new FileWriter(file);
			filewriter.write(content);
			
			filewriter.close();
		} catch (IOException e) {
			return false;
		}
		return true;
	}
	
	public static LinkedList<User> loadUserList() {
		File file = new File(WATCHED_USERS_FILE);
		if(file.exists() && !file.isDirectory()) {
			try {
				InputStream stream = new FileInputStream(file);
				JsonReader reader = Json.createReader(stream);
				
				JsonArray jsonArray = reader.readArray();
				reader.close();
				return Parser.parseUsers(jsonArray);
			} catch(Exception e) {
				//Malformed file or file does not exist
				e.printStackTrace();
				return new LinkedList<User>();
			}
		}

		return new LinkedList<User>();
	}
}
