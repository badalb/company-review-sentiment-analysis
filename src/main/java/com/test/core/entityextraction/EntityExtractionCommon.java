package com.test.core.entityextraction;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import au.com.bytecode.opencsv.CSVWriter;

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
			this.allEntityFile = CommonConfig.EXTRACTED_ENTITY_FILENAME + ".csv";
		} else {
			this.allEntityFile = CommonConfig.OUTPUT_DIR + "/" + fileName + ".csv";
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

//	public void writeMap() {
//		this.sortEntityMap();
//		List<String> entityCountList = new ArrayList<String>();
//		for (Entry<String, Integer> entry : this.allEntityMap.entrySet()) {
//			String entity = entry.getKey();
//			int count = entry.getValue();
//			String oneLine = entity + "\t" + count;
//			entityCountList.add(oneLine);
//		}
//		System.out.println("FileName: " + this.allEntityFile);
//		FileReaderWriter.write_file(entityCountList, this.allEntityFile, false);
//	}
	
	public void writeMap() throws IOException {
        CSVWriter writer = new CSVWriter(new FileWriter(this.allEntityFile),CSVWriter.DEFAULT_SEPARATOR, CSVWriter.NO_QUOTE_CHARACTER);
//        CSVWriter writer = new CSVWriter(new FileWriter(this.allEntityFile,false),',',CSVWriter.NO_QUOTE_CHARACTER);

        this.sortEntityMap();
//        List<String> entityCountList = new ArrayList<String>();
        List<String[]> data = new ArrayList<String[]>();
        data.add(new String[] {"terms", "frequency"});
//        entityCountList.add("Terms,Frequency");
        for (Entry<String, Integer> entry : this.allEntityMap.entrySet()) {
            String[] temp=new String[2];
            temp[0]=entry.getKey();
            temp[1]=entry.getValue().toString();
            data.add(temp);
//            String entity = entry.getKey();
//            int count = entry.getValue();
//            String oneLine = entity + "," + count;
//            entityCountList.add(oneLine);
        }
        System.out.println("FileName: " + this.allEntityFile);
        writer.writeAll(data);
        writer.close();
//        FileReaderWriter.write_file(entityCountList, this.allEntityFile, false);
    }

	public String getAllEntityFile() {
		return this.allEntityFile;
	}

}
