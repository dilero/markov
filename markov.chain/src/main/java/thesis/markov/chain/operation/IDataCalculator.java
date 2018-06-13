package thesis.markov.chain.operation;

import java.util.List;

import thesis.markov.chain.enums.OperationClass;
import thesis.markov.chain.model.CalculatorData;
import thesis.markov.chain.model.OperationSequence;

public interface IDataCalculator {
	
	public void calculate(List<OperationSequence> opSeqList);

	public CalculatorData getData(OperationClass className);

}
