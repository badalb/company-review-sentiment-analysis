package com.test.main;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.jopendocument.dom.spreadsheet.Sheet;

import com.test.core.entityextraction.FileReaderWriter;
import com.test.odsreader.ODSReader;

public class SourceReviewToTextConverter {

	public static void main(String[] args) {
		//aspectClassDictionary = new HashMap<String, List<String>>();
		String filePath = "/Users/badalb/TravisCI/company-review-sentiment-analysis/src/main/resources/appdirect.ods";
		Sheet sheet = ODSReader.readODS(new File(filePath));
		List<String> aspectValuesList = new ArrayList<>();
		int nRowCount = sheet.getRowCount();
		for (int nRowIndex = 1; nRowIndex < nRowCount; nRowIndex++) {
			//String aspectClass = sheet.getCellAt(0, nRowIndex).getValue().toString().trim();
			//String aspectValue = sheet.getCellAt(1, nRowIndex).getValue().toString().trim();
			String aspectValueStatus = sheet.getCellAt(1, nRowIndex).getValue().toString().trim();
			String aspectValuePros = sheet.getCellAt(2, nRowIndex).getValue().toString().trim();
			String aspectValueCons = sheet.getCellAt(3, nRowIndex).getValue().toString().trim();
			String aspectValueAdvcMngnt = sheet.getCellAt(4, nRowIndex).getValue().toString().trim();
			//List<String> aspectValues = Arrays.asList(aspectValue.split("\\s*,\\s*"));
			aspectValuesList.add(aspectValueStatus);
			aspectValuesList.add(aspectValuePros);
			aspectValuesList.add(aspectValueCons);
			aspectValuesList.add(aspectValueAdvcMngnt);
			//aspectClassDictionary.put(aspectClass, aspectValues);
		}
		FileReaderWriter.write_file(aspectValuesList,
				"/Users/badalb/TravisCI/company-review-sentiment-analysis/src/main/resources/input/review.txt",
				false);
	}
}
