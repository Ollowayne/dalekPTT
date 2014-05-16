package ptt.dalek.github;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

public interface JsonConvertable {
	public JsonObject toJsonObject();
	public JsonObjectBuilder createObjectBuilder();
}
