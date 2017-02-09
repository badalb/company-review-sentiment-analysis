package com.test.core.graphtraversal;

import edu.stanford.nlp.semgraph.SemanticGraphEdge;
import edu.stanford.nlp.trees.UniversalEnglishGrammaticalRelations;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class DependencyRelationSorter implements Comparator<SemanticGraphEdge> {

	private static final List<String> ORDERED_ENTRIES = Arrays.asList(
			UniversalEnglishGrammaticalRelations.NEGATION_MODIFIER.toString(),
			UniversalEnglishGrammaticalRelations.SEMANTIC_DEPENDENT.toString(),
			UniversalEnglishGrammaticalRelations.COMPOUND_MODIFIER.toString(),
			UniversalEnglishGrammaticalRelations.NOMINAL_SUBJECT.toString(),
			UniversalEnglishGrammaticalRelations.NOMINAL_PASSIVE_SUBJECT.toString(),
			UniversalEnglishGrammaticalRelations.CLAUSAL_COMPLEMENT.toString(),
			UniversalEnglishGrammaticalRelations.XCLAUSAL_COMPLEMENT.toString(),
			UniversalEnglishGrammaticalRelations.NUMERIC_MODIFIER.toString());

	@Override
	public int compare(SemanticGraphEdge o1, SemanticGraphEdge o2) {

		String o1str = o1.getRelation().toString();
		String o2str = o2.getRelation().toString();

		if (ORDERED_ENTRIES.contains(o1str) || ORDERED_ENTRIES.contains(o2str)) {
			if (ORDERED_ENTRIES.contains(o1str) && ORDERED_ENTRIES.contains(o2str)) {
				/*
				 * Both objects are in our ordered list. Compare them by their
				 * position in the list
				 */
				return ORDERED_ENTRIES.indexOf(o1str) - ORDERED_ENTRIES.indexOf(o2str);
			}

			if (ORDERED_ENTRIES.contains(o1str)) {
				/*
				 * o1 is in the ordered list, but o2 isn't. o1 is smaller (i.e.
				 * first)
				 */
				return -1;
			}

			if (ORDERED_ENTRIES.contains(o2str)) {
				/*
				 * o2 is in the ordered list, but o1 isn't. o2 is smaller (i.e.
				 * first)
				 */
				return 1;
			}
		}
		if (o1str.endsWith("mod") || o2str.endsWith("mod")) {
			if (o1str.endsWith("mod") && o2str.endsWith("mod")) {
				return o1str.compareTo(o2str);
			}
			if (o1str.endsWith("mod")) {
				return -1;
			}
			if (o2str.endsWith("mod")) {
				return 1;
			}
		}

		return o1str.compareTo(o2str);
	}
}
