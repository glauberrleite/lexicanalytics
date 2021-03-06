package org.lexicanalytics.control.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;

import org.lexicanalytics.application.Main;
import org.lexicanalytics.control.Analyzer;
import org.lexicanalytics.model.BaseController;
import org.lexicanalytics.model.Production;

/**
 * 
 * @author glauberrleite
 *
 */

public class TextInputController extends BaseController {

	@FXML
	private TextArea text;

	@FXML
	public void next() {
		
		if ((text.getText() != null) && (text.getText().equals("") == false)) {
			
			Analyzer.getInstance().productions.insertProduction(new Production(text.getText()));
			
		}  else {
	
			showTextAlert();

		}
		
		clean();
		
	}

	@FXML
	public void analyse() {
		
		if ((text.getText() != null) && (text.getText().equals("") == false)) {
			
			Analyzer.getInstance().productions.insertProduction(new Production(text.getText()));
			
		} 
		
		if (Analyzer.getInstance().productions.size() > 0){
			
			Main.showProcessing();
			
			Analyzer.getInstance().analyzeAllProductions();
			
			Main.showResults();
			
		} else {
			
			showTextAlert();

		}
	}

	@FXML
	public void clean() {

		text.setText("");

	}
	
	/**
	 *  Alert in JavaFX available from jdk 8u40 onward
	 */
	public void showTextAlert(){
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Lexicanalytics - Analyser");
		alert.setHeaderText(null);
		alert.setContentText("No text input for analysis");

		alert.showAndWait();
	}
}
