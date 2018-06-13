package thesis.markov.chain;

import java.io.IOException;

import thesis.markov.chain.enums.OperationClass;
import thesis.markov.chain.enums.OperationType;
import thesis.markov.chain.model.Statistic;
import thesis.markov.chain.model.Transition;
import thesis.markov.chain.operation.TrainStatistic;
import thesis.markov.chain.operation.Trainer;

public class AppPrinter {
//	public static final String EX_TRAIN_DATA_PATH = "/home/diler/Desktop/Tez/results/exCode/1-100000/requestedOperations.csv";
//	private static final String EX_TRAIN_DATA_PATH = "/home/diler/Desktop/Tez/results/markov/f80Changed.arff";
	public static final String EX_TRAIN_DATA_PATH = "/home/diler/Desktop/Tez/results/80.arff";

	public static void main(String[] args) throws IOException {
		Trainer trainer = new Trainer(EX_TRAIN_DATA_PATH);
		TrainStatistic totalStatistic = trainer.train();

		printALL(totalStatistic);
		
	}

	public static void printALL(TrainStatistic totalStatistic) {
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
