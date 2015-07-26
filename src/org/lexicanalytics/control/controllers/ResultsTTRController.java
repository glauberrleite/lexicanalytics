package org.lexicanalytics.control.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import org.lexicanalytics.model.BaseController;

/**
 * 
 * @author glauberrleite
 *
 */

public class ResultsTTRController extends BaseController{
	
	@FXML
	private Label types;
	
	@FXML
	private Label tokens;
	
	@FXML
	private Label ttr;
	
	public void setTypes(int types){
		this.types.setText(String.valueOf(types));
	}
	
	public void setTokens(int tokens){
		this.tokens.setText(String.valueOf(tokens));
	}

	public void setTTR(float ttr){
		this.ttr.setText(String.format("%.2f", ttr) + "%");
	}
	
	public void cleanFrame(){
		types.setText("none");
		tokens.setText("none");
		ttr.setText("none");
	}
	
}
