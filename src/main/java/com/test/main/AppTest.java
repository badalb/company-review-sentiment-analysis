package com.test.main;

import java.util.HashMap;
import java.util.Map;

import com.test.core.ReviewExpression;
import com.test.core.classifier.AspectClassifier;
import com.test.core.classifier.SentimentClassifier;
import com.test.result.OutputResult;

public class AppTest {

	public static void main(String[] args) {

		String[] text = { 
				//"Work culture is good. Work life balance is poor. I don't recommend this company.",
				"possibility to work from home.",
				//"Technical knowledge is very good, engineers are mature and experienced." 
				};
		Map<String, OutputResult> outputMap = new HashMap<String, OutputResult>();
		
		// aspect and sentiment classify
		AspectClassifier.init();
		SentimentClassifier.init();
		ReviewExpression expression = new ReviewExpression();

		for (int i = 0; i < text.length; i++) {

			Map<String, String> reviewEntitySentimentMap = expression.getSentiment(text[i]);
			System.out.println(reviewEntitySentimentMap);

			for (Map.Entry<String, String> entry : reviewEntitySentimentMap.entrySet()) {

				String aspectClass = AspectClassifier.classify(entry.getKey());
				String sentimentClass = SentimentClassifier.classify(entry.getValue());

				if (aspectClass != null) {

					if (outputMap.containsKey(aspectClass)) {
						OutputResult result = outputMap.get(aspectClass);
						if (sentimentClass.equalsIgnoreCase("positive")) {
							result.setPositive(result.getPositive() + 1);
						} else if (sentimentClass.equalsIgnoreCase("negtaive")) {
							result.setNegative(result.getNegative() + 1);
						} else if (sentimentClass.equalsIgnoreCase("neutral")) {
							result.setNeutral(result.getNeutral() + 1);
						}

					} else {
						OutputResult result = new OutputResult();
						result.setAspact(aspectClass);
						if (sentimentClass.equalsIgnoreCase("positive")) {
							result.setPositive(result.getPositive() + 1);
						} else if (sentimentClass.equalsIgnoreCase("negative")) {
							result.setNegative(result.getNegative() + 1);
						} else if (sentimentClass.equalsIgnoreCase("neutral")) {
							result.setNeutral(result.getNeutral() + 1);
						}
						outputMap.put(aspectClass, result);
					}
				}

			}
		}

		System.out.println(outputMap);
	}
}
