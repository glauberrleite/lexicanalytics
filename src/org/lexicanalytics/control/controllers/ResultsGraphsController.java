package org.lexicanalytics.control.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import org.lexicanalytics.control.Analyzer;
import org.lexicanalytics.model.BaseController;
import org.lexicanalytics.model.Production;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.Chart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ResultsGraphsController extends BaseController implements Initializable {

	private List<Production> productions;

	// Productions Chart variables
	@FXML
	private LineChart<String, Number> productionChart;

	XYChart.Series<String, Number> wordsSeries;
	XYChart.Series<String, Number> typesSeries;
	XYChart.Series<String, Number> linesSeries;
	XYChart.Series<String, Number> ttrSeries;

	@FXML
	private ToggleButton wordsButton;

	@FXML
	private ToggleButton typesButton;

	@FXML
	private ToggleButton linesButton;

	@FXML
	private ToggleButton ttrButton;

	// Words Pie Chart variables
	@FXML
	private PieChart wordsGraph;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		productions = Analyzer.getInstance().productions.listAll();

		// Productions line chart
		toggleWords();
		productionChart.setCursor(Cursor.CROSSHAIR);

		// Words pie chart

		// Increment to get first 5 itens
		int i = 0;
		int totalTopWords = 0;

		for (Map.Entry<String, Integer> entry : Analyzer.getInstance().generalMeasurements.occurrences.entrySet()) {

			// Getting the frequency of the given word
			float value = (float) ((entry.getValue() * 100) / Analyzer.getInstance().generalMeasurements.totalWords);

			PieChart.Data data = new PieChart.Data(entry.getKey(), value);
			wordsGraph.getData().add(data);

			totalTopWords += entry.getValue();

			// Get first 5 values
			if (i >= 5) {
				break;
			} else {
				i++;
			}
		}

		// Getting frequency of non top words
		float value = (float) (((Analyzer.getInstance().generalMeasurements.totalWords - totalTopWords) * 100)
				/ Analyzer.getInstance().generalMeasurements.totalWords);

		if (value > 0) {
			PieChart.Data otherWords = new PieChart.Data("Other words", value);
			wordsGraph.getData().add(otherWords);
		}

	}

	@FXML
	private void toggleWords() {
		if (wordsButton.isSelected()) {

			wordsSeries = new XYChart.Series<String, Number>();
			wordsSeries.setName("Words");

			for (Production production : productions) {
				wordsSeries.getData()
						.add(new XYChart.Data<String, Number>(production.toString(), production.getNumberOfWords()));
			}

			productionChart.getData().add(wordsSeries);

		} else {
			productionChart.getData().remove(wordsSeries);
		}

	}

	@FXML
	private void toggleTypes() {
		if (typesButton.isSelected()) {

			typesSeries = new XYChart.Series<String, Number>();
			typesSeries.setName("Types");

			for (Production production : productions) {
				typesSeries.getData()
						.add(new XYChart.Data<String, Number>(production.toString(), production.getNumberOfTypes()));
			}

			productionChart.getData().add(typesSeries);

		} else {
			productionChart.getData().remove(typesSeries);
		}
	}

	@FXML
	private void toggleLines() {
		if (linesButton.isSelected()) {

			linesSeries = new XYChart.Series<String, Number>();
			linesSeries.setName("Lines");

			for (Production production : productions) {
				linesSeries.getData()
						.add(new XYChart.Data<String, Number>(production.toString(), production.getNumberOfLines()));
			}

			productionChart.getData().add(linesSeries);

		} else {
			productionChart.getData().remove(linesSeries);
		}
	}

	@FXML
	private void toggleTTR() {
		if (ttrButton.isSelected()) {
			ttrSeries = new XYChart.Series<String, Number>();
			ttrSeries.setName("TTR");

			for (Production production : productions) {
				ttrSeries.getData().add(new XYChart.Data<String, Number>(production.toString(), production.getTtr()));
			}

			productionChart.getData().add(ttrSeries);

		} else {
			productionChart.getData().remove(ttrSeries);
		}
	}

	@FXML
	private void saveProductionAnalysis() {
		saveGraph(productionChart);
	}

	@FXML
	private void saveWordsAnalysis() {
		saveGraph(wordsGraph);
		
	}
	
	private void saveGraph(Chart chart) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save Graph");
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image File", "*.png"),
				new FileChooser.ExtensionFilter("All", "*.*"));
		File file = fileChooser.showSaveDialog(new Stage());

		if (file != null) {

			WritableImage image = chart.snapshot(new SnapshotParameters(), null);

			try {
				ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}

			// Alert in JavaFX 8u40
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Success");
			alert.setHeaderText(null);
			alert.setContentText("Graph saved on " + file.getAbsolutePath());

			alert.showAndWait();
		}
	}

}
