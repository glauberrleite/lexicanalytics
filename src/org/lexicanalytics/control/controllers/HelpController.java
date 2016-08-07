package org.lexicanalytics.control.controllers;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

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

		VBox root = new VBox();
		root.getChildren().add(browser);
		
		Scene scene = new Scene(root, 800, 500);
		
		
		Stage stage = new Stage();
		stage.setTitle("Lexicanalytics Browser");
		stage.getIcons().add(
				new Image(getClass().getClassLoader().getResourceAsStream(
						"org/lexicanalytics/resources/logo.png")));
		stage.setScene(scene);
		stage.show();
		
		
		
		
	}
}
