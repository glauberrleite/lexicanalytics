package org.lexicanalytics.control.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import org.lexicanalytics.model.BaseController;

/**
 * 
 * @author glauberrleite
 *
 */

public class ResultsGeneralController extends BaseController{

	@FXML
	private Label lines;
	
	@FXML
	private Label words;
	
	public void setLines(int lines){
		this.lines.setText(String.valueOf(lines));
	}
	
	public void setWords(int words){
		this.words.setText(String.valueOf(words));
	}
	
	public void cleanFrame(){
		lines.setText("none");
		words.setText("none");
	}
	
}
