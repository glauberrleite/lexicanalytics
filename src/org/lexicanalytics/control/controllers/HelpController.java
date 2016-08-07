package org.lexicanalytics.control.controllers;

import javafx.fxml.FXML;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import org.lexicanalytics.application.Main;
import org.lexicanalytics.model.BaseController;

/**
 * 
 * @author glauberrleite
 *
 */
public class HelpController extends BaseController {
	@FXML
	private void back() {
		Main.showLastPane();
	}

	@FXML
	private void visitWebsite() {
		visitPage("http://projects.glauberrleite.com/lexicanalytics");
	}

	@FXML
	private void visitGithub() {
		visitPage("https://github.com/glauberrleite/lexicanalytics");
	}

	@FXML
	private void visitHome() {
		visitPage("http://glauberrleite.com");
	}
	
	private void visitPage(String link){
		WebView browser = new WebView();
		WebEngine webEngine = browser.getEngine();
		webEngine.load(link);
		
	}
}
