package com.test.core;

import java.util.List;

import com.test.initializer.PipeLineInitilizer;

import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations.CollapsedCCProcessedDependenciesAnnotation;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.util.CoreMap;

@SuppressWarnings("deprecation")
public class ReviewTextProcessor {

	private String reviewText;

	private Annotation document;

	public ReviewTextProcessor(String reviewStr) {
		this.reviewText = reviewStr;
	}
	
	public void process(){
		preprocess();
		annotateText();
	}
	
	private void preprocess(){
		
		//System.out.println("Original Text :\t" + this.reviewText);
		/* should not hit for 9/10 */
		this.reviewText = this.reviewText.replaceAll("(\\w{2,})/(\\w{2,})", "$1 or $2");
		/* If "....." exists in sentence, then replace it with . and a space */
		this.reviewText = this.reviewText.replaceAll("\\.\\.+", ". ");
		/* If no space exists after a ".". Then add a space */
		this.reviewText = this.reviewText.replaceAll("\\.([A-Z][^\\.])", ". $1");
		/* omit the single letters at the end of a sentence */
		this.reviewText = this.reviewText.replaceAll("\\s\\S\\.", ".");
		this.reviewText = this.reviewText.replaceAll("\\s\\S\\!", "!");
		this.reviewText = this.reviewText.replaceAll("\\s\\S\\Z", "");
		/*
		 * If a lowercaps letter after . and " " then replace with capitalized
		 * version
		 */
		this.reviewText = this.reviewText.replaceAll("\\.([a-z][^\\.])", ". " + "$1".toUpperCase());
		/*
		 * Multiple occurences of same punctuation character is replaced by one.
		 * This leads to wrong parsing .!?\\-
		 */
		this.reviewText = this.reviewText.replaceAll("(\\?|\\*|\\$|\\!|\\-)+", "$1");
		this.reviewText = this.reviewText.replaceAll("\\.([a-z][^\\.])", ". " + "$1".toUpperCase());
		this.reviewText = this.reviewText.replaceAll("[^\\x00-\\x7F]", "");
		this.reviewText = this.reviewText.replaceAll("\\s+", " ");

		//System.out.println("Normalized Text :\t" + this.reviewText);
	}
	
	private void annotateText(){
		this.document =  new Annotation(this.reviewText);
		PipeLineInitilizer.initializePipeline().annotate(this.document);
	}

	public List<CoreMap> getSentences() {
		List<CoreMap> sentencesList = this.document.get(SentencesAnnotation.class);
		return sentencesList;
	}

	public String getStanfordSentiment(CoreMap sentence) {
		return sentence.get(SentimentCoreAnnotations.SentimentClass.class);
	}

	public SemanticGraph getDependencyGraph(CoreMap sentence) {

		// SemanticGraph dependency_graph =
		// sentence.get(SemanticGraphCoreAnnotations.CollapsedCCProcessedDependenciesAnnotation.class);
		SemanticGraph dependencyGraph = sentence.get(CollapsedCCProcessedDependenciesAnnotation.class);
		return dependencyGraph;

	}
}
