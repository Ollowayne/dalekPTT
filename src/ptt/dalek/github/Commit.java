package ptt.dalek.github;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

public class Commit implements JsonConvertable {

	private String sha;
	private CommitData commitData;
	private String htmlUrl;
	private User author;
	private User committer;

	@Override
	public JsonObject toJsonObject() {
		return createObjectBuilder().build();
	}

	@Override
	public JsonObjectBuilder createObjectBuilder() {
		JsonObjectBuilder builder = Json.createObjectBuilder();
		builder.add("sha", sha);
		builder.add("commit", commitData.toJsonObject());
		builder.add("html_url", htmlUrl);
		//builder.add("author", author.toJsonObject());
		//builder.add("committer", committer.toJsonObject());
		return builder;
	}
	
	// Getter & Setter
	
	public String getSha() {
		return sha;
	}

	public void setSha(String sha) {
		this.sha = sha;
	}

	public CommitData getCommitData() {
		return commitData;
	}

	public void setCommitData(CommitData commitData) {
		this.commitData = commitData;
	}

	public String getHtmlUrl() {
		return htmlUrl;
	}

	public void setHtmlUrl(String htmlUrl) {
		this.htmlUrl = htmlUrl;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public User getCommitter() {
		return committer;
	}

	public void setCommitter(User committer) {
		this.committer = committer;
	}
}
