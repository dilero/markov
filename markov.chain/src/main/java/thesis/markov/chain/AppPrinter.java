package thesis.markov.chain;

import java.io.IOException;
import java.util.logging.Logger;

import thesis.markov.chain.enums.OperationClass;
import thesis.markov.chain.enums.OperationType;
import thesis.markov.chain.model.Statistic;
import thesis.markov.chain.model.Transition;
import thesis.markov.chain.operation.TrainStatistic;
import thesis.markov.chain.operation.Trainer;

public class AppPrinter {
	static Logger logger = Logger.getLogger(AppPrinter.class.getName());

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
		logger.info(classType+" INITIAL MATRIX");
		for (OperationType type : OperationType.values()) {
			logger.info( "   "+type);
		}
		statistic.initialOperationToRatio.values().forEach(v ->logger.info(v.toString()));

		logger.info(classType+" TRANSITION MATRIX");
		for (OperationType type : OperationType.values()) {
			logger.info( "   "+type);
		}

		for (OperationType sourceType : OperationType.values()) {
			for (OperationType sinkType : OperationType.values()) {
				logger.info("  "
						+ statistic.transitionToRatio.get(new Transition(sourceType, sinkType)));
			}


		}
	}
}
