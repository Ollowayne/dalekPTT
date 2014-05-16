package ptt.dalek.main;

import java.util.LinkedList;

import javax.json.JsonArray;
import javax.json.JsonObject;

import ptt.dalek.github.Repository;
import ptt.dalek.github.User;

public class Parser {

	public static String jsonValueToString(JsonObject obj, String key) {
		return obj.get(key).toString() == "null" ? "" : obj.getString(key);
	}

	public static User parseUser(JsonObject obj) {
		User user = new User();

		user.setLogin(obj.getString("login"));
		user.setId(obj.getInt("id"));

		user.setAvatarUrl(obj.getString("avatar_url"));
		user.setGravatarId(jsonValueToString(obj, "gravatar_id"));
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

		if (obj.containsKey("name")) {
			user.setName(obj.getString("name"));
		}
		if (obj.containsKey("company")) {
			user.setCompany(jsonValueToString(obj, "company"));
		}
		if (obj.containsKey("blog")) {
			user.setBlog(jsonValueToString(obj, "blog"));
		}
		if (obj.containsKey("location")) {
			user.setLocation(jsonValueToString(obj, "location"));
		}
		if (obj.containsKey("email")) {
			user.setEmail(jsonValueToString(obj, "email"));
		}
		if (obj.containsKey("hireable")) {
			user.setHireable(obj.getBoolean("hireable"));
		}
		if (obj.containsKey("bio")) {
			user.setBio(jsonValueToString(obj, "bio"));
		}

		if (obj.containsKey("public_repos")) {
			user.setPublicRepos(obj.getInt("public_repos"));
			user.setPublicGists(obj.getInt("public_gists"));
			user.setFollowers(obj.getInt("followers"));
			user.setFollowing(obj.getInt("following"));
			user.setCreatedAt(ISO8601.toUnix(obj.getString("created_at")));
			user.setUpdatedAt(ISO8601.toUnix(obj.getString("updated_at")));
		}

		return user;
	}

	public static LinkedList<User> parseUsers(JsonArray array) {
		LinkedList<User> users = new LinkedList<User>();
		for (int i = 0; i < array.size(); ++i)
			users.add(parseUser(array.getJsonObject(i)));

		return users;
	}

	public static Repository parseRepository(JsonObject obj) {
		Repository repository = new Repository();

		repository.setId(obj.getInt("id"));
		repository.setName(obj.getString("name"));
		repository.setFullName(obj.getString("full_name"));

		repository.setOwner(parseUser(obj.getJsonObject("owner")));

		repository.setDescription(obj.getString("description"));
		repository.setPrivate(obj.getBoolean("private"));
		repository.setFork(obj.getBoolean("fork"));

		repository.setHtmlUrl(obj.getString("html_url"));
		repository.setUrl(obj.getString("url"));
		repository.setForksUrl(obj.getString("forks_url"));
		repository.setKeysUrl(obj.getString("keys_url"));
		repository.setCollaboratorsUrls(obj.getString("collaborators_url"));
		repository.setTeamsUrl(obj.getString("teams_url"));
		repository.setHooksUrl(obj.getString("hooks_url"));
		repository.setIssueEventsUrl(obj.getString("issue_events_url"));
		repository.setEventsUrl(obj.getString("events_url"));
		repository.setAssigneesUrl(obj.getString("assignees_url"));
		repository.setBranchesUrl(obj.getString("branches_url"));
		repository.setTagsUrl(obj.getString("tags_url"));
		repository.setBlobsUrl(obj.getString("blobs_url"));
		repository.setGitTagsUrl(obj.getString("git_tags_url"));
		repository.setGitRefsUrl(obj.getString("git_refs_url"));
		repository.setTreesUrl(obj.getString("trees_url"));
		repository.setStatusesUrl(obj.getString("statuses_url"));
		repository.setLanguagesUrl(obj.getString("languages_url"));
		repository.setStargazersUrl(obj.getString("stargazers_url"));
		repository.setContributorsUrl(obj.getString("contributors_url"));
		repository.setSubscribersUrl(obj.getString("subscribers_url"));
		repository.setCommitsUrl(obj.getString("commits_url"));
		repository.setGitCommitsUrl(obj.getString("git_commits_url"));
		repository.setCommentsUrl(obj.getString("comments_url"));
		repository.setIssueCommentUrl(obj.getString("issue_comment_url"));
		repository.setContentsUrl(obj.getString("contents_url"));
		repository.setCompareUrl(obj.getString("compare_url"));
		repository.setMergesUrl(obj.getString("merges_url"));
		repository.setArchiveUrl(obj.getString("archive_url"));
		repository.setDownloadsUrl(obj.getString("downloads_url"));
		repository.setIssuesUrl(obj.getString("issues_url"));
		repository.setPullsUrl(obj.getString("pulls_url"));
		repository.setMilestonesUrl(obj.getString("milestones_url"));
		repository.setNotificationsUrl(obj.getString("notifications_url"));
		repository.setLabelsUrl(obj.getString("labels_url"));
		repository.setReleasesUrl(obj.getString("releases_url"));

		repository.setGitUrl(obj.getString("git_url"));
		repository.setSshUrl(obj.getString("ssh_url"));
		repository.setCloneUrl(obj.getString("clone_url"));
		repository.setSvnUrl(obj.getString("svn_url"));
		repository.setHomepage(jsonValueToString(obj, "homepage"));

		repository.setSize(obj.getInt("size"));
		repository.setStargazersCount(obj.getInt("stargazers_count"));
		repository.setWatchersCount(obj.getInt("watchers_count"));
		repository.setLanguage(jsonValueToString(obj, "language"));
		repository.setHasIssues(obj.getBoolean("has_issues"));
		repository.setHasDownloads(obj.getBoolean("has_downloads"));
		repository.setHasWiki(obj.getBoolean("has_wiki"));

		repository.setForksCount(obj.getInt("forks_count"));
		repository.setForks(obj.getInt("forks"));
		repository.setOpenIssues(obj.getInt("open_issues"));
		repository.setWatchers(obj.getInt("watchers"));
		repository.setDefaultBranch(obj.getString("default_branch"));

		repository.setCreatedAt(parseISO8601(obj, "created_at"));
		repository.setUpdatedAt(parseISO8601(obj, "updated_at"));
		repository.setPushedAt(parseISO8601(obj, "pushed_at"));

		return repository;
	}

	public static long parseISO8601(JsonObject obj, String key) {
		String value = jsonValueToString(obj, key);
		return ISO8601.toUnix(value);
	}

	public static LinkedList<Repository> parseRepositories(JsonArray array) {
		LinkedList<Repository> repos = new LinkedList<Repository>();
		for (int i = 0; i < array.size(); ++i)
			repos.add(Parser.parseRepository(array.getJsonObject(i)));

		return repos;
	}

	public static LinkedList<String> parseStringArray(JsonArray array) {
		LinkedList<String> list = new LinkedList<String>();

		for (int i = 0; i < array.size(); ++i)
			list.add(array.getString(i));

		return list;
	}
}
