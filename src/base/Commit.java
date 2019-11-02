package base;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;

public class Commit {

	private String id;
	private String emailCommitter;
	private Committer committer;
	private Date date;
	private String description;
	private ArrayList<Change> changes;

	public Commit(String id, String emailCommitter, Committer committer, Date date, String description,
			ArrayList<Change> changes) {
		this.id = id;
		this.emailCommitter = emailCommitter;
		this.committer = committer;
		this.date = date;
		this.description = description;
		this.changes = changes;
	}

	public static Commit read(BufferedReader br) {
		String commitId = null;
		String committerName = null;
		String committerEmail = null;
		Committer committer = null;
		Date commitDate = null;
		String description = null;
		ArrayList<Change> changes = null;
		Commit commit = null;

		try {

			if (!br.ready())
				return null;

			commitId = br.readLine();

			if (commitId.contains("Commit:"))
				commitId = br.readLine();

			committerName = br.readLine();
			committerEmail = br.readLine();

			committer = new Committer(committerName, committerEmail);

			String dateString = br.readLine();

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			try {
				commitDate = sdf.parse(dateString);
			} catch (ParseException e) {
				System.err.println(e.getMessage());
				System.err.println("Data: " + dateString + " del committ non letta ");
				commitDate = new Date();
			}

			description = br.readLine();
			if (description.length() > 20)
				description = description.substring(0, 20);

			int l = description.length();
			while (description.contains(",") || description.contains("'")) {
				l--;
				description = description.substring(0, l);
			}
			
			boolean exit = false;

			String line;

			String fileName;

			Change change = null;
			Range range = null;

			changes = new ArrayList<Change>();
			ArrayList<Range> ranges = new ArrayList<Range>();

			commit = new Commit(commitId, committerEmail, committer, commitDate, description, changes);

			while (br.ready() && !exit) {

				line = br.readLine();

				if (line.contains("Commit:")) {
					exit = true;
				}

				if ((line.contains("--- a") && line.contains(".java"))
						|| (line.contains("--- b") && line.contains(".java"))) {
					fileName = line.substring(6);

					StringTokenizer stringTokenizer = new StringTokenizer(fileName, "/");

					fileName = stringTokenizer.nextToken();

					while (!fileName.contains(".java")) {
						fileName = stringTokenizer.nextToken();
					}

					change = new Change(commitId + fileName, commitId, fileName);
					changes.add(change);

					boolean exit1 = false;

					String line1;

					while (br.ready() && !exit1) {
						line1 = br.readLine();

						if (line1.contains("@@ -")) {
							line1 = line1.substring(4);

							StringTokenizer tokenizer = new StringTokenizer(line1, "@@");
							line1 = tokenizer.nextToken();
							StringTokenizer tokenizer1 = new StringTokenizer(line1, " ");
							line1 = tokenizer1.nextToken();
							line1 = tokenizer1.nextToken();
							line1 = line1.substring(1);

							int startRow;
							int interval;
							StringTokenizer tokenizer2 = new StringTokenizer(line1, ",");
							String startRowString = tokenizer2.nextToken();
							String intervalString = tokenizer2.nextToken();

							startRow = Integer.parseInt(startRowString);
							interval = Integer.parseInt(intervalString);

							range = new Range(commitId + fileName + startRow + interval, startRow, interval,
									change.getId(), change);

							ranges.add(range);
							change.addRange(range);

						} else if (line1.contains("Commit:")) {
							exit1 = true;
							exit = true;
						} else if (line1.contains("diff --git a")) {
							exit1 = true;
						}

					}

				} else if (line.contains("Commit:")) {
					exit = true;
				} else {
					exit = false;
				}

			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return commit;
	}

	public String getId() {
		return id;
	}

	public String getEmailCommitter() {
		return emailCommitter;
	}

	public Committer getCommitter() {
		return committer;
	}

	public Date getDate() {
		return date;
	}

	public String getDescription() {
		return description;
	}

	public ArrayList<Change> getChanges() {
		return changes;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setEmailCommitter(String emailCommitter) {
		this.emailCommitter = emailCommitter;
	}

	public void setCommitter(Committer committer) {
		this.committer = committer;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setChanges(ArrayList<Change> changes) {
		this.changes = changes;
	}

	public void addChange(Change change) {
		this.changes.add(change);
	}

	@Override
	public String toString() {
		return "Commit [id=" + id + ", changes=" + changes + "]";
	}

}
