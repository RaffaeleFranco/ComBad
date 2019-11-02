package base;

public class CommitterBadCodeSmell {

	private String committerEmail;
	private String badCodeSmellId;
	private Committer committer;
	private BadCodeSmell badCodeSmell;

	public CommitterBadCodeSmell(String committerEmail, String badCodeSmellId, Committer committer, BadCodeSmell badCodeSmell) {
		this.committerEmail = committerEmail;
		this.badCodeSmellId = badCodeSmellId;
		this.committer = committer;
		this.badCodeSmell = badCodeSmell;
		this.badCodeSmell.getCommitters().put(committer.getEmail(), committer);
		this.committer.addBadCodeSmells(badCodeSmell);
	}

	public String getCommitterEmail() {
		return committerEmail;
	}

	public void setCommitterEmail(String committerEmail) {
		this.committerEmail = committerEmail;
	}

	public String getBadCodeSmellId() {
		return badCodeSmellId;
	}

	public void setBadCodeSmellId(String badCodeSmellId) {
		this.badCodeSmellId = badCodeSmellId;
	}

	public Committer getCommitter() {
		return committer;
	}

	public void setCommitter(Committer committer) {
		this.committer = committer;
	}

	public BadCodeSmell getBadCodeSmell() {
		return badCodeSmell;
	}

	public void setBadCodeSmell(BadCodeSmell badCodeSmell) {
		this.badCodeSmell = badCodeSmell;
	}

	@Override
	public String toString() {
		return "Association [committerEmail=" + committerEmail + ", badCodeSmellId=" + badCodeSmellId + "]";
	}

}
