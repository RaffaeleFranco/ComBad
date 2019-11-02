package base;

import java.util.HashMap;
import java.util.HashSet;

public class Committer {

	private String name;
	private String email;
	private HashSet<BadCodeSmell> badCodeSmells;
	private HashMap<String, Commit> commits;

	public Committer(String name, String email) {
		this.name = name;
		this.email = email;
		this.badCodeSmells = new HashSet<BadCodeSmell>();
		this.setCommits(new HashMap<String, Commit>());
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public HashSet<BadCodeSmell> getBadCodeSmells() {
		return badCodeSmells;
	}

	public HashMap<String, Commit> getCommits() {
		return commits;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setBadCodeSmells(HashSet<BadCodeSmell> badCodeSmells) {
		this.badCodeSmells = badCodeSmells;
	}

	public void setCommits(HashMap<String, Commit> commits) {
		this.commits = commits;
	}

	public void addBadCodeSmells(BadCodeSmell badCodeSmell) {
		this.badCodeSmells.add(badCodeSmell);
	}

	public void addCommit(Commit commit) {
		this.commits.put(commit.getId(), commit);
	}

	@Override
	public String toString() {
		return "Committer [name=" + name + ", email=" + email + "]";
	}

}
