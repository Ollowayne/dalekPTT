package ptt.dalek.helpers;

import java.io.InputStream;
import java.util.LinkedList;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

import ptt.dalek.github.Repository;
import ptt.dalek.main.PagedRequest;
import ptt.dalek.main.Parser;
import ptt.dalek.main.Request;


public class RepositoryHelper extends BasicHelper {

	public static final String REPOSITORIES_STRING = "%s/users/%s/repos";
	public static final String REPOSITORY_STRING = "%s/repos/%s/%s";

	public static LinkedList<Repository> getRepositories(String name) {
		PagedRequest pagedRequest = new PagedRequest(String.format(REPOSITORIES_STRING, GITHUB_API_URL, name));

		LinkedList<Repository> repositories =  new LinkedList<Repository>();
		while(pagedRequest.hasNext()) {
			System.out.println("test");
			InputStream stream = pagedRequest.next();
			if(stream != null) {
				JsonReader reader = Json.createReader(stream);
				for(Repository repo : Parser.parseRepositories(reader.readArray()))
					repositories.add(repo);

				reader.close();
			}
		}

		return repositories;
	}

	public static Repository getRepository(String name, String repository) {
		InputStream stream = new Request(String.format(REPOSITORY_STRING, GITHUB_API_URL, name, repository)).send();
		if(stream != null) {
			JsonReader reader = Json.createReader(stream);
			JsonObject jsonObj = reader.readObject();
			reader.close();
			return Parser.parseRepository(jsonObj);
		}

		return new Repository();
	}
}
