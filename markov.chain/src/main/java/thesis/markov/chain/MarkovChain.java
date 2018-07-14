package thesis.markov.chain;

import java.io.IOException;

import thesis.markov.chain.operation.Classifier;

public class MarkovChain {
	private static boolean bayes = false;

	public static void main(String[] args) throws IOException {
		final String trainDataPath = args[0];
		final String testDataPath = args[1];
		final boolean bayes = Boolean.valueOf(args[2]);

		Classifier classifier = new Classifier(trainDataPath, testDataPath, bayes);
		classifier.classify();
		classifier.writeResults(getResultPath());

	}
	private static String getResultPath(){
		return "result" + bayes + ".txt";
	}
	
}
