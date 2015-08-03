package org.lexicanalytics.control.controllers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.lexicanalytics.application.Main;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * 
 * @author glauberrleite
 *
 */

public class ReportController {

	@FXML
	private TextArea reportText;

	@FXML
	public void close() {
		Main.showMain();
	}

	public void setReportText(String text) {
		reportText.setText(text);
	}

	@FXML
	public void save() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save Report");
		fileChooser.getExtensionFilters().addAll(
				new FileChooser.ExtensionFilter("Text", "*.txt"),
				new FileChooser.ExtensionFilter("All", "*.*"));
		File file = fileChooser.showSaveDialog(new Stage());

		if (file != null) {
			try {
				FileWriter out = new FileWriter(file, true);

				// New lines can be different throughout platforms
				String[] lines = reportText.getText().split("\n");

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

}
