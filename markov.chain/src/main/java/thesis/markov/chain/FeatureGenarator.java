package thesis.markov.chain;

import java.io.IOException;
import java.math.BigDecimal;

import thesis.markov.chain.model.MarkovFeatures;
import thesis.markov.chain.model.TestData;
import thesis.markov.chain.model.TestDataItem;
import thesis.markov.chain.operation.Classifier;
import thesis.markov.chain.operation.FeatureWriter;

public class FeatureGenarator {

	public static void main(String[] args) throws IOException {
		final String trainer = args[0];
		final String tester = args[1];
		final String newFile =args[2];
		final boolean bayes = Boolean.valueOf(args[3]);

		generate(trainer, tester, newFile, bayes);

	}

	private static void generate(String trainer, String tester, String newFile, boolean bayes) throws IOException {
		MarkovFeatures markovFeatures = new MarkovFeatures();
		Classifier classifier = new Classifier(trainer, tester, bayes);

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
