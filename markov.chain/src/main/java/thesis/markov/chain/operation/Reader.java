package thesis.markov.chain.operation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import thesis.markov.chain.enums.DataType;
import thesis.markov.chain.enums.OperationClass;
import thesis.markov.chain.enums.OperationType;
import thesis.markov.chain.model.OperationSequence;

public class Reader {
	private static final String cvsSplitBy = ",";
	private static final String seqSplitBy = ";";
	private final String csvFilePath;
	private BufferedReader bufferReader;
	private String line;
	private final DataType readerType;

	public Reader(String csvFilePath, DataType readerType) throws FileNotFoundException {
		this.csvFilePath = csvFilePath;
		this.readerType = readerType;

	}

	public List<OperationSequence> read() throws IOException {
		FileReader fileReader = new FileReader(new File(csvFilePath));
		bufferReader = new BufferedReader(fileReader);

		List<OperationSequence> opSeqList = new ArrayList<OperationSequence>();

		while ((line = bufferReader.readLine()) != null) {
			if (line.contains("@data")) {
				break;
			}
		}

		while ((line = bufferReader.readLine()) != null) {
			OperationSequence opSeq = readLine(line);
			opSeqList.add(opSeq);

		}

		bufferReader.close();
		return opSeqList;
	}

	private OperationSequence readLine(String line) {
		String[] attributes = line.split(cvsSplitBy);
		String sequences = attributes[4];
		String[] items = sequences.split(seqSplitBy);

		int initialReadIndex = 0;
		String counterString = "1";
		OperationClass classType = OperationClass.UNKNOWN;
		if (readerType.equals(DataType.TRAIN) || readerType.equals(DataType.TEST)) {
			classType = OperationClass.getValue(attributes[5]);
		}
		List<OperationType> opList = new ArrayList<OperationType>();

		for (int i = initialReadIndex; i < items.length; i++) {
			OperationType opreation = OperationType.getValue(items[i]);
			opList.add(opreation);
		}
		return new OperationSequence(classType, Integer.valueOf(counterString), opList);

	}
}