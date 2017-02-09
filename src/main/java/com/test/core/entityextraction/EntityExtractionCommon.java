package com.test.core.entityextraction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

public class EntityExtractionCommon {

	private String allEntityFile;
	public int processedReviewCount;
	public int reviewBatchSize;
	public boolean saveFile = true;
	private Map<String, Integer> allEntityMap;

	public EntityExtractionCommon(String fileName) {

		allEntityMap = new ConcurrentHashMap<String, Integer>();
		this.processedReviewCount = 0;
		this.reviewBatchSize = 0;

		if (fileName == null) {
			this.allEntityFile = CommonConfig.EXTRACTED_ENTITY_FILENAME + ".txt";
		} else {
			this.allEntityFile = CommonConfig.OUTPUT_DIR + "/" + fileName + ".txt";
		}
	}

	public Map<String, Integer> getAllEntityMap() {
		return allEntityMap;
	}

	public void setAllEntityMap(Map<String, Integer> allEntityMap) {
		this.allEntityMap = allEntityMap;
	}

	public void sortEntityMap() {
		this.allEntityMap = (HashMap<String, Integer>) CollectionUtils.sortByValue(allEntityMap, true);
	}

	public void writeMap() {
		this.sortEntityMap();
		List<String> entityCountList = new ArrayList<String>();
		for (Entry<String, Integer> entry : this.allEntityMap.entrySet()) {
			String entity = entry.getKey();
			int count = entry.getValue();
			String oneLine = entity + "\t" + count;
			entityCountList.add(oneLine);
		}
		System.out.println("FileName: " + this.allEntityFile);
		FileReaderWriter.write_file(entityCountList, this.allEntityFile, false);
	}

	public String getAllEntityFile() {
		return this.allEntityFile;
	}

}
