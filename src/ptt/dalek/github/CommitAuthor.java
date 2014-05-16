package ptt.dalek.github;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import ptt.dalek.main.ISO8601;

public class CommitAuthor implements JsonConvertable {

	private String name;
	private String email;
	private long date;

	@Override
	public JsonObject toJsonObject() {
		return createObjectBuilder().build();
	}

	@Override
	public JsonObjectBuilder createObjectBuilder() {
		JsonObjectBuilder builder = Json.createObjectBuilder();
		builder.add("name", name);
		builder.add("email", email);
		builder.add("date", ISO8601.fromUnix(date));
		return builder;
	}

	// Getter & Setter

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public long getDate() {
		return date;
	}

	public void setDate(long date) {
		this.date = date;
	}
	
}