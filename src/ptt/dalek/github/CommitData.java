package ptt.dalek.github;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

public class CommitData implements JsonConvertable {

	private CommitAuthor author;
	private CommitAuthor committer;
	private String message;
	private String url;
	private int commentCount;
	
	@Override
	public JsonObject toJsonObject() {
		return createObjectBuilder().build();
	}

	@Override
	public JsonObjectBuilder createObjectBuilder() {
		JsonObjectBuilder builder = Json.createObjectBuilder();
		builder.add("author", author.toJsonObject());
		builder.add("committer", committer.toJsonObject());
		builder.add("message", message);
		builder.add("url", url);
		builder.add("comment_count", commentCount);	
		return builder;
	}

	// Getter & Setter

	public CommitAuthor getAuthor() {
		return author;
	}

	public void setAuthor(CommitAuthor author) {
		this.author = author;
	}

	public CommitAuthor getCommitter() {
		return committer;
	}

	public void setCommitter(CommitAuthor committer) {
		this.committer = committer;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}

}
