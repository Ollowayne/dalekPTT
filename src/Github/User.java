package Github;


public class User {
	
	private String login;
	private int id;

	private String avatarUrl;
	private String gravatarId;
	private String url;
	private String htmlUrl;
	private String followersUrl;
	private String followingUrl;
	private String gistsUrl;
	private String starredUrl;
	private String subscriptionsUrl;
	private String organizationsUrl;
	private String reposUrl;
	private String eventsUrl;
	private String receivedEventsUrl;

	private String userType;
	private boolean admin;
	
	public User() {
		this.id = -1;
		this.login = "Unknown";
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
