package base;

public class DeadCode extends BadCodeSmell {

	private String type;
	private String variable;

	/**
	 * @param className
	 * @param packageName
	 * @param file
	 * @param startRow
	 * @param endRow
	 * @param type
	 * @param variable
	 */
	public DeadCode(String className, String packageName, String file, int startRow, int endRow, String type,
			String variable) {
		super(className, packageName, file, startRow, endRow);
		this.type = type;
		this.variable = variable;

	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getVariable() {
		return variable;
	}

	public void setVariable(String variable) {
		this.variable = variable;
	}

	@Override
	public String toString() {
		return "DeadCode [type=" + type + ", variable=" + variable + ", getClassName()=" + getClassName()
				+ ", getPackageName()=" + getPackageName() + ", getFile()=" + getFile() + ", getStartRow()="
				+ getStartRow() + ", getEndRow()=" + getEndRow() + ", getCommitters()=" + getCommitters() + "]";
	}

}
