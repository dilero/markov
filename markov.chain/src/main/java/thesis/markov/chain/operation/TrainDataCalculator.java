package thesis.markov.chain.operation;

import java.util.List;

import thesis.markov.chain.enums.OperationClass;
import thesis.markov.chain.enums.OperationType;
import thesis.markov.chain.model.CalculatorData;
import thesis.markov.chain.model.OperationSequence;
import thesis.markov.chain.model.Transition;

public class TrainDataCalculator implements IDataCalculator {
	private CalculatorData malData = new CalculatorData(OperationClass.MAL);
	private CalculatorData normalData = new CalculatorData(OperationClass.NORMAL);

	public TrainDataCalculator() {
		initializeTransitionMap();
		initializeOperationMap();
	}

	private void initializeTransitionMap() {
		for (OperationType sourceType : OperationType.values()) {
			for (OperationType sinkType : OperationType.values()) {
				malData.getTransitionToCounter().put(new Transition(sourceType, sinkType), 0);
				normalData.getTransitionToCounter().put(new Transition(sourceType, sinkType), 0);
			}
		}
	}

	private void initializeOperationMap() {
		for (OperationType sourceType : OperationType.values()) {
			malData.getInitialOperationToCounter().put(sourceType, 0);
			normalData.getInitialOperationToCounter().put(sourceType, 0);
		}
	}

	@Override
	public void calculate(List<OperationSequence> opSeqList) {
		for (OperationSequence operationSequence : opSeqList) {
			updateStatistic(operationSequence);
		}
	}

	protected void updateStatistic(OperationSequence operationSequence) {
		OperationClass classType = operationSequence.getClassType();
		List<OperationType> opList = operationSequence.getOperationList();
		int seqCount = operationSequence.getCount();
		CalculatorData data = getData(classType);

		updateInitialCounter(opList.get(0), seqCount, data);

		for (int i = 0; i < opList.size() - 1; i++) {
			Transition transition = new Transition(opList.get(i), opList.get(i + 1));
			Integer counter = data.getTransitionToCounter().get(transition);
			data.getTransitionToCounter().put(transition, counter + seqCount);
		}

	}

	private void updateInitialCounter(OperationType initialType, int count, CalculatorData data) {
		data.getInitialOperationToCounter().put(initialType,
				data.getInitialOperationToCounter().get(initialType) + count);

	}

	public CalculatorData getData(OperationClass classType) {
		if (classType == OperationClass.MAL) {
			return malData;
		} else if (classType == OperationClass.NORMAL) {
			return normalData;
		}
		return null;
	}

}
