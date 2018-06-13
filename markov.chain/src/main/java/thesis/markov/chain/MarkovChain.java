package thesis.markov.chain;

import java.io.IOException;

import thesis.markov.chain.operation.Classifier;

public class MarkovChain {
	private static boolean bayes = false;
	public static final String Root_Path="/home/diler/Desktop/Tez/results/220616/10/";
	public static final String EX_TEST_DATA_PATH = Root_Path+"f10.arff";
	private static final String EX_TRAIN_DATA_PATH = Root_Path+"f123456789.arff";

	public static void main(String[] args) throws IOException {
		Classifier classifier = new Classifier(EX_TRAIN_DATA_PATH, EX_TEST_DATA_PATH, bayes);
		classifier.classify();
		classifier.writeResults(getResultPath());
		
		bayes=true;
		
		classifier = new Classifier(EX_TRAIN_DATA_PATH, EX_TEST_DATA_PATH, bayes);
		classifier.classify();
		classifier.writeResults(getResultPath());

	}
	private static String getResultPath(){
		return Root_Path+"Markov/result" + bayes + ".txt";
	}
	
}
