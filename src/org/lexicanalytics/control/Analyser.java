package org.lexicanalytics.control;

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

	// Text results
	private int numberOfLines;
	private int numberOfWords;
	private int types;
	private int tokens;

	private Analyser() {
	}

	public static Analyser getInstance() {
		if (instance == null)
			instance = new Analyser();
		return instance;
	}

	public void analyse(String text) {
		if (text != null) {
			numberOfLines = text.split("\n").length;
			
			System.out.println("Number of Lines = " + numberOfLines);

			// There are some words in brazilian grammar that contains -
			// character and still counts as a single word
			text = text.replace("-", "");

			// Remove spaces and general punctuation and put words on an array
			/*
			 * String words[] = text.trim().split("[\\[|\\]|\\{\\}|\\(|\\)|" +
			 * // brackets
			 * "\\:|\\!|\\?|\\.|\\,|\\_|\\<|\\>|\\+|\\=|\\*|\\%|\\#|" + //
			 * general "\"|\'|\\/|\\|" + // slashes, quotes "\\s]" + // space
			 * "+"); // if those items happen in sequences
			 */

			String words[] = text.trim().split("[\\P{L}\\s]+");

			numberOfWords = words.length;

			System.out.println("Number of Words = " + numberOfWords);

			for (int i = 0; i < words.length; i++) {
				System.out.println(words[i]);
			}

		}
	}

	public int getNumberOfLines() {
		return numberOfLines;
	}

	public int getNumberOfWords() {
		return numberOfWords;
	}

	public int getTypes() {
		return types;
	}

	public int getTokens() {
		return tokens;
	}

}
