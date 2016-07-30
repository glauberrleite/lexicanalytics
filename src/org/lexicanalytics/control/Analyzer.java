package org.lexicanalytics.control;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.lang.Math;

import org.lexicanalytics.application.Main;
import org.lexicanalytics.control.controllers.ProcessingController;
import org.lexicanalytics.model.GeneralAnalysisMeasurements;
import org.lexicanalytics.model.Production;
import org.lexicanalytics.persistence.ProductionDAO;
import org.lexicanalytics.persistence.ProductionArray;

/**
 * Uses Singleton design pattern, so do not create new instances, instead use
 * Analyzer.getInstance(); This class offers results for the Lexicanalytics
 * application.
 * 
 * @author glauberrleite
 *
 */

public class Analyzer {

	private static Analyzer instance;

	public ProductionDAO productions;
	public GeneralAnalysisMeasurements generalMeasurements;

	private Analyzer() {
		productions = new ProductionArray();
	}

	public static Analyzer getInstance() {
		if (instance == null)
			instance = new Analyzer();
		return instance;
	}

	public void analyzeAllProductions() {

		ProcessingController processingCtrl = (ProcessingController) Main.getProcessingController();

		// Analyzing each production
		for (int i = 0; i < productions.size(); i++) {

			processingCtrl.setStatus("Analyzing production " + (i + 1) + " of " + productions.size());

			analyze(productions.getByIndex(i));

			// Increment progress indicator
			processingCtrl.addProgress(80 / productions.size());
		}

		processingCtrl.setStatus(productions.size() + " production(s) analyzed, building general analysis");

		generalMeasurements = new GeneralAnalysisMeasurements();

		List<Float> linesList = new ArrayList<Float>();
		List<Float> wordsList = new ArrayList<Float>();
		List<Float> ttrList = new ArrayList<Float>();

		for (Production production : productions.listAll()) {
			linesList.add((float) production.getNumberOfLines());
			wordsList.add((float) production.getNumberOfWords());
			ttrList.add(production.getTtr());

			generalMeasurements.totalLines += production.getNumberOfLines();
			generalMeasurements.totalWords += production.getNumberOfWords();
			generalMeasurements.totalTTR += production.getTtr();

			generalMeasurements.meanLines += ((float) production.getNumberOfLines() / productions.size());
			generalMeasurements.meanWords += ((float) production.getNumberOfWords() / productions.size());
			generalMeasurements.meanTTR += ((float) production.getTtr() / productions.size());
		}

		// Calculating standard deviation

		// First the sum deviations
		for (Production production : productions.listAll()) {
			generalMeasurements.sdWords += Math.pow(production.getNumberOfWords() - generalMeasurements.meanWords, 2);
			generalMeasurements.sdLines += Math.pow(production.getNumberOfLines() - generalMeasurements.meanLines, 2);
			generalMeasurements.sdTTR += Math.pow(production.getTtr() - generalMeasurements.meanTTR, 2);
		}

		// Then the standard deviation
		generalMeasurements.sdWords = (float) Math.sqrt((generalMeasurements.sdWords) / productions.size());
		generalMeasurements.sdLines = (float) Math.sqrt((generalMeasurements.sdLines) / productions.size());
		generalMeasurements.sdTTR = (float) Math.sqrt((generalMeasurements.sdTTR) / productions.size());

		// Sorting lists
		Collections.sort(wordsList);
		Collections.sort(linesList);
		Collections.sort(ttrList);

		// Finding the median
		if ((wordsList.size() % 2) == 0) {
			generalMeasurements.medianWords = (wordsList.get(wordsList.size() / 2)
					+ wordsList.get((wordsList.size() / 2) + 1)) / 2;
		} else {
			generalMeasurements.medianWords = wordsList.get(wordsList.size() / 2);
		}
		
		if ((linesList.size() % 2) == 0) {
			generalMeasurements.medianLines = (linesList.get(linesList.size() / 2)
					+ linesList.get((linesList.size() / 2) + 1)) / 2;
		} else {
			generalMeasurements.medianLines = linesList.get(linesList.size() / 2);
		}
		
		if ((ttrList.size() % 2) == 0) {
			generalMeasurements.medianTTR = (ttrList.get(ttrList.size() / 2)
					+ ttrList.get((ttrList.size() / 2) + 1)) / 2;
		} else {
			generalMeasurements.medianTTR = ttrList.get(ttrList.size() / 2);
		}

	}

	public void analyze(Production production) {

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
