package org.lexicanalytics.control.controllers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.lexicanalytics.control.Analyzer;
import org.lexicanalytics.model.BaseController;
import org.lexicanalytics.model.GeneralAnalysisMeasurements;
import org.lexicanalytics.model.Production;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * 
 * @author glauberrleite
 *
 */
public class ResultsReportsController extends BaseController implements Initializable {

	private final String FORMAT = "%.2f";

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

	@FXML
	private TextArea report;

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
				highestTTR.setText(production.toString() + " - " + String.format(FORMAT, production.getTtr()) + "%");
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
				lowestTTR.setText(production.toString() + " - " + String.format(FORMAT, production.getTtr()) + "%");
			}

		}

		// Show general report
		String generalReport = "Lexicanalytics Report\n\n";

		generalReport += newSection("PRODUCTIONS");

		for (Production production : Analyzer.getInstance().productions.listAll()) {
			generalReport += "----\n";
			generalReport += production.toString() + "\n";
			generalReport += "----\n\n";
			generalReport += production.getText() + "\n\n";

			generalReport += "Words: " + production.getNumberOfWords() + "\n";
			generalReport += "Types: " + production.getNumberOfTypes() + "\n";
			generalReport += "Lines: " + production.getNumberOfLines() + "\n";
			generalReport += "TTR: " + String.format(FORMAT, production.getTtr()) + " %\n";
			
			generalReport += "\n";

		}

		generalReport += newSection("GENERAL STATISTICS");

		GeneralAnalysisMeasurements measures = Analyzer.getInstance().generalMeasurements;

		generalReport += "Words\n";
		generalReport += "Total: " + String.format(FORMAT, measures.totalWords) + "\n";
		generalReport += "Mean: " + String.format(FORMAT, measures.meanWords) + "\n";
		generalReport += "Median: " + String.format(FORMAT, measures.medianWords) + "\n";
		generalReport += "Mode: " + String.format(FORMAT, measures.modeWords) + "\n";
		generalReport += "SD: " + String.format(FORMAT, measures.sdWords) + "\n";
		
		generalReport += "\n";
		
		generalReport += "Types\n";
		generalReport += "Total: " + String.format(FORMAT, measures.totalTypes) + "\n";
		generalReport += "Mean: " + String.format(FORMAT, measures.meanTypes) + "\n";
		generalReport += "Median: " + String.format(FORMAT, measures.medianTypes) + "\n";
		generalReport += "Mode: " + String.format(FORMAT, measures.modeTypes) + "\n";
		generalReport += "SD: " + String.format(FORMAT, measures.sdTypes) + "\n";

		generalReport += "\n";

		generalReport += "Lines\n";
		generalReport += "Total: " + String.format(FORMAT, measures.totalLines) + "\n";
		generalReport += "Mean: " + String.format(FORMAT, measures.meanLines) + "\n";
		generalReport += "Median: " + String.format(FORMAT, measures.medianLines) + "\n";
		generalReport += "Mode: " + String.format(FORMAT, measures.modeLines) + "\n";
		generalReport += "SD: " + String.format(FORMAT, measures.sdLines) + "\n";

		generalReport += "\n";

		generalReport += "TTR\n";
		generalReport += "Total: " + String.format(FORMAT, measures.totalTTR) + "\n";
		generalReport += "Mean: " + String.format(FORMAT, measures.meanTTR) + " %\n";
		generalReport += "Median: " + String.format(FORMAT, measures.medianTTR) + " %\n";
		generalReport += "Mode: " + String.format(FORMAT, measures.modeTTR) + " %\n";
		generalReport += "SD: " + String.format(FORMAT, measures.sdTTR) + " %\n";

		generalReport += "\n\n";

		generalReport += newSection("Productions Ranking");

		generalReport += "Words\n";
		generalReport += "Highest: " + highestWords.getText() + "\n";
		generalReport += "Lowest: " + lowestWords.getText() + "\n";

		generalReport += "\n";

		generalReport += "Types\n";
		generalReport += "Highest: " + highestTypes.getText() + "\n";
		generalReport += "Lowest: " + lowestTypes.getText() + "\n";

		generalReport += "\n";

		generalReport += "Lines\n";
		generalReport += "Highest: " + highestLines.getText() + "\n";
		generalReport += "Lowest: " + lowestLines.getText() + "\n";

		generalReport += "\n";

		generalReport += "TTR\n";
		generalReport += "Highest: " + highestTTR.getText() + "\n";
		generalReport += "Lowest: " + lowestTTR.getText() + "\n";

		generalReport += "\n";

		report.setText(generalReport);

	}

	@FXML
	private void saveReport() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save Report");
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text", "*.txt"),
				new FileChooser.ExtensionFilter("All", "*.*"));
		File file = fileChooser.showSaveDialog(new Stage());

		if (file != null) {
			try {
				FileWriter out = new FileWriter(file, true);

				// New lines can be different throughout platforms
				String[] lines = report.getText().split("\n");

				for (int i = 0; i < lines.length; i++) {
					out.write(lines[i]);
					out.write(System.getProperty("line.separator"));
				}
				out.close();

			} catch (IOException e) {
				e.printStackTrace();
			}

			// Alert in JavaFX 8u40
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Success");
			alert.setHeaderText(null);
			alert.setContentText("Report saved on " + file.getAbsolutePath());

			alert.showAndWait();
		}
	}

	/**
	 * A method to automate the process of building a new section in the report
	 * text.
	 * 
	 * @param title
	 *            Title of section
	 * @return A string that represents a new section header
	 */
	private String newSection(String title) {
		String result = "########################\n";
		result += title + "\n";
		result += "########################\n\n";
		return result;
	}

}
