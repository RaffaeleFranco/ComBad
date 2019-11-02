package base;

public class LargeClass extends BadCodeSmell {

	/**
	 * @param className
	 * @param packageName
	 * @param file
	 * @param startRow
	 * @param endRow
	 */
	public LargeClass(String className, String packageName, String file, int startRow, int endRow) {
		super(className, packageName, file, startRow, endRow);

	}

	@Override
	public String toString() {
		return "LongClass [getClassName()=" + getClassName() + ", getPackageName()=" + getPackageName() + ", getFile()="
				+ getFile() + ", getStartRow()=" + getStartRow() + ", getEndRow()=" + getEndRow() + ", getCommitters()="
				+ getCommitters() + "]";
	}

}
