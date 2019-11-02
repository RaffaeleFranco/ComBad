package base;

public class DuplicatedCode extends BadCodeSmell {

	private int interval;

	/**
	 * @param className
	 * @param packageName
	 * @param file
	 * @param startRow
	 * @param endRow
	 * @param interval
	 */
	public DuplicatedCode(String className, String packageName, String file, int startRow, int endRow, int interval) {
		super(className, packageName, file, startRow, endRow);
		this.interval = interval;
	}

	public int getInterval() {
		return interval;
	}

	public void setInterval(int interval) {
		this.interval = interval;
	}

	@Override
	public String toString() {
		return "DuplicatedCode [interval=" + interval + ", getClassName()=" + getClassName() + ", getPackageName()="
				+ getPackageName() + ", getFile()=" + getFile() + ", getStartRow()=" + getStartRow() + ", getEndRow()="
				+ getEndRow() + ", getCommitters()=" + getCommitters() + "]";
	}

}
