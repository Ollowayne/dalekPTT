package ptt.dalek.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;

import ptt.dalek.github.Commit;
import ptt.dalek.github.Repository;
import ptt.dalek.github.User;

public class Settings {

	private static final String[] FOLDERS = new String[] {"data"};
	private static final String DATA_FOLDER = "data";
	private static final String WATCHED_USERS_FILE = DATA_FOLDER + File.separator + "watched_users.json";
	private static final String REPOSITORY_FILE = DATA_FOLDER + File.separator + "repositories.json";
	private static final String COMMIT_STATUS_FILE = DATA_FOLDER + File.separator + "commit_status.json";

	
	public static boolean initializeFolders() {
		boolean result = true;
		for(String folder : FOLDERS) {
			File dir = new File(folder);
			if(!dir.exists())
				result &= dir.mkdir();
		}
		
		return result;
	}
	
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
	
	public static boolean saveRepositoryMap(Map<String, LinkedList<Repository>> map) {
		JsonObjectBuilder objBuilder = Json.createObjectBuilder();
		
		for(String key : map.keySet()) {
			JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
			
			for(Repository repository : map.get(key)) {
				arrayBuilder.add(repository.toJsonObject());
			}
			objBuilder.add(key, arrayBuilder.build());
		}
		
		String content = objBuilder.build().toString();
		File file = new File(REPOSITORY_FILE);
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
	
	public static Map<String, LinkedList<Repository>> loadRepositoryMap() {
		File file = new File(REPOSITORY_FILE);
		if(file.exists() && !file.isDirectory()) {
			try {
				Map<String, LinkedList<Repository>> map = new HashMap<String, LinkedList<Repository>>();
				InputStream stream = new FileInputStream(file);
				JsonReader reader = Json.createReader(stream);
				JsonObject obj = reader.readObject();
				reader.close();

				for(String key : obj.keySet()) {
					LinkedList<Repository> repositoryList = new LinkedList<Repository>();
					JsonArray repositoryArray = obj.getJsonArray(key);
					for(int i = 0; i < repositoryArray.size(); ++i) {
						repositoryList.add(Parser.parseRepository(repositoryArray.getJsonObject(i)));
					}

					map.put(key, repositoryList);
				}

				return map;
			} catch(Exception e) {
				//Malformed file or file does not exist
				e.printStackTrace();
				return new HashMap<String, LinkedList<Repository>>();
			}
		}

		return new HashMap<String, LinkedList<Repository>>();
	}

	public static boolean saveCommitStatus(Map<String, String> map) {
		JsonObjectBuilder objBuilder = Json.createObjectBuilder();

		for(String key : map.keySet()) {
			objBuilder.add(key, map.get(key));
		}

		String content = objBuilder.build().toString();
		File file = new File(COMMIT_STATUS_FILE);
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
	
	public static Map<String, String> loadCommitStatus() {
		File file = new File(COMMIT_STATUS_FILE);
		if(file.exists() && !file.isDirectory()) {
			try {
				Map<String, String> map = new HashMap<String, String>();
				InputStream stream = new FileInputStream(file);
				JsonReader reader = Json.createReader(stream);
				JsonObject obj = reader.readObject();
				reader.close();

				for(String key : obj.keySet()) {
					map.put(key, obj.getString(key));
				}

				return map;
			} catch(Exception e) {
				//Malformed file or file does not exist
				e.printStackTrace();
				return new HashMap<String, String>();
			}
		}

		return new HashMap<String, String>();
	}

	public static boolean saveCommits(Map<String, LinkedList<Commit>> map) {
		try {
			for(String fullName : map.keySet()) {
				JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
				for(Commit commit : map.get(fullName)) {
					arrayBuilder.add(commit.toJsonObject());
				}
	
				String content = arrayBuilder.build().toString();
				String folder = DATA_FOLDER + File.separator + fullName.split("/")[0];
				String path = DATA_FOLDER + File.separator + fullName.replace("/", File.separator) + ".json";
	
				File userFolder = new File(folder);
				if(!userFolder.exists()) {
					userFolder.mkdir();
				}
	
				File commitFile = new File(path);
				if(!commitFile.exists()) {
					commitFile.createNewFile();
				}
	
				FileWriter filewriter = new FileWriter(commitFile);
				filewriter.write(content);
				filewriter.close();
			}
		} catch (IOException e) {
			return false;
		}
		
		return true;
	}

	public static Map<String, LinkedList<Commit>> loadCommits(Set<String> keySet) {
		try {
			Map<String, LinkedList<Commit>> map = new HashMap<String, LinkedList<Commit>>();
			for(String fullName : keySet) {
				String path = DATA_FOLDER + File.separator + fullName.replace("/", File.separator) + ".json";	
				File commitFile = new File(path);
				if(commitFile.exists() && !commitFile.isDirectory()) {
					InputStream stream = new FileInputStream(commitFile);
					JsonReader reader = Json.createReader(stream);
					JsonArray array = reader.readArray();
					reader.close();
					
					map.put(fullName, Parser.parseCommits(array));
				}
			}
			return map;
		} catch (IOException e) {
			return new HashMap<String, LinkedList<Commit>>();
		}
	}
}
