package org.lexicanalytics.control.controllers;

import javafx.fxml.FXML;
import org.lexicanalytics.application.Main;
import org.lexicanalytics.model.BaseController;

/**
 * 
 * @author glauberrleite
 *
 */
public class HelpController extends BaseController{
	@FXML
	public void back(){
		Main.showTextInput();
	}
}
