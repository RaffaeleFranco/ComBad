package base;

public class LongMethod extends BadCodeSmell {

	private String methodName;

	public LongMethod(String className, String packageName, String file, int startRow, int endRow, String methodName) {
		super(className, packageName, file, startRow, endRow);
		this.methodName = methodName;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	@Override
	public String toString() {
		return "LongMethod [methodName=" + methodName + ", getClassName()=" + getClassName() + ", getPackageName()="
				+ getPackageName() + ", getFile()=" + getFile() + ", getStartRow()=" + getStartRow() + ", getEndRow()="
				+ getEndRow() + ", getCommitters()=" + getCommitters() + "]";
	}

}
