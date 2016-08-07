package org.lexicanalytics.control.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import org.lexicanalytics.control.Analyzer;
import org.lexicanalytics.model.BaseController;
import org.lexicanalytics.model.Production;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class ResultsReportsController extends BaseController implements Initializable {

	@FXML
	private Label highestWords;

	@FXML
	private Label highestLines;
	
	@FXML
	private Label highestTypes;

	@FXML
	private Label highestTTR;

	@FXML
	private Label lowestWords;

	@FXML
	private Label lowestLines;
	
	@FXML
	private Label lowestTypes;

	@FXML
	private Label lowestTTR;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		int topWords = 0;
		int topLines = 0;
		int topTypes = 0;
		float topTTR = 0;
		int lowWords = 0;
		int lowLines = 0;
		int lowTypes = 0;

		// The highest TTR possible is 100
		float lowTTR = 100;

		// Moving through the list of productions to find the highest and lowest
		// measurements
		for (Production production : Analyzer.getInstance().productions.listAll()) {

			if (production.getNumberOfWords() > topWords) {
				topWords = production.getNumberOfWords();
				highestWords.setText(production.toString() + " - " + production.getNumberOfWords());
			}
			if (production.getNumberOfLines() > topLines) {
				topLines = production.getNumberOfLines();
				highestLines.setText(production.toString() + " - " + production.getNumberOfLines());
			}
			if (production.getNumberOfTypes() > topTypes) {
				topTypes = production.getNumberOfTypes();
				highestTypes.setText(production.toString() + " - " + production.getNumberOfTypes());
			}
			if (production.getTtr() > topTTR) {
				topTTR = production.getTtr();
				highestTTR.setText(production.toString() + " - " + production.getTtr() + " %");
			}

			// As words and lines are measurements that, in theory, can reach
			// infinity, we'll assume that the first production is the
			// production with the lowest measures.

			if ((production.getNumberOfWords() < lowWords) || (lowWords == 0)) {
				lowWords = production.getNumberOfWords();
				lowestWords.setText(production.toString() + " - " + production.getNumberOfWords());
			}
			if ((production.getNumberOfLines() < lowLines) || (lowLines == 0)) {
				lowLines = production.getNumberOfLines();
				lowestLines.setText(production.toString() + " - " + production.getNumberOfLines());
			}
			if ((production.getNumberOfTypes() < lowTypes) || (lowTypes == 0)) {
				lowTypes = production.getNumberOfTypes();
				lowestTypes.setText(production.toString() + " - " + production.getNumberOfTypes());
			}
			if (production.getTtr() < lowTTR) {
				lowTTR = production.getTtr();
				lowestTTR.setText(production.toString() + " - " + production.getTtr() + " %");
			}

		}
	}

}
