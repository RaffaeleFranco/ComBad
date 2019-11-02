package management;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.HashMap;

import base.Change;
import base.Commit;
import base.Committer;
import base.Range;

public class CommitManager {

	private HashMap<String, Commit> commits;
	private HashMap<String, Committer> committers;
	private ArrayList<Change> changes;
	private ArrayList<Range> ranges;

	public CommitManager(BufferedReader br) {
		commits = new HashMap<String, Commit>();
		committers = new HashMap<String, Committer>();
		changes = new ArrayList<Change>();
		ranges = new ArrayList<Range>();

		Commit commit = Commit.read(br);

		while (commit != null) {

			commits.put(commit.getId(), commit);
			committers.put(commit.getCommitter().getEmail(), commit.getCommitter());

			for (Change change : commit.getChanges()) {
				change.setCommit(commit);
				changes.add(change);
				ranges.addAll(change.getRanges());
			}

			commit = Commit.read(br);
		}

	}

	public HashMap<String, Commit> getCommits() {
		return commits;
	}

	public HashMap<String, Committer> getCommitters() {
		return committers;
	}

	public ArrayList<Change> getChanges() {
		return changes;
	}

	public ArrayList<Range> getRanges() {
		return ranges;
	}

	public void setCommits(HashMap<String, Commit> commits) {
		this.commits = commits;
	}

	public void setCommitters(HashMap<String, Committer> committers) {
		this.committers = committers;
	}

	public void setChanges(ArrayList<Change> changes) {
		this.changes = changes;
	}

	public void setRanges(ArrayList<Range> ranges) {
		this.ranges = ranges;
	}

	public void addCommit(Commit commit) {
		this.commits.put(commit.getId(), commit);
	}

	public void addCommitters(Committer committer) {
		this.committers.put(committer.getEmail(), committer);
	}

	public void addChanges(Change change) {
		this.changes.add(change);
	}

	public void addRanges(Range range) {
		this.ranges.add(range);
	}

}
