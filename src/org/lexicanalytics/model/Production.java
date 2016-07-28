package org.lexicanalytics.model;

import java.util.HashMap;
import java.util.Map;

import org.lexicanalytics.control.Analyzer;

/**
 * A Production is the entity that represents the text sample and its lexical properties that will 
 * be processed by the Analyser.
 * @author glauberrleite
 *
 */
public class Production {
	
	private String text;
	private int numberOfLines;
	private int numberOfWords;
	private Map<String, Integer> occurrences;
	private int numberOfTypes;
	private int numberOfTokens;
	private float ttr;
	
	
	/**
	 * To get a instance, only the text sample is needed as the other attributes will be set by 
	 * the Analyser.
	 * @param text The text sample
	 */
	public Production(String text){
		this.text = text;
		
		this.occurrences = new HashMap<String, Integer>();
		this.numberOfLines = 0;
		this.numberOfWords = 0;
		this.numberOfTypes = 0;
		this.numberOfTokens = 0;
		this.ttr = 0;
	}


	public int getNumberOfLines() {
		return numberOfLines;
	}


	public void setNumberOfLines(int numberOfLines) {
		this.numberOfLines = numberOfLines;
	}


	public int getNumberOfWords() {
		return numberOfWords;
	}


	public void setNumberOfWords(int numberOfWords) {
		this.numberOfWords = numberOfWords;
	}


	public Map<String, Integer> getOccurrences() {
		return new HashMap<String, Integer>(occurrences);
	}


	public void setOccurrences(Map<String, Integer> occurrences) {
		this.occurrences = occurrences;
	}


	public int getNumberOfTypes() {
		return numberOfTypes;
	}


	public void setNumberOfTypes(int numberOfTypes) {
		this.numberOfTypes = numberOfTypes;
	}


	public int getNumberOfTokens() {
		return numberOfTokens;
	}


	public void setNumberOfTokens(int numberOfTokens) {
		this.numberOfTokens = numberOfTokens;
	}


	public float getTtr() {
		return ttr;
	}


	public void setTtr(float ttr) {
		this.ttr = ttr;
	}


	public String getText() {
		return text;
	}
	
	@Override
	public String toString(){
		
		int index = Analyzer.getInstance().productions.getIndex(this) + 1;
		String output = "P" + index;
				
		return output;
	}
	
}
