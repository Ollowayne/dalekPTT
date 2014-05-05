package src.Github;


public class User {
	
	private String m_login;
	private int m_id;
	
	private String m_avatarUrl;
	private String m_gravatarId;
	private String m_Url;
	private String m_htmlUrl;
	private String m_followersUrl;
	private String m_followingUrl;
	private String m_gistsUrl;
	private String m_starredUrl;
	private String m_subscriptionsUrl;
	private String m_organizationsUrl;
	private String m_reposUrl;
	private String m_eventsUrl;
	private String m_receivedEventsUrl;
	
	private String m_userType;
	private boolean m_admin;
	
	public User() {
		m_id = -1;
		m_login = "Unknown";
	}

	public int getId() {
		return m_id;
	}
	public void setId(int id) {
		m_id = id;
	}
	
	public String getLogin() {
		return m_login;
	}
	public void setLogin(String login) {
		m_login = login;
	}

	public String getAvatarUrl() {
	    return m_avatarUrl;
	}
	public void setAvatarUrl(String avatarUrl) {
	    m_avatarUrl = avatarUrl;
	}
	
	public String getGravatarId() {
	    return m_gravatarId;
	}
	public void setGravatarId(String gravatarId) {
	    m_gravatarId = gravatarId;
	}
	
	public String getUrl() {
	    return m_Url;
	}
	public void setUrl(String Url) {
	    m_Url = Url;
	}
	
	public String getHtmlUrl() {
	    return m_htmlUrl;
	}
	public void setHtmlUrl(String htmlUrl) {
	    m_htmlUrl = htmlUrl;
	}
	
	public String getFollowersUrl() {
	    return m_followersUrl;
	}
	public void setFollowersUrl(String followersUrl) {
	    m_followersUrl = followersUrl;
	}
	
	public String getFollowingUrl() {
	    return m_followingUrl;
	}
	public void setFollowingUrl(String followingUrl) {
	    m_followingUrl = followingUrl;
	}
	
	public String getGistsUrl() {
	    return m_gistsUrl;
	}
	public void setGistsUrl(String gistsUrl) {
	    m_gistsUrl = gistsUrl;
	}
	
	public String getStarredUrl() {
	    return m_starredUrl;
	}
	public void setStarredUrl(String starredUrl) {
	    m_starredUrl = starredUrl;
	}
	
	public String getSubscriptionsUrl() {
	    return m_subscriptionsUrl;
	}
	public void setSubscriptionsUrl(String subscriptionsUrl) {
	    m_subscriptionsUrl = subscriptionsUrl;
	}
	
	public String getOrganizationsUrl() {
	    return m_organizationsUrl;
	}
	public void setOrganizationsUrl(String organizationsUrl) {
	    m_organizationsUrl = organizationsUrl;
	}
	
	public String getReposUrl() {
	    return m_reposUrl;
	}
	public void setReposUrl(String reposUrl) {
	    m_reposUrl = reposUrl;
	}
	
	public String getEventsUrl() {
	    return m_eventsUrl;
	}
	public void setEventsUrl(String eventsUrl) {
	    m_eventsUrl = eventsUrl;
	}
	
	public String getReceivedEventsUrl() {
	    return m_receivedEventsUrl;
	}
	public void setReceivedEventsUrl(String receivedEventsUrl) {
	    m_receivedEventsUrl = receivedEventsUrl;
	}
	
	public String getUserType() {
		return m_userType;
	}
	public void setUserType(String userType) {
		m_userType = userType;
	}

	public boolean isAdmin() {
		return m_admin;
	}
	public void setAdmin(boolean admin) {
		m_admin = admin;
	}
}
