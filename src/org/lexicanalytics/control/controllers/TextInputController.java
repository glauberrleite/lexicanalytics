package org.lexicanalytics.control.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

import org.lexicanalytics.control.Analyser;
import org.lexicanalytics.model.BaseController;

/**
 * 
 * @author glauberrleite
 *
 */

public class TextInputController extends BaseController {
	
	@FXML
	private TextArea text;
	
	@FXML
	public void analyse(){
		Analyser.getInstance().analyse(text.getText());
	}
}
