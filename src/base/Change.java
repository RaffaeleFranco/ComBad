package base;

import java.util.ArrayList;

public class Change {

	private String id;
	private String commitId;
	private Commit commit;
	private String file;
	private ArrayList<Range> ranges;

	public Change(String id, String commitId, String file) {
		this.id = id;
		this.commitId = commitId;
		this.commit = null;
		this.file = file;
		this.ranges = new ArrayList<Range>();
	}

	public String getId() {
		return id;
	}

	public String getCommitId() {
		return commitId;
	}

	public Commit getCommit() {
		return commit;
	}

	public String getFile() {
		return file;
	}

	public ArrayList<Range> getRanges() {
		return ranges;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setCommitId(String commitId) {
		this.commitId = commitId;
	}

	public void setCommit(Commit commit) {
		this.commit = commit;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public void setRanges(ArrayList<Range> ranges) {
		this.ranges = ranges;
	}

	public void addRange(Range range) {
		this.ranges.add(range);
	}

	@Override
	public String toString() {
		return "Change [id=" + id + ", file=" + file + ", ranges=" + ranges + "]";
	}

}
