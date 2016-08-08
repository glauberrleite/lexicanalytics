package org.lexicanalytics.model;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * This class offers a structured way to save the general measures of the
 * productions sample used by the Analyzer.
 * 
 * @author glauberrleite
 *
 */
public class GeneralAnalysisMeasurements {

	public GeneralAnalysisMeasurements() {
		totalLines = 0;
		totalWords = 0;
		totalTTR = 0;
		totalTypes = 0;
		meanLines = 0;
		meanWords = 0;
		meanTTR = 0;
		meanTypes = 0;
		medianLines = 0;
		medianWords = 0;
		medianTTR = 0;
		medianTypes = 0;
		modeLines = 0;
		modeWords = 0;
		modeTTR = 0;
		modeTypes = 0;
		sdLines = 0;
		sdWords = 0;
		sdTTR = 0;
		sdTypes = 0;
		occurrences = new LinkedHashMap<String, Integer>();
	}

	public float totalLines;

	public float totalWords;

	public float totalTTR;
	
	public float totalTypes;

	public float meanLines;

	public float meanWords;

	public float meanTTR;

	public float meanTypes;
	
	public float medianLines;

	public float medianWords;

	public float medianTTR;

	public float medianTypes;
	
	public float modeLines;

	public float modeWords;

	public float modeTTR;

	public float modeTypes;
	
	public float sdLines;

	public float sdWords;

	public float sdTTR;
	
	public float sdTypes;

	public Map<String, Integer> occurrences;

}
