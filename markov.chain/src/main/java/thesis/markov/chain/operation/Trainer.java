package thesis.markov.chain.operation;

import java.io.IOException;
import java.util.List;

import thesis.markov.chain.enums.DataType;
import thesis.markov.chain.model.OperationSequence;

public class Trainer {
	private final String trainFilePath;

	public Trainer(String trainFilePath) {
		this.trainFilePath = trainFilePath;
	}

	public TrainStatistic train() throws IOException {
		Reader reader = new Reader(trainFilePath, DataType.TRAIN);
		List<OperationSequence> opSeqList = reader.read();

		TrainDataCalculator calculator = new TrainDataCalculator();
		calculator.calculate(opSeqList);

		TrainStatistic totalStatistic = new TrainStatistic(calculator);
		totalStatistic.calculateTotalStatistic();

		return totalStatistic;
	}

	public String getTrainFilePath() {
		return trainFilePath;
	}

}
