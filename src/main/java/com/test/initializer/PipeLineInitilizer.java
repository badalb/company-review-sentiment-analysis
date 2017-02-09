package com.test.initializer;

import java.util.Properties;

import edu.stanford.nlp.pipeline.StanfordCoreNLP;

public class PipeLineInitilizer {

	private static StanfordCoreNLP pipeline;

	private PipeLineInitilizer() {

	}

	public static StanfordCoreNLP initializePipeline() {
		if (pipeline == null) {
			synchronized (PipeLineInitilizer.class) {
				if (pipeline == null) {
					Properties props = new Properties();
					props.put("tokenize.options", "ptb3Escaping=false");
					props.put("parse.maxlen", "10000");
					props.put("pos.model",
							"edu/stanford/nlp/models/pos-tagger/english-caseless-left3words-distsim.tagger");
					props.put("annotators", "tokenize,ssplit,pos,lemma,ner,parse,sentiment");
					pipeline = new StanfordCoreNLP(props);
				}
			}

		}
		return pipeline;
	}
}
