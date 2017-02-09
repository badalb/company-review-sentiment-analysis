package com.test.core;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.test.core.graphtraversal.StanfordDependencyGraph;
import com.test.initializer.StopWordLoader;

import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.util.CoreMap;

public class ReviewExpression {

	public Map<String, String> getSentiment(String reviewText) {
		ReviewTextProcessor processor = new ReviewTextProcessor(reviewText);

		Map<String, String> reviewEntitySentimentMap = this.processPipeline(processor);
		Map<String, String> refinedReviewEntitySentimentMap = new LinkedHashMap<String, String>();

		for (Map.Entry<String, String> entry : reviewEntitySentimentMap.entrySet()) {
			String entity = entry.getKey();
			String sentiment = entry.getValue();
			if (StopWordLoader.getStopwordList().contains(entity)) {
				continue;
			} else {
				refinedReviewEntitySentimentMap.put(entity, sentiment);
			}
		}
		return refinedReviewEntitySentimentMap;
	}

	public Map<String, String> processPipeline(ReviewTextProcessor processor) {

		processor.process();

		List<CoreMap> sentencesList = processor.getSentences();

		Map<String, String> reviewEntitySentimentMap = new LinkedHashMap<String, String>();

		for (CoreMap sentence : sentencesList) {

			String stanfordSentiment = processor.getStanfordSentiment(sentence);

			SemanticGraph semanticDepGraph = processor.getDependencyGraph(sentence);

			StanfordDependencyGraph sdg = new StanfordDependencyGraph(semanticDepGraph);

			Map<String, Set<String>> entityOpinionMap = null;
			if (sdg != null) {
				entityOpinionMap = sdg.getEntityOpinionMap();
			}

			Map<String, String> entitySentimentMap = new LinkedHashMap<String, String>();
			for (Map.Entry<String, Set<String>> entry : entityOpinionMap.entrySet()) {
				String entity = entry.getKey();
				String sentiment = stanfordSentiment;
				entitySentimentMap.put(entity, sentiment);
			}

			reviewEntitySentimentMap.putAll(entitySentimentMap);
		}

		return reviewEntitySentimentMap;
	}

}
