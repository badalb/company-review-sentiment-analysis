package com.test.core.graphtraversal;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.test.core.PoSTags;

import edu.stanford.nlp.ling.IndexedWord;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphEdge;
import edu.stanford.nlp.trees.GrammaticalRelation;

public class StanfordDependencyGraph {

	private static final Class<?> c = StanfordDependencyGraph.class;
	// private static final Pattern SPECIAL_CHARS_PATTERN =
	// Pattern.compile("[^\\w\\s]", Pattern.UNICODE_CHARACTER_CLASS);

	private SemanticGraph dependencyGraph;
	private List<SemanticGraphEdge> sortedEdges;
	private Set<SemanticGraphEdge> checkedEdge;

	private Map<IndexedWord, List<String>> entityOpinionMap;
	private Map<IndexedWord, String> multiWordEntityMap;

	public StanfordDependencyGraph(SemanticGraph dependencyGraph) {
		checkedEdge = new HashSet<SemanticGraphEdge>();

		if (dependencyGraph != null) {
			this.dependencyGraph = dependencyGraph;
			this.setSortedEdges();
			this.entityOpinionMap = new HashMap<IndexedWord, List<String>>();
			this.multiWordEntityMap = new HashMap<IndexedWord, String>();

			this.fireDependencyRules();
		}
	}

	public void fireDependencyRules() {
		for (SemanticGraphEdge edge : sortedEdges) {

			IndexedWord gov = edge.getGovernor();
			IndexedWord dep = edge.getDependent();

			if (this.checkedEdge.contains(edge)) {
				continue;
			}
			GrammaticalRelation rel = edge.getRelation();
			this.checkedEdge.add(edge);
			try {
				String relationStr = rel.toString();
				String methodName = getMethodName(relationStr);

				if (relationStr.equals("nsubj") || relationStr.equals("compound") || relationStr.equals("nn")
						|| relationStr.equals("amod")|| relationStr.equals("nmod:from")) {
					Method method = c.getMethod(methodName, IndexedWord.class, IndexedWord.class, IndexedWord.class,
							GrammaticalRelation.class);
					method.invoke(this, gov, null, dep, rel);
				} else {
					continue;
				}
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
					| NoSuchMethodException | SecurityException e) {
				e.printStackTrace();
			}

		}

	}

	public void setSortedEdges() {
		this.sortedEdges = this.dependencyGraph.edgeListSorted();
		Collections.sort(this.sortedEdges, new DependencyRelationSorter());
	}

	public String getMethodName(String relation) {
		if (relation.contains(":")) {
			relation = relation.split(":")[0];
		} else if (relation.contains("_")) {
			relation = relation.split("_")[0];
		}

		return relation + "FireRule";
	}

	public void nsubjFireRule(IndexedWord gov, IndexedWord v2, IndexedWord dep, GrammaticalRelation edgeRelation) {
		// String depWord = dep.word();
		String govWord = gov.word();

		if (!PoSTags.isProperNoun(gov.tag())) {
			this.addToOpinionMap(dep, govWord);
		}
	}

	public void nnFireRule(IndexedWord gov, IndexedWord v2, IndexedWord dep, GrammaticalRelation edgeRelation) {
		if (!this.multiWordEntityMap.containsKey(gov)) {
			String govWord = gov.value();
			StringBuilder netEntity = new StringBuilder();
			// System.out.println("Gov: " + gov);
			Set<IndexedWord> prefixList = this.dependencyGraph.getChildrenWithReln(gov, edgeRelation);
			if (prefixList != null) {
				// int i = 0;
				for (IndexedWord prefix : prefixList) {
					netEntity.append(prefix.word()).append(" ");
				}
			}
			String entity = netEntity.append(govWord).toString();
			this.addToMultiWordEntityMap(gov, entity);
		}
	}

	public void compoundFireRule(IndexedWord gov, IndexedWord v2, IndexedWord dep, GrammaticalRelation edgeRelation) {
		String relationName = edgeRelation.toString();
		if (relationName.contains(":")) {
		} else {
			nnFireRule(gov, v2, dep, edgeRelation);
		}
	}

	public void amodFireRule(IndexedWord gov, IndexedWord v2, IndexedWord dep, GrammaticalRelation edgeRelation) {
		String govWord = gov.word().toLowerCase();
		String depWord = dep.word().toLowerCase();

		this.addToOpinionMap(gov, depWord);

		if (v2 != null) {
			addToOpinionMapModified(v2, depWord, govWord);
		}
	}

	public void nmodFireRule(IndexedWord gov, IndexedWord v2, IndexedWord dep, GrammaticalRelation edgeRelation) {
		String govWord = gov.word().toLowerCase();
		String depWord = dep.word().toLowerCase();

		this.addToOpinionMap(gov, depWord);

		if (v2 != null) {
			addToOpinionMapModified(v2, depWord, govWord);
		}
	}
	
	protected void addToOpinionMap(IndexedWord entity, String currOpinion) {
		List<String> opinionSet = this.entityOpinionMap.get(entity);
		currOpinion = currOpinion.toLowerCase();
		if (opinionSet == null) {
			opinionSet = new ArrayList<String>();
			opinionSet.add(currOpinion);
		} else {
			// for (String str : tempSet) {
			int length = opinionSet.size();
			boolean addFlag = true;
			for (int i = 0; i < length; i++) {
				String existingOpinion = opinionSet.get(i);

				if (existingOpinion.contains(currOpinion)) {
					addFlag = false;
					break;
				} else if (currOpinion.contains(existingOpinion)) {
					opinionSet.remove(existingOpinion);
					opinionSet.add(currOpinion);
					addFlag = false;
					break;
				}
			}
			if (addFlag) {
				opinionSet.add(currOpinion);
			}
		}
		this.entityOpinionMap.put(entity, opinionSet);
	}

	private void addToMultiWordEntityMap(IndexedWord entity, String multiWordEntity) {
		this.multiWordEntityMap.put(entity, multiWordEntity);
	}

	private void addToOpinionMapModified(IndexedWord entity, String dep, String gov) {
		List<String> tempSet = this.entityOpinionMap.get(entity);
		String opinion = (dep + " " + gov).toLowerCase();
		if (tempSet == null) {
			tempSet = new ArrayList<String>();
			tempSet.add(opinion);
		} else {
			int length = tempSet.size();
			boolean addFlag = true;
			for (int i = 0; i < length; i++) {
				String str = tempSet.get(i);
				if (str.contains(gov)) {
					tempSet.remove(str);
					str = str.replaceAll(gov, opinion);
					tempSet.add(str);
					addFlag = false;
					break;
				}
			}
			if (addFlag) {
				tempSet.add(opinion);
			}
		}
		this.entityOpinionMap.put(entity, tempSet);
	}

	public Map<String, Set<String>> getEntityOpinionMap() {
		if (this.entityOpinionMap == null) {
			return null;
		}

		Map<String, Set<String>> entityOpinionMap_mod = new LinkedHashMap<String, Set<String>>();

		for (Map.Entry<IndexedWord, List<String>> entry : this.entityOpinionMap.entrySet()) {

			IndexedWord keyIdxWord = entry.getKey();
			String entity = keyIdxWord.word().toLowerCase();

			List<String> opinionList = entry.getValue();
			Set<String> opinionSet = new HashSet<String>(opinionList);

			String entity_multiword = null;
			if (this.multiWordEntityMap.containsKey(keyIdxWord)) {
				entity_multiword = this.multiWordEntityMap.get(keyIdxWord);
			}

			if (entity_multiword != null) {
				entityOpinionMap_mod.put(entity_multiword, opinionSet);
			} else {
				entityOpinionMap_mod.put(entity, opinionSet);
			}

		}
		return entityOpinionMap_mod;
	}

}
