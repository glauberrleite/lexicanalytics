package org.lexicanalytics.control;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.lexicanalytics.model.Production;
import org.lexicanalytics.persistence.ProductionDAO;
import org.lexicanalytics.persistence.ProductionList;

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

	public ProductionDAO productions;
	
	private Analyser() {
		productions = new ProductionList();
	}

	public static Analyser getInstance() {
		if (instance == null)
			instance = new Analyser();
		return instance;
	}
	
	public void analyseAllProductions(){
		for (Production production : productions.listAll()){
			analyse(production);
		}
	}

	public void analyse(Production production) {

		production.setNumberOfLines(production.getText().split("\n").length);

		// There are some words in Brazilian grammar that contains :
		// character in between other characters and still counts as a
		// single word, e.g. 01:00
		String fixedText = production.getText().replaceAll("\\:|\\.|\\,", "");

		// Remove spaces and general punctuation then put words on an array
		String words[] = fixedText.trim().split("[^\\p{L}&&^\\P{Alnum}]+");

		production.setNumberOfWords(words.length);

		// For now, number of tokens are defined by the number of words
		production.setNumberOfTokens(production.getNumberOfWords()); 

		// Created a local variable to avoid constant new allocations of Map
		// instances when calling
		// production.getOccurrences();
		Map<String, Integer> occurrences = production.getOccurrences();

		for (int i = 0; i < words.length; i++) {

			int newValue = 1;

			String word = words[i].toLowerCase(); // to make a standard

			if (occurrences.containsKey(word)) {
				newValue = occurrences.get(word) + 1;
			}

			occurrences.put(word, newValue);

		}

		// Sort occurrences by value
		occurrences = sortByComparator(occurrences);

		// After manipulating the map, sending it to the production instance
		production.setOccurrences(occurrences);

		// Types of words in the text are the keys of occurrences
		production.setNumberOfTypes(occurrences.size());

		float ttr = (((float) production.getNumberOfTypes() / (float) production.getNumberOfTokens())) * 100;
		
		production.setTtr(ttr);

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
	private static Map<String, Integer> sortByComparator(Map<String, Integer> unsortMap) {

		// Convert Map to List
		List<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>(unsortMap.entrySet());

		// Sort list with comparator, to compare the Map values
		Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
			public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
				return (o1.getValue()).compareTo(o2.getValue()) * (-1); // -1 to
																		// have
																		// a
																		// decreasing
																		// value
			}
		});

		// Convert sorted map back to a Map
		Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
		for (Iterator<Map.Entry<String, Integer>> it = list.iterator(); it.hasNext();) {
			Map.Entry<String, Integer> entry = it.next();
			sortedMap.put(entry.getKey(), entry.getValue());
		}
		return sortedMap;
	}

}
