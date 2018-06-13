package thesis.markov.chain.operation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import thesis.markov.chain.model.MarkovFeatures;

public class FeatureWriter {
	private static final String cvsSplitBy = ",";
	private final String oldArffFileName;
	private final String newArffFileName;
	private final MarkovFeatures markovFeatures;

	public FeatureWriter(String oldArffFileName, String newArffFileName, MarkovFeatures markovFeatures) {
		this.oldArffFileName = oldArffFileName;
		this.newArffFileName = newArffFileName;
		this.markovFeatures = markovFeatures;
	}

	public void write() {
		try (BufferedReader bufferedReader = new BufferedReader(new FileReader(oldArffFileName));
				BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(newArffFileName))) {
			String line;
			for (int i = 0; i < 10; ++i) {
				line = bufferedReader.readLine();
				if (i == 8) {
					String newAttribure = "@attribute malProbability numeric\n@attribute normalProbability numeric\n";
					bufferedWriter.write(newAttribure);
				}
				bufferedWriter.write(line + "\n");
			}
			int initialIndex = 0;
			while ((line = bufferedReader.readLine()) != null) {
				line = new StringBuilder(line).append(cvsSplitBy)
						.append(markovFeatures.malProbabilityList.get(initialIndex)).append(cvsSplitBy)
						.append(markovFeatures.normalProbabilityList.get(initialIndex)).append("\n").toString();
				bufferedWriter.write(line);
				initialIndex++;
			}
		}

		catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
