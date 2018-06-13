package thesis.markov.chain.operation;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import thesis.markov.chain.enums.OperationType;
import thesis.markov.chain.model.Statistic;
import thesis.markov.chain.model.Transition;

public abstract class StatisticCalculator {
	public static final int SCALECONST = 4;
	protected static final RoundingMode ROUNDINGMODE = RoundingMode.HALF_UP;
	public static MathContext mathContext = new MathContext(StatisticCalculator.SCALECONST, StatisticCalculator.ROUNDINGMODE);
	protected final IDataCalculator calculator;
	protected Statistic[] statisticList;

	public StatisticCalculator(IDataCalculator calculator, Statistic[] statisticList) {
		this.calculator = calculator;
		this.statisticList = statisticList;

		for (OperationType sourceType : OperationType.values()) {
			for (Statistic statistic : statisticList) {
				statistic.initialOperationToRatio.put(sourceType, new BigDecimal(0));
			}
			for (OperationType sinkType : OperationType.values()) {
				for (Statistic statistic : statisticList) {
					statistic.transitionToRatio.put(new Transition(sourceType, sinkType), new BigDecimal(0));
				}
			}
		}

	}

}
