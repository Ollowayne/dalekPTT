package ptt.dalek.github;

import javax.json.JsonObject;


public class Repository implements JsonConvertable {

	private int id;
	private String name;
	private String fullName;

	private User owner;
	private String description;
	private boolean isPrivate;
	private boolean isFork;

	private String htmlUrl;
	private String url;
	private String forksUrl;
	private String keysUrl;
	private String collaboratorsUrls;
	private String teamsUrl;
	private String hooksUrl;
	private String issueEventsUrl;
	private String eventsUrl;
	private String assigneesUrl;
	private String branchesUrl;
	private String tagsUrl;
	private String blobsUrl;
	private String gitTagsUrl;
	private String gitRefsUrl;
	private String treesUrl;
	private String statusesUrl;
	private String languagesUrl;
	private String stargazersUrl;
	private String contributorsUrl;
	private String subscribersUrl;
	private String subscriptionUrl;
	private String commitsUrl;
	private String gitCommitsUrl;
	private String commentsUrl;
	private String issueCommentUrl;
	private String contentsUrl;
	private String compareUrl;
	private String mergesUrl;
	private String archiveUrl;
	private String downloadsUrl;
	private String issuesUrl;
	private String pullsUrl;
	private String milestonesUrl;
	private String notificationsUrl;
	private String labelsUrl;
	private String releasesUrl;

	private long createdAt;
	private long updatedAt;
	private long pushedAt;
	private String gitUrl;
	private String sshUrl;
	private String cloneUrl;
	private String svnUrl;
	private String homepage;
	private int size;
	private int stargazersCount;
	private int watchersCount;
	private String language;
	private boolean hasIssues;
	private boolean hasDownloads;
	private boolean hasWiki;
	private int forksCount;
	private int forks;
	private int openIssues;
	private int watchers;
	private String defaultBranch;

	/*
	 * private String mirrorUrl;
	 */

	public Repository() {
		id = -1;
		name = fullName = "";
		owner = new User();
	}
	
	@Override
	public JsonObject toJsonObject() {
		return null;
	}

	public User getOwner() {
		return owner;
	}
	public void setOwner(User owner) {
		this.owner = owner;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isPrivate() {
		return isPrivate;
	}

	public void setPrivate(boolean isPrivate) {
		this.isPrivate = isPrivate;
	}

	public boolean isFork() {
		return isFork;
	}

	public void setFork(boolean isFork) {
		this.isFork = isFork;
	}

	public String getHtmlUrl() {
		return htmlUrl;
	}

	public void setHtmlUrl(String htmlUrl) {
		this.htmlUrl = htmlUrl;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getForksUrl() {
		return forksUrl;
	}

	public void setForksUrl(String forksUrl) {
		this.forksUrl = forksUrl;
	}

	public String getKeysUrl() {
		return keysUrl;
	}

	public void setKeysUrl(String keysUrl) {
		this.keysUrl = keysUrl;
	}

	public String getCollaboratorsUrls() {
		return collaboratorsUrls;
	}

	public void setCollaboratorsUrls(String collaboratorsUrls) {
		this.collaboratorsUrls = collaboratorsUrls;
	}

	public String getTeamsUrl() {
		return teamsUrl;
	}

	public void setTeamsUrl(String teamsUrl) {
		this.teamsUrl = teamsUrl;
	}

	public String getHooksUrl() {
		return hooksUrl;
	}

	public void setHooksUrl(String hooksUrl) {
		this.hooksUrl = hooksUrl;
	}

	public String getIssueEventsUrl() {
		return issueEventsUrl;
	}

	public void setIssueEventsUrl(String issueEventsUrl) {
		this.issueEventsUrl = issueEventsUrl;
	}

	public String getEventsUrl() {
		return eventsUrl;
	}

	public void setEventsUrl(String eventsUrl) {
		this.eventsUrl = eventsUrl;
	}

	public String getAssigneesUrl() {
		return assigneesUrl;
	}

	public void setAssigneesUrl(String assigneesUrl) {
		this.assigneesUrl = assigneesUrl;
	}

	public String getBranchesUrl() {
		return branchesUrl;
	}

	public void setBranchesUrl(String branchesUrl) {
		this.branchesUrl = branchesUrl;
	}

	public String getTagsUrl() {
		return tagsUrl;
	}

	public void setTagsUrl(String tagsUrl) {
		this.tagsUrl = tagsUrl;
	}

	public String getBlobsUrl() {
		return blobsUrl;
	}

	public void setBlobsUrl(String blobsUrl) {
		this.blobsUrl = blobsUrl;
	}

	public String getGitTagsUrl() {
		return gitTagsUrl;
	}

	public void setGitTagsUrl(String gitTagsUrl) {
		this.gitTagsUrl = gitTagsUrl;
	}

	public String getGitRefsUrl() {
		return gitRefsUrl;
	}

	public void setGitRefsUrl(String gitRefsUrl) {
		this.gitRefsUrl = gitRefsUrl;
	}

	public String getTreesUrl() {
		return treesUrl;
	}

	public void setTreesUrl(String treesUrl) {
		this.treesUrl = treesUrl;
	}

	public String getStatusesUrl() {
		return statusesUrl;
	}

	public void setStatusesUrl(String statusesUrl) {
		this.statusesUrl = statusesUrl;
	}

	public String getLanguagesUrl() {
		return languagesUrl;
	}

	public void setLanguagesUrl(String languagesUrl) {
		this.languagesUrl = languagesUrl;
	}

	public String getStargazersUrl() {
		return stargazersUrl;
	}

	public void setStargazersUrl(String stargazersUrl) {
		this.stargazersUrl = stargazersUrl;
	}

	public String getContributorsUrl() {
		return contributorsUrl;
	}

	public void setContributorsUrl(String contributorsUrl) {
		this.contributorsUrl = contributorsUrl;
	}

	public String getSubscribersUrl() {
		return subscribersUrl;
	}

	public void setSubscribersUrl(String subscribersUrl) {
		this.subscribersUrl = subscribersUrl;
	}

	public String getSubscriptionUrl() {
		return subscriptionUrl;
	}

	public void setSubscriptionUrl(String subscriptionUrl) {
		this.subscriptionUrl = subscriptionUrl;
	}

	public String getCommitsUrl() {
		return commitsUrl;
	}

	public void setCommitsUrl(String commitsUrl) {
		this.commitsUrl = commitsUrl;
	}

	public String getGitCommitsUrl() {
		return gitCommitsUrl;
	}

	public void setGitCommitsUrl(String gitCommitsUrl) {
		this.gitCommitsUrl = gitCommitsUrl;
	}

	public String getCommentsUrl() {
		return commentsUrl;
	}

	public void setCommentsUrl(String commentsUrl) {
		this.commentsUrl = commentsUrl;
	}

	public String getIssueCommentUrl() {
		return issueCommentUrl;
	}

	public void setIssueCommentUrl(String issueCommentUrl) {
		this.issueCommentUrl = issueCommentUrl;
	}

	public String getContentsUrl() {
		return contentsUrl;
	}

	public void setContentsUrl(String contentsUrl) {
		this.contentsUrl = contentsUrl;
	}

	public String getCompareUrl() {
		return compareUrl;
	}

	public void setCompareUrl(String compareUrl) {
		this.compareUrl = compareUrl;
	}

	public String getMergesUrl() {
		return mergesUrl;
	}

	public void setMergesUrl(String mergesUrl) {
		this.mergesUrl = mergesUrl;
	}

	public String getArchiveUrl() {
		return archiveUrl;
	}

	public void setArchiveUrl(String archiveUrl) {
		this.archiveUrl = archiveUrl;
	}

	public String getDownloadsUrl() {
		return downloadsUrl;
	}

	public void setDownloadsUrl(String downloadsUrl) {
		this.downloadsUrl = downloadsUrl;
	}

	public String getIssuesUrl() {
		return issuesUrl;
	}

	public void setIssuesUrl(String issuesUrl) {
		this.issuesUrl = issuesUrl;
	}

	public String getPullsUrl() {
		return pullsUrl;
	}

	public void setPullsUrl(String pullsUrl) {
		this.pullsUrl = pullsUrl;
	}

	public String getMilestonesUrl() {
		return milestonesUrl;
	}

	public void setMilestonesUrl(String milestonesUrl) {
		this.milestonesUrl = milestonesUrl;
	}

	public String getNotificationsUrl() {
		return notificationsUrl;
	}

	public void setNotificationsUrl(String notificationsUrl) {
		this.notificationsUrl = notificationsUrl;
	}

	public String getLabelsUrl() {
		return labelsUrl;
	}

	public void setLabelsUrl(String labelsUrl) {
		this.labelsUrl = labelsUrl;
	}

	public String getReleasesUrl() {
		return releasesUrl;
	}

	public void setReleasesUrl(String releasesUrl) {
		this.releasesUrl = releasesUrl;
	}

	public long getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(long createdAt) {
		this.createdAt = createdAt;
	}

	public long getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(long updatedAt) {
		this.updatedAt = updatedAt;
	}

	public long getPushedAt() {
		return pushedAt;
	}

	public void setPushedAt(long pushedAt) {
		this.pushedAt = pushedAt;
	}

	public String getGitUrl() {
		return gitUrl;
	}

	public void setGitUrl(String gitUrl) {
		this.gitUrl = gitUrl;
	}

	public String getSshUrl() {
		return sshUrl;
	}

	public void setSshUrl(String sshUrl) {
		this.sshUrl = sshUrl;
	}

	public String getCloneUrl() {
		return cloneUrl;
	}

	public void setCloneUrl(String cloneUrl) {
		this.cloneUrl = cloneUrl;
	}

	public String getSvnUrl() {
		return svnUrl;
	}

	public void setSvnUrl(String svnUrl) {
		this.svnUrl = svnUrl;
	}

	public String getHomepage() {
		return homepage;
	}

	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getStargazersCount() {
		return stargazersCount;
	}

	public void setStargazersCount(int stargazersCount) {
		this.stargazersCount = stargazersCount;
	}

	public int getWatchersCount() {
		return watchersCount;
	}

	public void setWatchersCount(int watchersCount) {
		this.watchersCount = watchersCount;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public boolean isHasIssues() {
		return hasIssues;
	}

	public void setHasIssues(boolean hasIssues) {
		this.hasIssues = hasIssues;
	}

	public boolean isHasDownloads() {
		return hasDownloads;
	}

	public void setHasDownloads(boolean hasDownloads) {
		this.hasDownloads = hasDownloads;
	}

	public boolean isHasWiki() {
		return hasWiki;
	}

	public void setHasWiki(boolean hasWiki) {
		this.hasWiki = hasWiki;
	}

	public int getForksCount() {
		return forksCount;
	}

	public void setForksCount(int forksCount) {
		this.forksCount = forksCount;
	}

	public int getForks() {
		return forks;
	}

	public void setForks(int forks) {
		this.forks = forks;
	}

	public int getOpenIssues() {
		return openIssues;
	}

	public void setOpenIssues(int openIssues) {
		this.openIssues = openIssues;
	}

	public int getWatchers() {
		return watchers;
	}

	public void setWatchers(int watchers) {
		this.watchers = watchers;
	}

	public String getDefaultBranch() {
		return defaultBranch;
	}

	public void setDefaultBranch(String defaultBranch) {
		this.defaultBranch = defaultBranch;
	}
}