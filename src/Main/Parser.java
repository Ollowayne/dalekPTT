package Main;
import javax.json.JsonObject;

import Github.Repository;
import Github.User;



public class Parser {

	public static User parseUser(JsonObject obj) {
		User user = new User();
		
		user.setLogin(obj.getString("login"));
		user.setId(obj.getInt("id"));
		
		user.setAvatarUrl(obj.getString("avatar_url"));
		user.setGravatarId(obj.getString("gravatar_id"));
		user.setUrl(obj.getString("url"));
		user.setHtmlUrl(obj.getString("html_url"));
		user.setFollowersUrl(obj.getString("followers_url"));
		user.setFollowingUrl(obj.getString("following_url"));
		user.setGistsUrl(obj.getString("gists_url"));
		user.setStarredUrl(obj.getString("starred_url"));
		user.setSubscriptionsUrl(obj.getString("subscriptions_url"));
		user.setOrganizationsUrl(obj.getString("organizations_url"));
		user.setReposUrl(obj.getString("repos_url"));
		user.setEventsUrl(obj.getString("events_url"));
		user.setReceivedEventsUrl(obj.getString("received_events_url"));
		
		user.setUserType(obj.getString("type"));
		user.setAdmin(obj.getBoolean("site_admin"));

		return user;
	}
	
	public static Repository parseRepository(JsonObject obj) {
		Repository repository = new Repository();
		
		repository.setOwner(parseUser(obj.getJsonObject("owner")));
		
		return repository;
	}
}
