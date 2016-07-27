package org.lexicanalytics.application;

import org.lexicanalytics.model.BaseController;
import org.lexicanalytics.model.BaseFrame;
import org.lexicanalytics.view.HelpFrame;
import org.lexicanalytics.view.ProcessingFrame;
import org.lexicanalytics.view.ResultsFrame;
import org.lexicanalytics.view.TextInputFrame;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;

/**
 * Lexicanalytics offers a set of functionalities to help linguistics researchers
 * with lexical studies, when they are manipulating and retrieving information 
 * from text data. Copyright (C) 2015 Glauber Rodrigues Leite
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 * 
 */

/**
 * 
 * @author glauberrleite
 *
 */

public class Main extends Application {

	private static BorderPane rootLayout;
	private static BaseFrame input, help, processing, results;

	@Override
	public void start(Stage primaryStage) {
		try {
			// Setting the basic app properties
			primaryStage.setTitle("Lexicanalytics");
			primaryStage.setFullScreen(false);
			primaryStage.setResizable(false);
			primaryStage.getIcons().add(
					new Image(getClass().getClassLoader().getResourceAsStream(
							"org/lexicanalytics/resources/logo.png")));

			// Loads the Layout defined by the respective .fxml file
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getClassLoader().getResource(
					"org/lexicanalytics/view/RootLayout.fxml"));
			rootLayout = (BorderPane) loader.load();
			Scene scene = new Scene(rootLayout);

			// If a controller is needed
			// BaseController rootController = loader.getController();

			// Shows a pane inside the root pane
			showTextInput();

			// Starting GUI
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	public static void showHelp() {
		if (help == null)
			help = new HelpFrame();
		rootLayout.setCenter(help.getAnchorPane());
	}

	public static void showTextInput() {
		if (input == null)
			input = new TextInputFrame();
		rootLayout.setCenter(input.getAnchorPane());
	}

	public static void showProcessing(){
		if (processing == null)
			processing = new ProcessingFrame();
		rootLayout.setCenter(processing.getAnchorPane());
	}
	
	public static void showResults(){
		if (results == null)
			results = new ResultsFrame();
		rootLayout.setCenter(results.getAnchorPane());
	}
	
	public static BaseController getProcessingController(){
		return processing.getController();
	}
	
	public static BaseController getResultsController(){
		return results.getController();
	}
}
