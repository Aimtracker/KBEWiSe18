package de.htw.ai.kbe.servlet.utils;

import java.util.List;

public class ListEntriesToString {
	public static <T> String toString (List<T> list, String seperatedString) {
		StringBuilder resultString = new StringBuilder();
		for (T entryInList : list) {
			resultString.append(entryInList).append(seperatedString);
		}return resultString.toString();
	}
}
