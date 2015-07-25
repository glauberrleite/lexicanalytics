package org.lexicanalytics.control.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

import org.lexicanalytics.application.Main;
import org.lexicanalytics.control.Analyser;
import org.lexicanalytics.model.BaseController;
import org.lexicanalytics.model.ResultsType;

/**
 * 
 * @author glauberrleite
 *
 */

public class TextInputController extends BaseController {

	@FXML
	private TextArea text;

	@FXML
	public void analyse() {
		Analyser.getInstance().analyse(text.getText());

		ResultsController resultsController = (ResultsController) Main
				.getResultsController();

		// Fill General Results

		ResultsGeneralController generalController = (ResultsGeneralController) resultsController
				.getResultsTypeController(ResultsType.GENERAL);

		generalController.setLines(Analyser.getInstance().getNumberOfLines());
		generalController.setWords(Analyser.getInstance().getNumberOfWords());

		// Fill TTR results
		ResultsTTRController ttrController = (ResultsTTRController) resultsController
				.getResultsTypeController(ResultsType.TTR);

		ttrController.setTypes(Analyser.getInstance().getNumberOfTypes());
		ttrController.setTokens(Analyser.getInstance().getNumberOfTokens());
		ttrController.setTTR(Analyser.getInstance().getTTR());
	}
}
