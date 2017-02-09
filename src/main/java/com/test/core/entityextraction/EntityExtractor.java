package com.test.core.entityextraction;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.test.core.PoSTags;
import com.test.initializer.PipeLineInitilizer;
import com.test.initializer.StopWordLoader;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.util.CoreMap;

public class EntityExtractor {

	private Annotation document;

	public Set<String> extractReviewEntities(String review) throws Exception {

		Set<String> reviewEntitySet = new HashSet<String>();

		this.annotateDocument(review);
		List<CoreMap> sentencesList = document.get(SentencesAnnotation.class);
		for (CoreMap sentence : sentencesList) {
			List<String> sentenceEntityList = extractEntityList(sentence);
			reviewEntitySet.addAll(sentenceEntityList);
		}
		return reviewEntitySet;
	}

	private void annotateDocument(String review) {
		document = new Annotation(review);
		PipeLineInitilizer.initializePipeline().annotate(document);
	}

	public List<String> extractEntityList(CoreMap sentence) throws Exception {
		List<String> entityList = new ArrayList<String>();
		String entity = "";
		String prevpos = "";
		String lastword = "";
		for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class)) {
			String word = token.originalText().toString().toLowerCase();
			String pos = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);

			if (PoSTags.isCommonNoun(pos) || PoSTags.isProperNounPlural(pos)) {
				entity += " " + word;
				prevpos = pos;
			} else if (PoSTags.isAdjective(pos) && "".equalsIgnoreCase(entity)) {
				entity += " " + word;
				prevpos = pos;
			} else if (PoSTags.isAdjective(pos) && !"".equalsIgnoreCase(entity)) {

				if (PoSTags.isAdjective(prevpos)) {
					entity += " " + word;
				} else {
					if (PoSTags.isIN(prevpos)) {
						entity = entity.replace(lastword, "");
					}
					if (!StopWordLoader.getStopwordList().contains(entity.trim())) {
						entityList.add(entity.trim());
					}
					entity = word;
				}

			} else if (PoSTags.isMainVerb(pos) && "".equalsIgnoreCase(entity)) {
				prevpos = pos;
				entity += " " + word;
				prevpos = pos;
				lastword = word;
			} else if (PoSTags.isIN(pos) && !"".equalsIgnoreCase(entity)) {

				if (PoSTags.isMainVerb(prevpos) || PoSTags.isNoun(prevpos)) {
					prevpos = pos;
					entity += " " + word;
					lastword = word;

				} else {
					if (!StopWordLoader.getStopwordList().contains(entity.trim())) {
						entityList.add(entity.trim());
					}
					entity = "";
					prevpos = "";
				}
			} else {
				if (!entity.equals("")) {
					if (PoSTags.isIN(prevpos)) {
						entity = entity.replace(lastword, "");
					}

					if (!StopWordLoader.getStopwordList().contains(entity.trim())) {
						entityList.add(entity.trim());
					}
				}
				entity = "";
				prevpos = "";
			}
//			if (!StopWordLoader.getStopwordList().contains(entity)) {
//				entityList.add(entity.trim());
//			}
		}
		//System.out.println(entityList);
		return entityList;
	}

	public static void main(String[] args) throws Exception {

		PipeLineInitilizer.initializePipeline();
		StopWordLoader.getStopwordList();

		String doc = "Very good work/life Balance with flexible schedule and possibility to work from home.";

		EntityExtractor tee = new EntityExtractor();
		Set<String> reviewEntitySet = tee.extractReviewEntities(doc);
		System.out.println(reviewEntitySet);
	}

}
