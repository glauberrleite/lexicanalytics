package org.lexicanalytics.application;
	
import org.lexicanalytics.model.BaseFrame;
import org.lexicanalytics.view.HelpFrame;
import org.lexicanalytics.view.ResultsFrame;
import org.lexicanalytics.view.TextInputFrame;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	
	private static BorderPane rootLayout;
	private static BaseFrame input, help, results;
	
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
			loader.setLocation(getClass().getClassLoader()
					.getResource("org/lexicanalytics/view/RootLayout.fxml"));
			rootLayout = (BorderPane) loader.load();
			Scene scene = new Scene(rootLayout);
			
			// If a controller is needed
			// BaseController rootController = loader.getController();

			// Shows a pane inside the root pane
			showTextInput();
			showResultsPane();

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
	
	public static void showTextInput(){
		if (input == null)
			input = new TextInputFrame();
		rootLayout.setCenter(input.getAnchorPane());
	}
	
	public static void showResultsPane(){
		if (results == null)
			results = new ResultsFrame();
		rootLayout.setBottom(results.getAnchorPane());
	}
}
