package base;

public class Range {
	private String id;
	private int startRow;
	private int interval;
	private String changeId;
	private Change change;

	public Range(String id, int startRow, int interval, String changeId, Change change) {
		this.id = id;
		this.startRow = startRow;
		this.interval = interval;
		this.changeId = changeId;
		this.change = change;
	}

	public String getId() {
		return id;
	}

	public int getStartRow() {
		return startRow;
	}

	public int getInterval() {
		return interval;
	}

	public String getChangeId() {
		return changeId;
	}

	public Change getChange() {
		return change;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	public void setInterval(int interval) {
		this.interval = interval;
	}

	public void setChangeId(String changeId) {
		this.changeId = changeId;
	}

	public void setChange(Change change) {
		this.change = change;
	}

	@Override
	public String toString() {
		return "Range [id=" + id + ", startRow=" + startRow + ", interval=" + interval + ", changeId=" + changeId + "]";
	}

}
