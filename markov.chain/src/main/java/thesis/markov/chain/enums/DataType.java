package thesis.markov.chain.enums;

public enum DataType {
	TEST("0"), TRAIN("1");
	private String type;

	private DataType(String type) {
		this.type = type;
	}

	public static DataType getValue(String value) {
		for (DataType e : DataType.values()) {
			if (e.type.equals(value)) {
				return e;
			}
		}
		return null;
	}

}
