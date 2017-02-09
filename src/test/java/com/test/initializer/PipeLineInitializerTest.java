package com.test.initializer;

import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import junit.framework.Assert;

public class PipeLineInitializerTest {

	public static void main(String[] args) {
		StanfordCoreNLP pipeline = PipeLineInitilizer.initializePipeline();
		Assert.assertTrue(pipeline !=null);
	}

}
