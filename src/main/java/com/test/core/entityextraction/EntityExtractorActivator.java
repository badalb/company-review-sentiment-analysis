package com.test.core.entityextraction;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EntityExtractorActivator {

	public static void main(String[] args) throws Exception {

		ExtractionEngine.init();

		EntityExtractionCommon tkEntity = new EntityExtractionCommon(null);
		tkEntity.processedReviewCount++;
		String inputFile = CommonConfig.INPUT_REVIEW_FILENAME;
		CommonConfig.Languages language = CommonConfig.Languages.EN;
		List<String> reviewList = new ArrayList<String>();
		FileReaderWriter.read_file(inputFile, reviewList);

		ExecutorService executor = Executors.newFixedThreadPool(16);

	//	int i = 0;
		for (String oneLine : reviewList) {
//			System.out.println("\n$$review count: " + i);
//			i++;
//			String[] lineParts = oneLine.split(CommonConfig.REVIEW_DELIMITER);
//			if (lineParts.length < CommonConfig.REVIEW_FIELD + 1) {
//				continue;
//			}
//			String review = lineParts[CommonConfig.REVIEW_FIELD];
//			System.out.println("@@review: " + review);
//			int maxReviewLength = CommonConfig.MAX_REVIEW_LENGTH;
//			if (maxReviewLength != 0 && review.length() > maxReviewLength) {
//				continue;
//			}

			Runnable worker = new EntityExtractorThread(language, oneLine, tkEntity);
			executor.execute(worker);
		}
		ThreadUtils.shutdownAndAwaitTermination(executor);

		System.out.println("Finished all threads");

		tkEntity.writeMap();
		return;
	}
}
