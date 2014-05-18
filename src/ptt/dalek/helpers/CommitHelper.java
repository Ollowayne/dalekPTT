package ptt.dalek.helpers;

import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedList;

import javax.json.Json;
import javax.json.JsonReader;

import ptt.dalek.github.Commit;
import ptt.dalek.main.PagedRequest;
import ptt.dalek.main.Parser;

public class CommitHelper extends BasicHelper {

	public static final String COMMIT_STRING = "%s/repos/%s/%s/commits";

	public static LinkedList<Commit> getCommits(String name, String repository) {
		return getCommits(name, repository, "1970-00-01T00:00:00Z");
	}
	
	//adds commits (all if there were none, else all new ones and not listed ones)
	@SuppressWarnings("serial")
	public static LinkedList<Commit> getCommits(String name, String repository, final String since) {
		PagedRequest pagedRequest = new PagedRequest(String.format(COMMIT_STRING, GITHUB_API_URL, name, repository));
		pagedRequest.setParameters(new HashMap<String, String>() {{put("since", since);}});

		LinkedList<Commit> commits =  new LinkedList<Commit>();
		while(pagedRequest.hasNext()) {
			InputStream stream = pagedRequest.next();
			if(stream != null) {
				JsonReader reader = Json.createReader(stream);
				for(Commit commit : Parser.parseCommits(reader.readArray())) {
					commits.add(commit);
				}

				reader.close();
			}
		}

		return commits;
	}
}
