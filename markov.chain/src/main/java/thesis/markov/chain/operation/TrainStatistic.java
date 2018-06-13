package thesis.markov.chain.operation;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import thesis.markov.chain.enums.OperationClass;
import thesis.markov.chain.enums.OperationType;
import thesis.markov.chain.model.CalculatorData;
import thesis.markov.chain.model.ITransitionMapFilter;
import thesis.markov.chain.model.Statistic;
import thesis.markov.chain.model.Transition;

public class TrainStatistic extends StatisticCalculator {
	public static Statistic normalStatistic = new Statistic();
	public static Statistic malStatistic = new Statistic();

	public TrainStatistic(IDataCalculator calculator) {
		super(calculator, new Statistic[] { normalStatistic, malStatistic });
	}

	public void calculateTotalStatistic() {
		CalculatorData normalData = calculator.getData(OperationClass.NORMAL);
		CalculatorData malData = calculator.getData(OperationClass.MAL);

		BigDecimal normalInitialCounter = new BigDecimal(normalData.getTotalInitialOperation());
		BigDecimal malInitialCounter = new BigDecimal(malData.getTotalInitialOperation());

		calculateAndUpdateMap(normalData.getInitialOperationToCounter(), normalStatistic.initialOperationToRatio,
				normalInitialCounter);
		calculateAndUpdateMap(malData.getInitialOperationToCounter(), malStatistic.initialOperationToRatio,
				malInitialCounter);

		ITransitionMapFilter transitionMapFilter = (Map<Transition, Integer> map, OperationType type) -> map.entrySet()
				.stream().filter(p -> Transition.getAllTransitionsOfSource(type).contains(p.getKey()))
				.collect(Collectors.toMap(p -> p.getKey(), p -> p.getValue()));

		for (OperationType type : OperationType.values()) {
			Map<Transition, Integer> normalFilter = transitionMapFilter.filter(normalData.getTransitionToCounter(),
					type);
			calculateAndUpdateMap(normalFilter, normalStatistic.transitionToRatio,
					new BigDecimal(normalData.getTotalTransitionCountOfGivenSource(type)));

			Map<Transition, Integer> malFilter = transitionMapFilter.filter(malData.getTransitionToCounter(), type);
			calculateAndUpdateMap(malFilter, malStatistic.transitionToRatio,
					new BigDecimal(malData.getTotalTransitionCountOfGivenSource(type)));

		}

		BigDecimal totalOperation = new BigDecimal(normalData.getTotalInitialOperation()
				+ malData.getTotalInitialOperation());
		BigDecimal normalProb = new BigDecimal(normalData.getTotalInitialOperation()).divide(totalOperation,
				StatisticCalculator.mathContext);
		BigDecimal malProb = new BigDecimal(malData.getTotalInitialOperation()).divide(totalOperation,
				StatisticCalculator.mathContext);

		normalStatistic.initialProb = normalProb;
		malStatistic.initialProb = malProb;

	}

	private <T> void calculateAndUpdateMap(Map<T, Integer> valueMap, Map<T, BigDecimal> statisticMap,
			BigDecimal totalCount) {
		if (totalCount.intValue() != 0) {
			valueMap.forEach((key, value) -> {
				BigDecimal divide = new BigDecimal(value).divide(totalCount, StatisticCalculator.mathContext);
				statisticMap.put(key, divide);
			});
		} else {
			valueMap.forEach((key, value) -> {
				statisticMap.put(key, totalCount);
			});
		}
	}

	@Override
	public String toString() {
		return String.format("TotalStatistic [normalStatistic=%s, malStatistic=%s]", normalStatistic, malStatistic);
	}

}
