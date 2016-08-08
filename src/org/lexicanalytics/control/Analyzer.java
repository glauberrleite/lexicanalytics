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
 * Class responsible to deal with most of logics applied to the productions
 * analysis. It uses singleton to keep a single instance. The Analyzer
 * communicates with persistence too by instantiating a DAO of productions and
 * having a instance of General Analysis Measurements for statistical purposes.
 * The user should add a production to the productions DAO then call
 * analyzeAllProduction method. Another possibility is to call the analyze
 * method giving it a production instance as parameter. The Analyzer will
 * compute metrics like number of words, words occurrences, lines and so on, and
 * attach it to the production instance.
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

	/**
	 * Passes through all productions analyzing each one's metrics, then
	 * calculates general measures like the sum of all words, and the mean of
	 * words in the sample.
	 */
	public void analyzeAllProductions() {

		ProcessingController processingCtrl = (ProcessingController) Main.getProcessingController();

		// Analyzing each production
		for (int i = 0; i < productions.size(); i++) {

			processingCtrl.setStatus("Analyzing production " + (i + 1) + " of " + productions.size());

			analyze(productions.getByIndex(i));

			// Increment progress indicator
			processingCtrl.addProgress(65 / productions.size());
		}

		processingCtrl.setStatus(productions.size() + " production(s) analyzed, building general analysis");

		generalMeasurements = new GeneralAnalysisMeasurements();

		List<Float> linesList = new ArrayList<Float>();
		List<Float> wordsList = new ArrayList<Float>();
		List<Float> ttrList = new ArrayList<Float>();
		List<Float> typesList = new ArrayList<Float>();

		for (Production production : productions.listAll()) {
			linesList.add((float) production.getNumberOfLines());
			wordsList.add((float) production.getNumberOfWords());
			typesList.add((float) production.getNumberOfTypes());
			ttrList.add(production.getTtr());

			// Total metrics
			generalMeasurements.totalLines += production.getNumberOfLines();
			generalMeasurements.totalWords += production.getNumberOfWords();
			generalMeasurements.totalTypes += production.getNumberOfTypes();
			generalMeasurements.totalTTR += production.getTtr();

			// Mean metrics
			generalMeasurements.meanLines += ((float) production.getNumberOfLines() / productions.size());
			generalMeasurements.meanWords += ((float) production.getNumberOfWords() / productions.size());
			generalMeasurements.meanTypes += ((float) production.getNumberOfTypes() / productions.size());
			generalMeasurements.meanTTR += ((float) production.getTtr() / productions.size());

			// Occurrences Metrics
			mergeMap(generalMeasurements.occurrences, production.getOccurrences());

			// Increment progress indicator
			processingCtrl.addProgress(20 / productions.size());
		}

		// Increment progress indicator
		processingCtrl.addProgress(5);

		// Sorting general occurrences map
		generalMeasurements.occurrences = sortByComparator(generalMeasurements.occurrences);

		// Calculating standard deviation

		// First the sum deviations
		for (Production production : productions.listAll()) {
			generalMeasurements.sdWords += Math.pow(production.getNumberOfWords() - generalMeasurements.meanWords, 2);
			generalMeasurements.sdTypes += Math.pow(production.getNumberOfTypes() - generalMeasurements.meanTypes, 2);
			generalMeasurements.sdLines += Math.pow(production.getNumberOfLines() - generalMeasurements.meanLines, 2);
			generalMeasurements.sdTTR += Math.pow(production.getTtr() - generalMeasurements.meanTTR, 2);
		}

		// Then the standard deviation
		generalMeasurements.sdWords = (float) Math.sqrt((generalMeasurements.sdWords) / productions.size());
		generalMeasurements.sdTypes = (float) Math.sqrt((generalMeasurements.sdTypes) / productions.size());
		generalMeasurements.sdLines = (float) Math.sqrt((generalMeasurements.sdLines) / productions.size());
		generalMeasurements.sdTTR = (float) Math.sqrt((generalMeasurements.sdTTR) / productions.size());

		// Finding the medians
		generalMeasurements.medianWords = calculateMedian(wordsList);
		generalMeasurements.medianTypes = calculateMedian(typesList);
		generalMeasurements.medianLines = calculateMedian(linesList);
		generalMeasurements.medianTTR = calculateMedian(ttrList);

		// Calculating mode
		generalMeasurements.modeWords = calculateMode(wordsList);
		generalMeasurements.modeTypes = calculateMode(typesList);
		generalMeasurements.modeLines = calculateMode(linesList);
		generalMeasurements.modeTTR = calculateMode(ttrList);

	}

	/**
	 * Computes some statistical measures like number of words, words
	 * occurrences for the given production and save those changes in the same
	 * instance of Production.
	 * 
	 * @param production
	 *            Production to analyze
	 */
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
	 * should stay on the first positions of the Map. This method is adapted
	 * from http://www.mkyong.com/java/how-to-sort-a-map-in-java/.
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

	private float calculateMedian(List<Float> list) {
		Collections.sort(list);

		if ((list.size() % 2) == 0) {
			return (list.get(list.size() / 2) + list.get((list.size() / 2) - 1)) / 2;
		} else {
			return list.get(list.size() / 2);
		}

	}

	/**
	 * Calculates the mode in the sample
	 * 
	 * @param list
	 *            A list of float values
	 * @return The most frequent value in the list, know as mode in statistics
	 */
	private float calculateMode(List<Float> list) {

		// Finding the number of occurrences of each item
		Map<String, Integer> map = new LinkedHashMap<String, Integer>();
		for (int i = 0; i < list.size(); i++) {
			String key = String.valueOf(list.get(i));

			int value = 1;
			if (map.containsKey(key)) {
				value = map.get(key) + 1;
			}

			map.put(key, value);
		}

		map = sortByComparator(map);

		// Getting only the first value of the sorted map;
		String result = map.entrySet().iterator().next().getKey();

		return Float.parseFloat(result);

	}

	/**
	 * Merges two maps, adding the values of a given key of the second map to
	 * the same key in the first parameter map.
	 * 
	 * @param sourceMap
	 *            The map where keys will be compared and values added, so it
	 *            will be merged with the other map.
	 * @param mergeMap
	 *            The map with keys and values that should be added to the
	 *            sourceMap
	 */
	private void mergeMap(Map<String, Integer> sourceMap, Map<String, Integer> mergeMap) {
		for (Map.Entry<String, Integer> entry : mergeMap.entrySet()) {

			int newValue = entry.getValue();

			String word = entry.getKey().toLowerCase(); // to make a standard

			if (sourceMap.containsKey(word)) {
				newValue = sourceMap.get(word) + entry.getValue();
			}

			sourceMap.put(word, newValue);
		}
	}

}
