package com.test.core.entityextraction;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.test.initializer.PipeLineInitilizer;

public class ExtractionEngine {

	private static boolean mlMode = false;

	private static String CURRENT_DATE_STR = null;
	private static String outputFileName;

	public static String mode;

	public static void setOutputFileName(String fileName) {
		outputFileName = fileName;
	}

	public static String getOutputFileName() {
		return outputFileName;
	}

	public static String getCurrentDateStr() {
		return CURRENT_DATE_STR;
	}

	public static boolean getMachineLearningMode() {
		return mlMode;
	}

	public static void init(String... varargs) {

		String propsFile = "";

		try {
			CommonConfig.init(propsFile, varargs);

			SimpleDateFormat sdf = new SimpleDateFormat(CommonConfig.DATE_FORMAT);
			CURRENT_DATE_STR = sdf.format(Calendar.getInstance().getTime());
			PipeLineInitilizer.initializePipeline();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}