package org.lexicanalytics.control;

import javafx.scene.control.TextArea;

/**
 * Uses Singleton design pattern, so do not create new instances, instead use
 * Analyser.getInstance();
 * This class offers results for the Lexicanalytics application.
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

	public void analyse(TextArea text){
		numberOfLines = text.getText().split("\n").length;
		System.out.println(numberOfLines);
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
