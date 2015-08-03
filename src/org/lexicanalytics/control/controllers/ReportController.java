package org.lexicanalytics.control.controllers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.lexicanalytics.application.Main;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
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
				out.write(reportText.getText());
				out.close();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}
