package thesis.markov.chain.operation;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;

import thesis.markov.chain.AppPrinter;
import thesis.markov.chain.enums.OperationClass;
import thesis.markov.chain.enums.OperationType;
import thesis.markov.chain.model.Statistic;
import thesis.markov.chain.model.TestData;
import thesis.markov.chain.model.TestDataItem;
import thesis.markov.chain.model.Transition;

public class Classifier {
	private static final String END_LINE = "\n";
	private final Trainer trainer;
	private final Tester tester;
	private final boolean bayes;
	private TrainStatistic trainStatistic;
	private Evaluator evaluator;

	public Classifier(String trainDataPath, String testDataPath, boolean bayes) throws IOException {
		trainer = new Trainer(trainDataPath);
		tester = new Tester(testDataPath);
		trainStatistic = trainer.train();
		this.bayes = bayes;
		AppPrinter.printALL(trainStatistic);
	}

	public Evaluator classify() throws IOException {
		TestData testData = tester.test();
		evaluator = new Evaluator(testData.getDataList().size());
		for (TestDataItem testItem : testData.getDataList()) {
			OperationClass expectedClass = calculateAndClassify(testItem);
			evaluator.evaluateInstance(testItem.getRealClass(), expectedClass);
		}
		return evaluator;
	}

	public BigDecimal getMalClassProbability(TestDataItem testDataItem) {
		return calculateForData(trainStatistic.malStatistic, testDataItem);
	}

	public BigDecimal getNormalClassProbability(TestDataItem testDataItem) {
		return calculateForData(trainStatistic.normalStatistic, testDataItem);
	}

	private OperationClass calculateAndClassify(TestDataItem testDataItem) {

		BigDecimal malClassProbability = getMalClassProbability(testDataItem);
		BigDecimal normalClassProbability = getNormalClassProbability(testDataItem);

		if (normalClassProbability.compareTo(malClassProbability) > 0) {
			return OperationClass.NORMAL;
		}

		return OperationClass.MAL;

	}

	private BigDecimal calculateForData(Statistic statistic, TestDataItem testDataItem) {
		BigDecimal probability = new BigDecimal(1);
		if (bayes) {
			probability = statistic.initialProb.multiply(probability);
		}

		BigDecimal initialProbability = statistic.initialOperationToRatio.get(testDataItem.getInitialOperation());
		probability = probability.multiply(initialProbability, StatisticCalculator.mathContext);

		for (Transition transition : testDataItem.getTransitionList()) {
			BigDecimal transitionProbability = statistic.transitionToRatio.get(transition);
			probability = probability.multiply(transitionProbability, StatisticCalculator.mathContext);
		}
		return probability;
	}

	public Trainer getTrainer() {
		return trainer;
	}

	public Tester getTester() {
		return tester;
	}

	public TrainStatistic getTrainStatistic() {
		return trainStatistic;
	}

	public void writeResults(String filePath) {
		try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath))) {

			bufferedWriter.write("Markoc Chain Results, BAYES = " + bayes + END_LINE);
			bufferedWriter.write(END_LINE);
			bufferedWriter.write("Test Data Path: " + tester.getTestFilePath() + END_LINE);
			bufferedWriter.write("Train Data Path: " + trainer.getTrainFilePath() + END_LINE);
			bufferedWriter.write(END_LINE);
			bufferedWriter.write(END_LINE);
			bufferedWriter.write("RESULTS:" + END_LINE);
			bufferedWriter.write("Correctly Classified Instances  		" + evaluator.getCorrectlyClassifiedInstances()
					+ " 	" + evaluator.getCorrectlyClassifiedPercantage() + "%" + END_LINE);
			bufferedWriter.write("Incorrectly Classified Instances		" + evaluator.getIncorrectClassifiedInstance()
					+ " 	" + evaluator.getIncorrectlyClassifiedPercantage() + "%" + END_LINE + END_LINE + END_LINE);
			bufferedWriter.write("Presicion:		" + evaluator.getPrecision() + END_LINE);
			bufferedWriter.write("Recall   :		" + evaluator.getRecall() + END_LINE);
			bufferedWriter.write("F-Measure:		" + evaluator.getFMeasure() + END_LINE + END_LINE + END_LINE);
			bufferedWriter.write("=== Confusion Matrix ===" + END_LINE);
			bufferedWriter.write(evaluator.measurement.truePositive + " 	" + evaluator.measurement.falsePositive
					+ END_LINE);
			bufferedWriter.write(evaluator.measurement.falseNegative + " 	" + evaluator.measurement.trueNegative
					+ END_LINE + END_LINE + END_LINE);

			writeMatrix(bufferedWriter, OperationClass.MAL, TrainStatistic.malStatistic);
			writeMatrix(bufferedWriter, OperationClass.NORMAL, TrainStatistic.normalStatistic);

		}

		catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void writeMatrix(BufferedWriter bufferedWriter, OperationClass classType, Statistic statistic)
			throws IOException {
		bufferedWriter.write(classType + " INITIAL MATRIX" + END_LINE);
		for (OperationType type : OperationType.values()) {
			bufferedWriter.write("   " + type);
		}
		bufferedWriter.write(END_LINE);
		bufferedWriter.write(statistic.initialOperationToRatio.values() + END_LINE + END_LINE);
		bufferedWriter.write(classType + " TRANSITION MATRIX" + END_LINE);
		for (OperationType type : OperationType.values()) {
			bufferedWriter.write("   " + type);
		}
		bufferedWriter.write(END_LINE);
		for (OperationType sourceType : OperationType.values()) {
			for (OperationType sinkType : OperationType.values()) {
				bufferedWriter.write("  " + statistic.transitionToRatio.get(new Transition(sourceType, sinkType)));
			}
			bufferedWriter.write(END_LINE);

		}
		bufferedWriter.write(END_LINE);
		bufferedWriter.write(END_LINE);
	}

}
