package base;

import java.util.HashMap;

public abstract class BadCodeSmell {
	
	private String className;
	private String packageName;
	private String file;
	private int startRow;
	private int endRow;
	private HashMap<String, Committer> committers;
	
	/**
	 * @param className
	 * @param packageName
	 * @param file
	 * @param startRow
	 * @param endRow
	 */
	public BadCodeSmell(String className, String packageName, String file, int startRow, int endRow) {
		this.className = className;
		this.packageName = packageName;
		this.file = file; 
		this.startRow = startRow;
		this.endRow = endRow;
		this.committers = new HashMap<String, Committer>();
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public int getStartRow() {
		return startRow;
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	public int getEndRow() {
		return endRow;
	}

	public void setEndRow(int endRow) {
		this.endRow = endRow;
	}

	public HashMap<String, Committer> getCommitters() {
		return committers;
	}

	public void setCommitters(HashMap<String, Committer> committers) {
		this.committers = committers;
	}

	public void addCommitter(Committer committer) {
		this.committers.put(committer.getEmail(), committer);
	}

}
