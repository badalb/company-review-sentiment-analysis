package com.test.main;

import java.io.IOException;

import org.rosuda.REngine.Rserve.RserveException;

public class Main {

	public static void main(String[] args) throws IOException, RserveException {

		// 1. Convert input reviews to flat text
		SourceReviewToTextConverter.convertToText();
		// 2. Convert Text from setp1 to Entity Set CSV
		TextToEntitySetCSV.createEntitySet();
		// 3. Run wordcloud R script.
		String filePath = "/Users/badalb/TravisCI/company-review-sentiment-analysis/src/main/resources/rscript/review-wordcloud.R";
		ExecuteRScript.executeScript(filePath);
		// 4. Analyze sentiment
		SentimentAnalyzer.analyzeSentiment();
		// 5. Run sentiment analysis R script
		filePath = "/Users/badalb/TravisCI/company-review-sentiment-analysis/src/main/resources/rscript/sentiment_analysis.R";
		ExecuteRScript.executeScript(filePath);
	}

}
