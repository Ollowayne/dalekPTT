package Github;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import Main.ISO8601;

public class User extends SimpleUser {
	
	private String name;
	private String company;
	private String blog;
	private String location;
	private String email;
	private boolean hireable;
	private String bio;
	private int publicRepos;
	private int publicGists;
	private int followers;
	private int following;
	private long createdAt;
	private long updatedAt;
	
	public User() {
		name = company = blog = location = email = bio = "";
		publicRepos = publicGists = followers = following = 0;
		createdAt = updatedAt = 0;
	}
	
	public JsonObject toJsonObject() {
		JsonObjectBuilder builder = super.createObjectBuilder();
		
		builder.add("name", name);
		builder.add("company", company);
		builder.add("blog", blog);
		builder.add("location", location);
		builder.add("email", email);
		builder.add("hireable", hireable);
		builder.add("bio", bio);
		builder.add("public_repos", publicRepos);
		builder.add("public_gists", publicGists);
		builder.add("followers", followers);
		builder.add("following", following);
		builder.add("created_at", ISO8601.fromUnix(createdAt));
		builder.add("updated_at", ISO8601.fromUnix(updatedAt));
		
		return builder.build();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getBlog() {
		return blog;
	}

	public void setBlog(String blog) {
		this.blog = blog;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isHireable() {
		return hireable;
	}

	public void setHireable(boolean hireable) {
		this.hireable = hireable;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public int getPublicRepos() {
		return publicRepos;
	}

	public void setPublicRepos(int publicRepos) {
		this.publicRepos = publicRepos;
	}

	public int getPublicGists() {
		return publicGists;
	}

	public void setPublicGists(int publicGists) {
		this.publicGists = publicGists;
	}

	public int getFollowers() {
		return followers;
	}

	public void setFollowers(int followers) {
		this.followers = followers;
	}

	public int getFollowing() {
		return following;
	}

	public void setFollowing(int following) {
		this.following = following;
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
}
