package com.test.initializer;

import java.util.List;

import com.test.initializer.StopWordLoader;

import junit.framework.Assert;

public class StopWordLoaderTest {

	public static void main(String[] args) {
		
		List<String> stopWordList = StopWordLoader.getStopwordList();
		Assert.assertTrue(stopWordList !=null);
	}

}
