package com.test.initializer;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class StopWordLoader {

	private static volatile List<String> stopWordList;

	private StopWordLoader() {

	}

	public static List<String> getStopwordList() {
		if (stopWordList == null) {
			synchronized (StopWordLoader.class) {
				if (stopWordList == null) {
					try {
						stopWordList = FileUtils.readLines(new File("src/main/resources/stopwords_refined.txt"), "utf-8");
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

		}
		return stopWordList;
	}
}
