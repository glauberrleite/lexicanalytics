package org.lexicanalytics.control;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Uses Singleton design pattern, so do not create new instances, instead use
 * Analyser.getInstance(); This class offers results for the Lexicanalytics
 * application.
 * 
 * @author glauberrleite
 *
 */

public class Analyser {

	private static Analyser instance;

	// Text results occurrences
	private String inputText;
	private int numberOfLines;
	private int numberOfWords;
	private Map<String, Integer> occurrences;
	private int numberOfTypes;
	private int numberOfTokens;
	private float ttr;

	private Analyser() {
		numberOfLines = 0;
		numberOfWords = 0;
		occurrences = null;
		numberOfTypes = 0;
		numberOfTokens = 0;
		ttr = 0;
	}

	public static Analyser getInstance() {
		if (instance == null)
			instance = new Analyser();
		return instance;
	}

	public void analyse(String text) {
		if ((text != null) && (text.length() != 0)) {
			// For global accessibility of the analyzed text
			inputText = text;
			
			numberOfLines = text.split("\n").length;

			// There are some words in brazilian grammar that contains :
			// character and still counts as a single word, e.g. 01:00
			text = text.replaceAll("\\:|\\.|\\,", "");

			// Remove spaces and general punctuation then put words on an array
			String words[] = text.trim().split("[^\\p{L}&&^\\P{Alnum}]+");

			numberOfWords = words.length;

			numberOfTokens = numberOfWords; // for now, they are equal

			occurrences = new HashMap<String, Integer>();

			for (int i = 0; i < numberOfWords; i++) {

				int newValue = 1;

				String word = words[i].toLowerCase(); // to make a standard

				if (occurrences.containsKey(word)) {
					newValue = occurrences.get(word) + 1;
				}

				occurrences.put(word, newValue);

			}

			// Sort occurrences by value
			occurrences = sortByComparator(occurrences);

			// Types of words in the text are the keys of occurrences
			numberOfTypes = occurrences.size();

			ttr = (((float) numberOfTypes / (float) numberOfTokens)) * 100;

		}
	}

	public int getNumberOfLines() {
		return numberOfLines;
	}

	public int getNumberOfWords() {
		return numberOfWords;
	}

	public Map<String, Integer> getOccurrences() {
		return occurrences;
	}

	public int getNumberOfTypes() {
		return numberOfTypes;
	}

	public int getNumberOfTokens() {
		return numberOfTokens;
	}

	public float getTTR() {
		return ttr;
	}
	
	public String getInputText(){
		return inputText;
	}

	/**
	 * Method to sort a Map by value. In this application, the higher values
	 * stay on the first positions of the Map. This method is adapted from
	 * http://www.mkyong.com/java/how-to-sort-a-map-in-java/.
	 * 
	 * @param unsortMap
	 *            Map object to sort
	 * @return
	 */
	private static Map<String, Integer> sortByComparator(
			Map<String, Integer> unsortMap) {

		// Convert Map to List
		List<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>(
				unsortMap.entrySet());

		// Sort list with comparator, to compare the Map values
		Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
			public int compare(Map.Entry<String, Integer> o1,
					Map.Entry<String, Integer> o2) {
				return (o1.getValue()).compareTo(o2.getValue()) * (-1); // -1 to
																		// have
																		// a
																		// decreasing
																		// value
			}
		});

		// Convert sorted map back to a Map
		Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
		for (Iterator<Map.Entry<String, Integer>> it = list.iterator(); it
				.hasNext();) {
			Map.Entry<String, Integer> entry = it.next();
			sortedMap.put(entry.getKey(), entry.getValue());
		}
		return sortedMap;
	}

}
