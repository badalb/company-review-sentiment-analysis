package com.test.core.classifier;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jopendocument.dom.spreadsheet.Sheet;

import com.test.odsreader.ODSReader;

public class SentimentClassifier {

	private static Map<String, List<String>> sentimentClassDictionary;

	public static String classify(String sentiment) {

		for (Map.Entry<String, List<String>> entry : sentimentClassDictionary.entrySet()) {
			//System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			if (entry.getValue().contains(sentiment)) {
				return entry.getKey();
			}
		}
		return null;
	}

	public static void init() {
		sentimentClassDictionary = new HashMap<String, List<String>>();
		String filePath = "/Users/badalb/AppDirectRabbitMq/glassdoor-review-absa/src/main/resources/sentiment-class-dictionary.ods";
		Sheet sheet = ODSReader.readODS(new File(filePath));

		int nRowCount = sheet.getRowCount();
		for (int nRowIndex = 1; nRowIndex < nRowCount; nRowIndex++) {
			String sentimentClass = sheet.getCellAt(0, nRowIndex).getValue().toString();
			String sentimentValue = sheet.getCellAt(1, nRowIndex).getValue().toString();
			List<String> sentimentValues = Arrays.asList(sentimentValue.split("\\s*,\\s*"));
			sentimentClassDictionary.put(sentimentClass, sentimentValues);
		}
	}
}
