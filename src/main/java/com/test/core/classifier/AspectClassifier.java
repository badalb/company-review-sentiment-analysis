package com.test.core.classifier;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jopendocument.dom.spreadsheet.Sheet;

import com.test.odsreader.ODSReader;

public class AspectClassifier {

	private static Map<String, List<String>> aspectClassDictionary;

	public static String classify(String entity) {
		
		for (Map.Entry<String, List<String>> entry : aspectClassDictionary.entrySet()) {
		   // System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
		    if(entry.getValue().contains(entity)){
		    	return entry.getKey();
		    }
		}
		return null;
	}

	public static void init() {
		aspectClassDictionary = new HashMap<String, List<String>>();
		String filePath = "/Users/badalb/AppDirectRabbitMq/glassdoor-review-absa/src/main/resources/aspect-classification-dictionary.ods";
		Sheet sheet = ODSReader.readODS(new File(filePath));

		int nRowCount = sheet.getRowCount();
		for (int nRowIndex = 1; nRowIndex < nRowCount; nRowIndex++) {
			String aspectClass = sheet.getCellAt(0, nRowIndex).getValue().toString();
			String aspectValue = sheet.getCellAt(1, nRowIndex).getValue().toString();
			List<String> aspectValues = Arrays.asList(aspectValue.split("\\s*,\\s*"));
			aspectClassDictionary.put(aspectClass, aspectValues);
		}
	}
}
