package base;

import java.util.ArrayList;

public class Duplication {

	private ArrayList<DuplicatedCode> duplicatedCodes;

	public Duplication() {
		this.duplicatedCodes = new ArrayList<DuplicatedCode>();
	}

	/**
	 * @param duplicatedCodes
	 */
	public Duplication(ArrayList<DuplicatedCode> duplicatedCodes) {
		this.duplicatedCodes = duplicatedCodes;
	}

	public ArrayList<DuplicatedCode> getDuplicatedCodes() {
		return duplicatedCodes;
	}

	public void setDuplicatedCodes(ArrayList<DuplicatedCode> duplicatedCodes) {
		this.duplicatedCodes = duplicatedCodes;
	}

	public void addDuplicatedCodes(DuplicatedCode duplicatedCode) {
		this.duplicatedCodes.add(duplicatedCode);
	}

	@Override
	public String toString() {
		return "Duplication [duplicatedCodes=" + duplicatedCodes + "]";
	}

}
