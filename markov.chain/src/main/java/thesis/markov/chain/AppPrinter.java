package thesis.markov.chain;

import java.io.IOException;

import thesis.markov.chain.enums.OperationClass;
import thesis.markov.chain.enums.OperationType;
import thesis.markov.chain.model.Statistic;
import thesis.markov.chain.model.Transition;
import thesis.markov.chain.operation.TrainStatistic;
import thesis.markov.chain.operation.Trainer;

public class AppPrinter {

	public static void main(String[] args) throws IOException {
		final String trainDataPath = args[0];

		Trainer trainer = new Trainer(trainDataPath);
		TrainStatistic totalStatistic = trainer.train();

		printAll(totalStatistic);
		
	}

	public static void printAll(TrainStatistic totalStatistic) {
		print(OperationClass.MAL, totalStatistic.malStatistic);
		print(OperationClass.NORMAL, totalStatistic.normalStatistic);
	}

	private static void print(OperationClass classType, Statistic statistic) {
		System.out.println(classType+" INITIAL MATRIX");
		for (OperationType type : OperationType.values()) {
			System.out.print( "   "+type);
		}
		System.out.println("");
		System.out.println(statistic.initialOperationToRatio.values());
		System.out.println("");
		System.out.println(classType+" TRANSITION MATRIX");
		for (OperationType type : OperationType.values()) {
			System.out.print( "   "+type);
		}
		System.out.println();
		for (OperationType sourceType : OperationType.values()) {
			for (OperationType sinkType : OperationType.values()) {
				System.out.print("  "
						+ statistic.transitionToRatio.get(new Transition(sourceType, sinkType)));
			}
			System.out.println("");

		}
		System.out.println("");
		System.out.println("");
	}
}
