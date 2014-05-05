package src.Main;

import javax.json.JsonObject;

import src.Github.User;


public class JsonParser {

	public static User parseUser(JsonObject obj) {
		User user = new User();
		
		user.setLogin(obj.getString("login"));
		user.setId(obj.getInt("id"));
		
		/*
		private String m_avatarUrl;
		private String m_gravatarUrl;
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
		*/
		return user;
	}
}
