package org.lexicanalytics.control.controllers;


import javafx.fxml.FXML;
import javafx.scene.control.Button;

import org.lexicanalytics.model.BaseController;

/**
 * 
 * @author glauberrleite
 *
 */

public class ResultsReportController extends BaseController {

	@FXML
	private Button generateButton;

	@FXML
	public void generate() {
	/*	String text = Analyser.getInstance().getInputText();

		if ((text != null) && (text.equals("") == false)) {
			Parent report = null;

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getClassLoader().getResource(
					"org/lexicanalytics/view/Report.fxml"));

			try {
				report = loader.load();
			} catch (IOException e) {
				e.printStackTrace();
			}

			// Get controller and fill the text
			ReportController controller = loader.getController();

			controller.setReportText(getReportText());

			// Create and show scene
			Scene reportScene = new Scene(report, 600, 400);

			Main.setScene(reportScene);
		}*/
	}

	private String getReportText() {
		String report = newSection("TEXT");

		/*report += Analyser.getInstance().getInputText();

		report += "\n\n";

		report += newSection("GENERAL");

		report += "Number of Lines = "
				+ Analyser.getInstance().getNumberOfLines();

		report += "\n";

		report += "Number of Words = "
				+ Analyser.getInstance().getNumberOfWords();

		report += "\n\n";

		report += newSection("TTR");

		report += "Types = " + Analyser.getInstance().getNumberOfTypes();

		report += "\n";

		report += "Tokens = " + Analyser.getInstance().getNumberOfTokens();

		report += "\n";

		report += "Type-Token Ratio = "
				+ String.format("%.2f", Analyser.getInstance().getTTR()) + "%";

		report += "\n\n";

		report += newSection("OCCURRENCES");

		for (Map.Entry<String, Integer> entry : Analyser.getInstance()
				.getOccurrences().entrySet()) {
			report += entry.getKey() + " - " + entry.getValue() + " times";
			report += "\n";
		}
*/
		return report;
	}

	private String newSection(String title) {
		String result = "########################\n";
		result += title + "\n";
		result += "########################\n\n";
		return result;
	}

	public void enableGenerateButton() {
		generateButton.setDisable(false);
	}

	public void disableGenerateButton() {
		generateButton.setDisable(true);
	}
}
