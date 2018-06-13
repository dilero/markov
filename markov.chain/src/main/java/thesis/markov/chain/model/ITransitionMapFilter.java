package thesis.markov.chain.model;

import java.util.Map;

import thesis.markov.chain.enums.OperationType;

@FunctionalInterface
public interface ITransitionMapFilter {
	
	 public Map<Transition, Integer> filter(Map<Transition, Integer> map,OperationType type);

}
