package com.test.core.entityextraction;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;

public class CommonConfig {

	public static Properties configProperties;

	public static String LIB_DIR;
	public static String RESOURCES_DIR;
	public static String MODELS_DIR;
	public static String DATA_DIR;
	public static String INPUT_DIR;
	public static String OUTPUT_DIR;

	public enum Languages {
		EN, SP
	}

	public static Set<Languages> LANGUAGES;

//	private static Map<Languages, String> STOPWORDS_FILE = new ConcurrentHashMap<Languages, String>();
//	private static Map<Languages, String> DICTIONARY_FILE = new ConcurrentHashMap<Languages, String>();
//	private static Map<Languages, String> PRONOUN_WORDS_FILE_LOCATION = new ConcurrentHashMap<Languages, String>();
//	private static Map<Languages, String> WEAK_VERBS_FILE_LOCATION = new ConcurrentHashMap<Languages, String>();
//
//	private static Map<Languages, String> POSSESSION_WORDS = new ConcurrentHashMap<Languages, String>();
//	private static Map<Languages, String> NON_POSSESSION_WORDS = new ConcurrentHashMap<Languages, String>();
//	private static Map<Languages, String> RESOURCES_WORDS = new ConcurrentHashMap<Languages, String>();
//	private static Map<Languages, String> CONSUME_WORDS = new ConcurrentHashMap<Languages, String>();
//	private static Map<Languages, String> NEED_WORDS = new ConcurrentHashMap<Languages, String>();
//	private static Map<Languages, String> DECREASE_WORDS = new ConcurrentHashMap<Languages, String>();
//	private static Map<Languages, String> INCREASE_WORDS = new ConcurrentHashMap<Languages, String>();
//	private static Map<Languages, String> INTENSIFIER_WORDS = new ConcurrentHashMap<Languages, String>();
//	private static Map<Languages, String> NEGATIVE_WORDS = new ConcurrentHashMap<Languages, String>();
//	private static Map<Languages, String> POSITIVE_WORDS = new ConcurrentHashMap<Languages, String>();
//	private static Map<Languages, String> UC_NEGATIVE_WORDS = new ConcurrentHashMap<Languages, String>();
//	private static Map<Languages, String> UC_POSITIVE_WORDS = new ConcurrentHashMap<Languages, String>();
//	private static Map<Languages, String> NPI_WORDS = new ConcurrentHashMap<Languages, String>();
//	private static Map<Languages, String> PPI_WORDS = new ConcurrentHashMap<Languages, String>();
//	private static Map<Languages, String> SHIFTER_WORDS = new ConcurrentHashMap<Languages, String>();
//	private static Map<Languages, String> NON_SENTI_WORDS = new ConcurrentHashMap<Languages, String>();
//	private static Map<Languages, String> POS_TAGS = new ConcurrentHashMap<Languages, String>();
//	private static Map<Languages, String> GREETINGS_SALUTATIONS = new ConcurrentHashMap<Languages, String>();
//	private static Map<Languages, String> ABSTRACT_CONCEPTS_FILE = new ConcurrentHashMap<Languages, String>();
//	private static Map<Languages, String> INTENT_ACTION_FILE = new ConcurrentHashMap<Languages, String>();
//	private static Map<Languages, String> INTENT_OBJECT_FILE = new ConcurrentHashMap<Languages, String>();
//
//	public static String EMOTICONS_FILE;
//	private static Map<Languages, String> IDIOMS_FILE = new ConcurrentHashMap<Languages, String>();
//	private static Map<Languages, String> PHRASAL_VERBS_FILE = new ConcurrentHashMap<Languages, String>();
//	private static Map<Languages, String> EMOTION_KB_FILE = new ConcurrentHashMap<Languages, String>();
//
//	private static Map<Languages, String> ABUSE_WORDS = new ConcurrentHashMap<Languages, String>();
//	private static Map<Languages, String> ADVOCATE_WORDS = new ConcurrentHashMap<Languages, String>();
//	private static Map<Languages, String> SUGGESTION_WORDS = new ConcurrentHashMap<Languages, String>();

	public static String INPUT_REVIEW_FILENAME;
	public static Languages REVIEW_LANGUAGE;
	public static String REVIEW_DELIMITER;
	public static int REVIEW_FIELD;
	public static int MAX_REVIEW_LENGTH;
	public static String EXTRACTED_ENTITY_FILENAME;

	public static String DATE_FORMAT;

	//private static Map<Languages, String> SUFFIX_FILE = new ConcurrentHashMap<Languages, String>();

	public static String UNLISTED_ENTITY_FILE;

//	private static String resourceName(Languages language, String dir, String shortName) {
//		String longName;
//		Pattern stemPattern = Pattern.compile("([\\w|-]+)\\.(\\w+)");
//		Matcher stemMatch = stemPattern.matcher(shortName);
//		String stemStr = null;
//		String fileExt = null;
//
//		switch (language) {
//		case SP:
//			if (stemMatch.find()) {
//				stemStr = stemMatch.group(1);
//				fileExt = stemMatch.group(2);
//			}
//			longName = dir + "/sp/" + stemStr + "-sp." + fileExt;
//			break;
//		default:
//			longName = dir + "/en/" + shortName;
//		}
//
//		return longName;
//	}

//	private static String sharedResourceName(String directory, String shortName) {
//		String longName = directory + "/" + shortName;
//		return longName;
//	}
//
//	private static void addResources(String dir, String resourceShortFilename, Map<Languages, String> resourceMap) {
//		for (Languages language : LANGUAGES) {
//			resourceMap.put(language, resourceName(language, dir, resourceShortFilename));
//		}
//	}

	public static void init(String popFile, String... varargs) throws Exception {

		popFile = "src/main/resources/config/common-config.properties";

		InputStream is = null;

		is = new FileInputStream(new File(popFile));

		configProperties = new Properties();
		configProperties.load(is);

		LIB_DIR = configProperties.getProperty("lib.dir", "lib");
		RESOURCES_DIR = configProperties.getProperty("resources.dir", "resources");
		// RESOURCES_STATIC_DIR =
		// configProperties.getProperty("resources.static.dir",
		// "resources-static");
		DATA_DIR = configProperties.getProperty("data.dir", "data");
		MODELS_DIR = configProperties.getProperty("models.dir", "models");
		INPUT_DIR = configProperties.getProperty("input.dir", "input");
		OUTPUT_DIR = configProperties.getProperty("output.dir", "output");

//		addResources(RESOURCES_DIR, configProperties.getProperty("pronoun_words.file", "pronoun-words.txt"),
//				PRONOUN_WORDS_FILE_LOCATION);
//		addResources(RESOURCES_DIR, configProperties.getProperty("weak_verbs.file", "weak-verbs.txt"),
//				WEAK_VERBS_FILE_LOCATION);
//		addResources(RESOURCES_DIR, configProperties.getProperty("stop_words.file", "stopwords_refined.txt"),
//				STOPWORDS_FILE);
//		addResources(RESOURCES_DIR, configProperties.getProperty("dictionary.file", "dict_words.txt"), DICTIONARY_FILE);
//
//		addResources(RESOURCES_DIR, configProperties.getProperty("resources.words", "resources-words.txt"),
//				RESOURCES_WORDS);
//		addResources(RESOURCES_DIR, configProperties.getProperty("consume.words", "consume-words.txt"), CONSUME_WORDS);
//		addResources(RESOURCES_DIR, configProperties.getProperty("possession.words", "possession-words.txt"),
//				POSSESSION_WORDS);
//		addResources(RESOURCES_DIR, configProperties.getProperty("non.possession.words", "non-possession-words.txt"),
//				NON_POSSESSION_WORDS);
//		addResources(RESOURCES_DIR, configProperties.getProperty("need.words", "need-words.txt"), NEED_WORDS);
//		addResources(RESOURCES_DIR, configProperties.getProperty("decrease.words", "decrease-words.txt"),
//				DECREASE_WORDS);
//		addResources(RESOURCES_DIR, configProperties.getProperty("increase.words", "increase-words.txt"),
//				INCREASE_WORDS);
//		addResources(RESOURCES_DIR, configProperties.getProperty("intensifier.words", "intensifier-words.txt"),
//				INTENSIFIER_WORDS);
//		addResources(RESOURCES_DIR, configProperties.getProperty("negative.words", "negative-words.txt"),
//				NEGATIVE_WORDS);
//		addResources(RESOURCES_DIR, configProperties.getProperty("positive.words", "positive-words.txt"),
//				POSITIVE_WORDS);
//		addResources(RESOURCES_DIR,
//				configProperties.getProperty("uc.negative.words", "unconditional-negative-words.txt"),
//				UC_NEGATIVE_WORDS);
//		addResources(RESOURCES_DIR,
//				configProperties.getProperty("uc.positive.words", "unconditional-positive-words.txt"),
//				UC_POSITIVE_WORDS);
//		addResources(RESOURCES_DIR, configProperties.getProperty("shifter.words", "shifter-words.txt"), SHIFTER_WORDS);
//		addResources(RESOURCES_DIR, configProperties.getProperty("NPI.words", "NPI-words.txt"), NPI_WORDS);
//		addResources(RESOURCES_DIR, configProperties.getProperty("PPI.words", "PPI-words.txt"), PPI_WORDS);
//		addResources(RESOURCES_DIR, configProperties.getProperty("non_senti.words", "non-senti-words.txt"),
//				NON_SENTI_WORDS);
//		EMOTICONS_FILE = sharedResourceName(RESOURCES_DIR,
//				configProperties.getProperty("emoticons.file", "emoticons.txt"));
//		addResources(RESOURCES_DIR, configProperties.getProperty("idioms.file", "idioms.txt"), IDIOMS_FILE);
//		addResources(RESOURCES_DIR, configProperties.getProperty("phrasal.verbs.file", "phrasal-verbs.txt"),
//				PHRASAL_VERBS_FILE);
//		addResources(RESOURCES_DIR,
//				configProperties.getProperty("emotion.kb.file", "NRC-emotions-lexicon-filtered.txt"), EMOTION_KB_FILE);
//		addResources(RESOURCES_DIR, configProperties.getProperty("pos.tags.file", "pos-tags.txt"), POS_TAGS);
//		addResources(RESOURCES_DIR,
//				configProperties.getProperty("greetings.salutations.file", "greetings-salutations.txt"),
//				GREETINGS_SALUTATIONS);
//		addResources(RESOURCES_DIR, configProperties.getProperty("abstract.concepts.file", "abstract-concepts.txt"),
//				ABSTRACT_CONCEPTS_FILE);
//		addResources(RESOURCES_DIR, configProperties.getProperty("suffix.file", "suffix-list.txt"), SUFFIX_FILE);
//
//		addResources(RESOURCES_DIR, configProperties.getProperty("intent.action.file", "intent-action.txt"),
//				INTENT_ACTION_FILE);
//		addResources(RESOURCES_DIR, configProperties.getProperty("intent.object.file", "intent-object.txt"),
//				INTENT_OBJECT_FILE);
//
//		UNLISTED_ENTITY_FILE = sharedResourceName(RESOURCES_DIR,
//				configProperties.getProperty("unlisted.entity.file", "unlisted_entities.txt"));
//
//		addResources(RESOURCES_DIR, configProperties.getProperty("abuse.words", "abuse-words.txt"), ABUSE_WORDS);
//		addResources(RESOURCES_DIR, configProperties.getProperty("advocate.words", "advocate-words.txt"),
//				ADVOCATE_WORDS);
//		addResources(RESOURCES_DIR, configProperties.getProperty("suggestion.words", "suggestion-words.txt"),
//				SUGGESTION_WORDS);

		INPUT_REVIEW_FILENAME = INPUT_DIR + "/"
				+ configProperties.getProperty("input.review.file.name", "woven_data_latest.csv");

		REVIEW_DELIMITER = configProperties.getProperty("review.delimiter", "\\s+");
		REVIEW_FIELD = Integer.parseInt(configProperties.getProperty("review.field", "4"));
		MAX_REVIEW_LENGTH = Integer.parseInt(configProperties.getProperty("max.review.length", "0"));

		EXTRACTED_ENTITY_FILENAME = OUTPUT_DIR + "/"
				+ configProperties.getProperty("extracted.entity.file.name", "extracted-entity.csv");

		DATE_FORMAT = configProperties.getProperty("date.format", "yyyyMMdd_HHmmss");

	}
}
