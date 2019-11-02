package base;

public class LongParameterList extends BadCodeSmell {

	private String method;

	/**
	 * @param className
	 * @param packageName
	 * @param file
	 * @param startRow
	 * @param endRow
	 * @param method
	 */
	public LongParameterList(String className, String packageName, String file, int startRow, int endRow,
			String method) {
		super(className, packageName, file, startRow, endRow);
		this.method = method;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	@Override
	public String toString() {
		return "LongParametersList [method=" + method + ", getClassName()=" + getClassName() + ", getPackageName()="
				+ getPackageName() + ", getFile()=" + getFile() + ", getStartRow()=" + getStartRow() + ", getEndRow()="
				+ getEndRow() + ", getCommitters()=" + getCommitters() + "]";
	}

}
