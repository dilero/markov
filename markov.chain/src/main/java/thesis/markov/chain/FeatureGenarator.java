package thesis.markov.chain;

import java.io.IOException;
import java.math.BigDecimal;

import thesis.markov.chain.model.MarkovFeatures;
import thesis.markov.chain.model.TestData;
import thesis.markov.chain.model.TestDataItem;
import thesis.markov.chain.operation.Classifier;
import thesis.markov.chain.operation.FeatureWriter;

public class FeatureGenarator {
	private static final boolean BAYES = true;

	private static final String oldTrainArffFile = MarkovChain.Root_Path + "f123456789.arff";
	private static final String newTrainArffFile = MarkovChain.Root_Path + "Markov+Weka/f123456789_markov" + BAYES
			+ ".arff";

	private static final String oldTestArffFile = MarkovChain.Root_Path + "f10.arff";
	private static final String newTestArffFile = MarkovChain.Root_Path + "Markov+Weka/f10_markov" + BAYES + ".arff";

	public static void main(String[] args) throws IOException {
		String trainer = oldTrainArffFile;
		String tester = oldTrainArffFile;
		String newFile = newTrainArffFile;

		generate(trainer, tester, newFile);

		trainer = oldTrainArffFile;
		tester = oldTestArffFile;
		newFile = newTestArffFile;

		generate(trainer, tester, newFile);

	}

	private static void generate(String trainer, String tester, String newFile) throws IOException {
		MarkovFeatures markovFeatures = new MarkovFeatures();
		Classifier classifier = new Classifier(trainer, tester, BAYES);

		TestData testData = classifier.getTester().test();
		for (TestDataItem testDataItem : testData.getDataList()) {
			BigDecimal malClassProbability = classifier.getMalClassProbability(testDataItem);
			BigDecimal normalClassProbability = classifier.getNormalClassProbability(testDataItem);
			markovFeatures.malProbabilityList.add(malClassProbability.toString());
			markovFeatures.normalProbabilityList.add(normalClassProbability.toString());
		}

		FeatureWriter featureWriter = new FeatureWriter(tester, newFile, markovFeatures);
		featureWriter.write();
	}
}
