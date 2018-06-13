package thesis.markov.chain.operation;

import java.math.BigDecimal;

import thesis.markov.chain.enums.OperationClass;
import thesis.markov.chain.model.Measurement;

public class Evaluator {
	public Measurement measurement = new Measurement();
	private final int totalInstance;

	public Evaluator(int totalInstance) {
		this.totalInstance = totalInstance;
	}

	public void evaluateInstance(OperationClass actual, OperationClass expected) {
		if (!actual.equals(expected)) {
			if (actual.equals(OperationClass.NORMAL)) {
				measurement.falsePositive++;
			} else {
				measurement.falseNegative++;
			}
		} else {
			if (actual.equals(OperationClass.NORMAL)) {
				measurement.trueNegative++;
			} else {
				measurement.truePositive++;
			}
		}

	}

	public int getIncorrectClassifiedInstance() {
		return measurement.falsePositive + measurement.falseNegative;
	}

	public int getTotalInstance() {
		return totalInstance;
	}

	public int getCorrectlyClassifiedInstances() {
		return totalInstance - getIncorrectClassifiedInstance();
	}

	public BigDecimal getCorrectlyClassifiedPercantage() {
		BigDecimal correctly = new BigDecimal(getCorrectlyClassifiedInstances());
		BigDecimal divide = correctly.divide(new BigDecimal(totalInstance), StatisticCalculator.mathContext);
		return divide.multiply(new BigDecimal(100));
	}

	public BigDecimal getIncorrectlyClassifiedPercantage() {
		BigDecimal incorrectly = new BigDecimal(getIncorrectClassifiedInstance());
		BigDecimal divide = incorrectly.divide(new BigDecimal(totalInstance), StatisticCalculator.mathContext);
		return divide.multiply(new BigDecimal(100));
	}

	public BigDecimal getPrecision() {
		BigDecimal denominator = new BigDecimal(measurement.truePositive + measurement.falsePositive);
		if (denominator.compareTo(BigDecimal.ZERO) == 0) {
			return new BigDecimal(0);
		}
		return new BigDecimal(measurement.truePositive).divide(denominator, StatisticCalculator.mathContext);
	}

	public BigDecimal getRecall() {
		BigDecimal denominator = new BigDecimal(measurement.truePositive + measurement.falseNegative);
		if (denominator.compareTo(BigDecimal.ZERO) == 0) {
			return new BigDecimal(0);
		}
		return new BigDecimal(measurement.truePositive).divide(denominator, StatisticCalculator.mathContext);
	}

	public BigDecimal getFMeasure() {
		BigDecimal numerator = getPrecision().multiply(getRecall(), StatisticCalculator.mathContext).multiply(
				new BigDecimal(2), StatisticCalculator.mathContext);
		BigDecimal denominator = getPrecision().add(getRecall());
		if (denominator.compareTo(BigDecimal.ZERO) == 0) {
			return new BigDecimal(0);
		}
		return numerator.divide(denominator, StatisticCalculator.mathContext);
	}

}
