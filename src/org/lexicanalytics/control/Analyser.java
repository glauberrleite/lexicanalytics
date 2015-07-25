package org.lexicanalytics.control;

import java.util.HashMap;
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
			numberOfLines = text.split("\n").length;

			// There are some words in brazilian grammar that contains -
			// character and still counts as a single word
			text = text.replace("-", "");

			// Remove spaces and general punctuation and put words on an array
			String words[] = text.trim().split("[\\P{L}\\s]+");

			numberOfWords = words.length;

			numberOfTokens = numberOfWords; // for now, they are equal

			occurrences = new HashMap<String, Integer>();

			for (int i = 0; i < numberOfWords; i++) {

				int newValue = 0;

				String word = words[i].toLowerCase(); // to make a standard

				if (occurrences.containsKey(word)) {
					newValue = occurrences.get(word) + 1;
				}

				occurrences.put(word, newValue);

			}

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

	
}
