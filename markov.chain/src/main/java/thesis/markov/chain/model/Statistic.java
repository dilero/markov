package thesis.markov.chain.model;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import thesis.markov.chain.enums.OperationType;

public class Statistic {
	public Map<Transition, BigDecimal> transitionToRatio;
	public Map<OperationType, BigDecimal> initialOperationToRatio;
	public BigDecimal initialProb = new BigDecimal(1);

	public Statistic() {
		transitionToRatio = new HashMap<Transition, BigDecimal>();
		initialOperationToRatio = new HashMap<OperationType, BigDecimal>();
	}

	@Override
	public String toString() {
		return String.format("Statistic [transitionToRatio=%s, initialOperationToRatio=%s]", transitionToRatio,
				initialOperationToRatio);
	}

}
