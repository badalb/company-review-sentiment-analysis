package com.test.core.entityextraction;

import java.util.Map;
import java.util.Set;

public class EntityExtractorThread implements Runnable {

	private String review;

	// private CommonConfig.Languages language;

	private EntityExtractionCommon xpEntity;

	public EntityExtractorThread(CommonConfig.Languages language, String review, EntityExtractionCommon xpEntity) {
		this.review = review;
		// this.language = language;
		this.xpEntity = xpEntity;
	}

	public void run() {
		try {
			Set<String> reviewEntitySet = null;
			EntityExtractor tee = new EntityExtractor();
			reviewEntitySet = tee.extractReviewEntities(review);

			Map<String, Integer> allEntityMap = xpEntity.getAllEntityMap();
			for (String entity : reviewEntitySet) {
				entity = entity.toLowerCase();
				String entityLemma = entity;

				Integer count = allEntityMap.get(entityLemma);
				if (count == null) {
					count = 0;
				}
				count += 1;
				allEntityMap.put(entityLemma, count);
			}
			xpEntity.reviewBatchSize++;
			xpEntity.processedReviewCount++;
			if (xpEntity.reviewBatchSize % 100 == 0) {
				xpEntity.writeMap();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
