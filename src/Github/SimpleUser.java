package Github;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;


public class SimpleUser implements JsonConvertable {
	
	protected String login;
	protected int id;

	protected String avatarUrl;
	protected String gravatarId;
	protected String url;
	protected String htmlUrl;
	protected String followersUrl;
	protected String followingUrl;
	protected String gistsUrl;
	protected String starredUrl;
	protected String subscriptionsUrl;
	protected String organizationsUrl;
	protected String reposUrl;
	protected String eventsUrl;
	protected String receivedEventsUrl;

	protected String userType;
	protected boolean admin;
	
	public SimpleUser() {
		this.id = -1;
		this.login = "_unknown";
	}
	
	protected JsonObjectBuilder createObjectBuilder() {
		JsonObjectBuilder builder = Json.createObjectBuilder();

		builder.add("login", login);
		builder.add("id", id);
		builder.add("avatar_url", avatarUrl);
		builder.add("gravatar_id", gravatarId);
		builder.add("url", url);
		builder.add("html_url", htmlUrl);
		builder.add("followers_url", followersUrl);
		builder.add("following_url", followingUrl);
		builder.add("gists_url", gistsUrl);
		builder.add("starred_url", starredUrl);
		builder.add("subscriptions_url", subscriptionsUrl);
		builder.add("organizations_url", organizationsUrl);
		builder.add("repos_url", reposUrl);
		builder.add("events_url", eventsUrl);
		builder.add("received_events_url", receivedEventsUrl);
		builder.add("type", userType);
		builder.add("site_admin", admin);
		
		return builder;
	}
	
	public JsonObject toJsonObject() {
		
		return createObjectBuilder().build();
	}

	public int getId() {
		return this.id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getLogin() {
		return this.login;
	}
	public void setLogin(String login) {
		this.login = login;
	}

	public String getAvatarUrl() {
	    return this.avatarUrl;
	}
	public void setAvatarUrl(String avatarUrl) {
	    this.avatarUrl = avatarUrl;
	}
	
	public String getGravatarId() {
	    return this.gravatarId;
	}
	public void setGravatarId(String gravatarId) {
	    this.gravatarId = gravatarId;
	}
	
	public String getUrl() {
	    return this.url;
	}
	public void setUrl(String url) {
	    this.url = url;
	}
	
	public String getHtmlUrl() {
	    return this.htmlUrl;
	}
	public void setHtmlUrl(String htmlUrl) {
	    this.htmlUrl = htmlUrl;
	}
	
	public String getFollowersUrl() {
	    return this.followersUrl;
	}
	public void setFollowersUrl(String followersUrl) {
	    this.followersUrl = followersUrl;
	}
	
	public String getFollowingUrl() {
	    return this.followingUrl;
	}
	public void setFollowingUrl(String followingUrl) {
	    this.followingUrl = followingUrl;
	}
	
	public String getGistsUrl() {
	    return this.gistsUrl;
	}
	public void setGistsUrl(String gistsUrl) {
	    this.gistsUrl = gistsUrl;
	}
	
	public String getStarredUrl() {
	    return this.starredUrl;
	}
	public void setStarredUrl(String starredUrl) {
	    this.starredUrl = starredUrl;
	}
	
	public String getSubscriptionsUrl() {
	    return this.subscriptionsUrl;
	}
	public void setSubscriptionsUrl(String subscriptionsUrl) {
	    this.subscriptionsUrl = subscriptionsUrl;
	}
	
	public String getOrganizationsUrl() {
	    return this.organizationsUrl;
	}
	public void setOrganizationsUrl(String organizationsUrl) {
	    this.organizationsUrl = organizationsUrl;
	}
	
	public String getReposUrl() {
	    return this.reposUrl;
	}
	public void setReposUrl(String reposUrl) {
	    this.reposUrl = reposUrl;
	}
	
	public String getEventsUrl() {
	    return this.eventsUrl;
	}
	public void setEventsUrl(String eventsUrl) {
	    this.eventsUrl = eventsUrl;
	}
	
	public String getReceivedEventsUrl() {
	    return this.receivedEventsUrl;
	}
	public void setReceivedEventsUrl(String receivedEventsUrl) {
	    this.receivedEventsUrl = receivedEventsUrl;
	}
	
	public String getUserType() {
		return this.userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}

	public boolean isAdmin() {
		return this.admin;
	}
	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
}
