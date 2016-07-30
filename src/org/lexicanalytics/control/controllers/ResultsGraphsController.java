package org.lexicanalytics.control.controllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.lexicanalytics.control.Analyzer;
import org.lexicanalytics.model.BaseController;
import org.lexicanalytics.model.Production;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ToggleButton;

public class ResultsGraphsController extends BaseController implements Initializable {

	private List<Production> productions;

	@FXML
	private LineChart<String, Number> productionChart;

	XYChart.Series<String, Number> wordsSeries;
	XYChart.Series<String, Number> linesSeries;
	XYChart.Series<String, Number> ttrSeries;

	@FXML
	private ToggleButton wordsButton;

	@FXML
	private ToggleButton linesButton;

	@FXML
	private ToggleButton ttrButton;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		productions = Analyzer.getInstance().productions.listAll();

		// Production Chart
		productionChart.setTitle("Production analysis");

		toggleWords();

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

}
