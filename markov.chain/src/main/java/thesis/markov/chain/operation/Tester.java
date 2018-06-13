package thesis.markov.chain.operation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import thesis.markov.chain.enums.DataType;
import thesis.markov.chain.model.OperationSequence;
import thesis.markov.chain.model.TestData;
import thesis.markov.chain.model.TestDataItem;

public class Tester {
	private final String testFilePath;
	private TestData testData;

	public Tester(String testFilePath) {
		this.testFilePath = testFilePath;
	}

	public TestData test() throws IOException {
		testData = new TestData(constructTestData(readTestData()));
		return testData;

	}

	private List<OperationSequence> readTestData() throws IOException {
		Reader reader = new Reader(testFilePath, DataType.TEST);
		return reader.read();
	}

	private List<TestDataItem> constructTestData(List<OperationSequence> operationSequenceList) {
		List<TestDataItem> itemList = new ArrayList<TestDataItem>();
		for (OperationSequence operationSequence : operationSequenceList) {
			itemList.add(TestDataItem.create(operationSequence.getClassType(), operationSequence.getOperationList()));
		}
		return itemList;
	}

	public TestData getTestData() {
		return testData;
	}

	public String getTestFilePath() {
		return testFilePath;
	}

}
