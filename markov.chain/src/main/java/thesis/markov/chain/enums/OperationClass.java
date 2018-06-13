package thesis.markov.chain.enums;

public enum OperationClass {
	NORMAL("0"), MAL("1"), UNKNOWN("2");
	private String type;

	private OperationClass(String type) {
		this.type = type;
	}

	public static OperationClass getValue(String value) {
		for (OperationClass e : OperationClass.values()) {
			if (e.type.equals(value)) {
				return e;
			}
		}
		return null;
	}

}
