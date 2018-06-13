package thesis.markov.chain.model;

import java.util.ArrayList;
import java.util.List;

import thesis.markov.chain.enums.OperationType;

public class Transition {
	private final OperationType sourceOperation;
	private final OperationType sinkOperation;

	public Transition(OperationType sourceOperation, OperationType sinkOperation) {
		super();
		this.sourceOperation = sourceOperation;
		this.sinkOperation = sinkOperation;
	}

	public OperationType getSourceOperation() {
		return sourceOperation;
	}

	public OperationType getSinkOperation() {
		return sinkOperation;
	}

	public static List<Transition> getAllTransitionsOfSource(OperationType sourceType){
		List<Transition> transitions = new ArrayList<Transition>();
		for (OperationType sinkType : OperationType.values()) {
			transitions.add(new Transition(sourceType, sinkType));
		}
		return transitions;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((sinkOperation == null) ? 0 : sinkOperation.hashCode());
		result = prime * result + ((sourceOperation == null) ? 0 : sourceOperation.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Transition other = (Transition) obj;
		if (sinkOperation != other.sinkOperation)
			return false;
		if (sourceOperation != other.sourceOperation)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format("Transition [sourceOperation=%s, sinkOperation=%s]", sourceOperation, sinkOperation);
	}

}
