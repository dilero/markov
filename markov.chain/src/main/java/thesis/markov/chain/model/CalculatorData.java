package thesis.markov.chain.model;

import java.util.HashMap;
import java.util.Map;

import thesis.markov.chain.enums.OperationClass;
import thesis.markov.chain.enums.OperationType;

public class CalculatorData {
	private Integer initialCounter = 0;
	private Map<OperationType, Integer> initialOperationToCounter;
	private Map<Transition, Integer> transitionToCounter;
	public OperationClass calculatorClass;

	public CalculatorData(OperationClass calculatorClass) {
		this.calculatorClass = calculatorClass;
		this.transitionToCounter = new HashMap<Transition, Integer>();
		this.initialOperationToCounter = new HashMap<OperationType, Integer>();

	}

	@Override
	public String toString() {
		return String.format("CalculatorData [initialOperationToCounter=%s, transitionToCounter=%s]",
				initialOperationToCounter, transitionToCounter);
	}

	public Integer getTotalInitialOperation() {
		initialCounter =0;
		initialOperationToCounter.forEach((key, value) -> {
			initialCounter = initialCounter + value;
		});
		return initialCounter;
	}

	public Integer getTotalTransitionCountOfGivenSource(OperationType sourceType) {
		Integer counter = 0;
		for (Transition transition : Transition.getAllTransitionsOfSource(sourceType)) {
			Integer transitionCount = transitionToCounter.get(transition);
			counter = counter + transitionCount;
		}
		return counter;
	}

	public Map<OperationType, Integer> getInitialOperationToCounter() {
		return initialOperationToCounter;
	}

	public Map<Transition, Integer> getTransitionToCounter() {
		return transitionToCounter;
	}

}