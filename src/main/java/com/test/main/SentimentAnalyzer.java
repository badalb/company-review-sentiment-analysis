package com.test.main;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.test.core.ReviewExpression;
import com.test.core.classifier.AspectClassifier;
import com.test.core.classifier.SentimentClassifier;
import com.test.core.entityextraction.FileReaderWriter;
import com.test.result.OutputResult;

import au.com.bytecode.opencsv.CSVWriter;

public class SentimentAnalyzer {

	private static void writeCSV(Map<String, OutputResult> outputMap) throws IOException {
		CSVWriter writer = new CSVWriter(
				new FileWriter(
						"/Users/badalb/TravisCI/company-review-sentiment-analysis/src/main/resources/output/sentiment-output.csv"),
				CSVWriter.DEFAULT_SEPARATOR, CSVWriter.NO_QUOTE_CHARACTER);

		// List<String> entityCountList = new ArrayList<String>();
		List<String[]> data = new ArrayList<String[]>();
		data.add(new String[] { "Class", "Positive", "Neutral", "Negative" });
		// entityCountList.add("Terms,Frequency");
		for (Entry<String, OutputResult> entry : outputMap.entrySet()) {
			// if (entry.getValue() > 1) {
			String[] temp = new String[4];
			temp[0] = entry.getKey();
			temp[1] = String.valueOf(entry.getValue().getPositive());
			temp[2] = String.valueOf(entry.getValue().getNeutral());
			temp[3] = String.valueOf(entry.getValue().getNegative());
			data.add(temp);
			// }
		}
		// System.out.println("FileName: " + this.allEntityFile);
		writer.writeAll(data);
		writer.close();
	}

	public static void analyzeSentiment() throws IOException {
		Map<String, OutputResult> outputMap = new HashMap<String, OutputResult>();
		List<String> reviewList = new ArrayList<String>();
		String inputFile = "/Users/badalb/TravisCI/company-review-sentiment-analysis/src/main/resources/input/review.txt";
		FileReaderWriter.read_file(inputFile, reviewList);

		AspectClassifier.init();
		SentimentClassifier.init();
		ReviewExpression expression = new ReviewExpression();

		for (String text : reviewList) {

			Map<String, String> reviewEntitySentimentMap = expression.getSentiment(text);
			// System.out.println(reviewEntitySentimentMap);

			for (Map.Entry<String, String> entry : reviewEntitySentimentMap.entrySet()) {

				String aspectClass = AspectClassifier.classify(entry.getKey());
				System.out.println("entry.getKey() = " + entry.getKey() + "=" + "aspectClass = " + aspectClass);
				String sentimentClass = SentimentClassifier.classify(entry.getValue());

				if (aspectClass != null) {

					if (outputMap.containsKey(aspectClass)) {
						OutputResult result = outputMap.get(aspectClass);
						if (sentimentClass.equalsIgnoreCase("positive")) {
							result.setPositive(result.getPositive() + 1);
						} else if (sentimentClass.equalsIgnoreCase("negtaive")) {
							result.setNegative(result.getNegative() + 1);
						} else if (sentimentClass.equalsIgnoreCase("neutral")) {
							result.setNeutral(result.getNeutral() + 1);
						}

					} else {
						OutputResult result = new OutputResult();
						result.setAspact(aspectClass);
						if (sentimentClass.equalsIgnoreCase("positive")) {
							result.setPositive(result.getPositive() + 1);
						} else if (sentimentClass.equalsIgnoreCase("negative")) {
							result.setNegative(result.getNegative() + 1);
						} else if (sentimentClass.equalsIgnoreCase("neutral")) {
							result.setNeutral(result.getNeutral() + 1);
						}
						outputMap.put(aspectClass, result);
					}
				}

			}
		}
		writeCSV(outputMap);
		System.out.println(outputMap);
	}

	public static void main(String[] args) throws IOException {

		analyzeSentiment();
	}
}
