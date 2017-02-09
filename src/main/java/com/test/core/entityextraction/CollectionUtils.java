package com.test.core.entityextraction;

import java.util.*;

public class CollectionUtils {

	public static <K extends Comparable<? super K>, V> Map<K, V> sortByKey(Map<K, V> map) throws Exception {
		Map<K, V> sortedMap = new TreeMap<K, V>(map);
		return sortedMap;

	}

	public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map, boolean isDescending) {
		List<Map.Entry<K, V>> list = new LinkedList<>(map.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
			@Override
			public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
				return (o1.getValue()).compareTo(o2.getValue());
			}
		});

		if (isDescending) {
			Collections.reverse(list);
		}

		Map<K, V> resultMap = new LinkedHashMap<>();
		for (Map.Entry<K, V> entry : list) {
			resultMap.put(entry.getKey(), entry.getValue());
		}
		return resultMap;
	}
}
