package com.test.odsreader;

import java.io.File;
import java.io.IOException;

import org.jopendocument.dom.spreadsheet.Sheet;
import org.jopendocument.dom.spreadsheet.SpreadSheet;

public class ODSReader {

	public static Sheet readODS(File file) {
		Sheet sheet= null;
		try {
			// Getting the 0th sheet for manipulation| pass sheet name as string
			sheet = SpreadSheet.createFromFile(file).getSheet(0);

			// Get row count and column count
			//int nColCount = sheet.getColumnCount();
			//int nRowCount = sheet.getRowCount();

//			// Iterating through each row of the selected sheet
//			MutableCell cell = null;
//			for (int nRowIndex = 1; nRowIndex < nRowCount; nRowIndex++) {
//				// Iterating through each column
//				int nColIndex = 0;
//
//				for (; nColIndex < nColCount; nColIndex++) {
//
//					cell = sheet.getCellAt(nColIndex, nRowIndex);
//				}
//
//			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return sheet;
	}

}