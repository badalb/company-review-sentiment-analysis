package com.test.main;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.test.core.entityextraction.CommonConfig;
import com.test.core.entityextraction.EntityExtractionCommon;
import com.test.core.entityextraction.EntityExtractorThread;
import com.test.core.entityextraction.ExtractionEngine;
import com.test.core.entityextraction.FileReaderWriter;
import com.test.core.entityextraction.ThreadUtils;

public class TextToEntitySetCSV {

	public static void main(String[] args) throws Exception {

		ExtractionEngine.init();

		EntityExtractionCommon tkEntity = new EntityExtractionCommon(null);
		tkEntity.processedReviewCount++;
		String inputFile = CommonConfig.INPUT_REVIEW_FILENAME;
		CommonConfig.Languages language = CommonConfig.Languages.EN;
		List<String> reviewList = new ArrayList<String>();
		FileReaderWriter.read_file(inputFile, reviewList);

		ExecutorService executor = Executors.newFixedThreadPool(16);

		for (String oneLine : reviewList) {
			Runnable worker = new EntityExtractorThread(language, oneLine, tkEntity);
			executor.execute(worker);
		}
		ThreadUtils.shutdownAndAwaitTermination(executor);

		System.out.println("Finished all threads");

		tkEntity.writeMap();
		return;
	}

}
