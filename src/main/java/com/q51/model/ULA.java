package com.q51.model;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class ULA {

	private SortedMap<String, String> ulaConnections;

	public ULA() {
		ulaConnections = new TreeMap<String, String>();
	}

	public SortedMap<String, String> getULAConnections() {
		return ulaConnections;
	}

	private <V> SortedMap<String, V> filterPrefix(SortedMap<String, V> baseMap, String prefix) {
		if (prefix.length() > 0) {
			char nextLetter = (char) (prefix.charAt(prefix.length() - 1) + 1);
			String end = prefix.substring(0, prefix.length() - 1) + nextLetter;
			return baseMap.subMap(prefix, end);
		}
		return baseMap;
	}

	public void printValues(String keyPrefix) {
		for (Map.Entry<String, String> entry : filterPrefix(ulaConnections, keyPrefix).entrySet()) {
			System.out.println(entry);
		}
	}

	public TreeMap<String, String> getMapping(String keyPrefix) {

		TreeMap<String, String> mapping = new TreeMap<String, String>();

		for (Map.Entry<String, String> entry : filterPrefix(ulaConnections, keyPrefix).entrySet()) {
			mapping.put(entry.getKey(), entry.getValue());
		}

		return mapping;
	}

}
