package thesis.markov.chain.enums;

public enum OperationType {
	ADD("add"), DELETE("delete"), READ("read"), UPDATE("update");
	private String operationName;

	private OperationType(String operationName) {
		this.operationName = operationName;
	}

	public static OperationType getValue(String value) {
		for (OperationType e : OperationType.values()) {
			if (e.operationName.equals(value)) {
				return e;
			}
		}
		return null;
	}
}
