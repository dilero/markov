package thesis.markov.chain.model;

import java.util.ArrayList;
import java.util.List;

import thesis.markov.chain.enums.OperationClass;
import thesis.markov.chain.enums.OperationType;

public class TestDataItem {
	private final OperationType initialOperation;
	private final List<Transition> transitionList;
	private final OperationClass realClass;

	private TestDataItem(OperationClass realClass, OperationType initialOperation, List<Transition> transitionList) {
		this.initialOperation = initialOperation;
		this.transitionList = transitionList;
		this.realClass = realClass;
	}

	public static TestDataItem create(OperationClass realClass, List<OperationType> operationList) {
		List<Transition> transitions = new ArrayList<Transition>();
		OperationType initOp = operationList.get(0);

		for (int i = 0; i < operationList.size() - 1; i++) {
			transitions.add(new Transition(operationList.get(i), operationList.get(i + 1)));
		}

		return new TestDataItem(realClass, initOp, transitions);
	}

	public OperationType getInitialOperation() {
		return initialOperation;
	}

	public List<Transition> getTransitionList() {
		return transitionList;
	}

	public OperationClass getRealClass() {
		return realClass;
	}

}
