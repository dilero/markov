package thesis.markov.chain.model;

import java.util.ArrayList;
import java.util.List;

import thesis.markov.chain.enums.OperationClass;
import thesis.markov.chain.enums.OperationType;

public class OperationSequence {
	private final OperationClass classType;
	private int count;
	private List<OperationType> operationList = new ArrayList<OperationType>();

	public OperationSequence(OperationClass classType, int count, List<OperationType> operationList) {
		this.classType = classType;
		this.count = count;
		this.operationList = operationList;
	}

	public OperationClass getClassType() {
		return classType;
	}

	public int getCount() {
		return count;
	}

	public List<OperationType> getOperationList() {
		return operationList;
	}

	@Override
	public String toString() {
		return String.format("OperationSequence [classType=%s, count=%s, operationList=%s]", classType, count,
				operationList);
	}
	

}
