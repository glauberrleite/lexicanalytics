package org.lexicanalytics.control.controllers;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;

import org.lexicanalytics.control.Analyzer;
import org.lexicanalytics.model.BaseController;
import org.lexicanalytics.model.Production;
import org.lexicanalytics.view.ResultsGraphsFrame;

/**
 * 
 * @author glauberrleite
 *
 */

public class ResultsController extends BaseController implements Initializable {

	private final String FORMAT = "%.2f";
	
	List<Production> productions;

	@FXML
	private ComboBox<Production> productionComboBox;
	
	@FXML
	private ListView<String> productionOccurrences;
	
	@FXML
	private TextField productionWordSearch;
	
	@FXML
	private TextField generalWordSearch;
	
	@FXML
	private Tab graphsTab;
	
	@FXML
	private Tab reportsTab;

	// Labels
	
	// General Labels
	
	@FXML
	private Label totalLines;
	
	@FXML
	private Label totalWords;
	
	@FXML
	private Label totalTTR;
	
	@FXML
	private Label meanLines;
	
	@FXML
	private Label meanWords;
	
	@FXML
	private Label meanTTR;
	
	@FXML
	private Label medianLines;
	
	@FXML
	private Label medianWords;
	
	@FXML
	private Label medianTTR;
	
	@FXML
	private Label modeLines;
	
	@FXML
	private Label modeWords;
	
	@FXML
	private Label modeTTR;
	
	@FXML
	private Label sdLines;
	
	@FXML
	private Label sdWords;
	
	@FXML
	private Label sdTTR;
	
	@FXML
	private Label generalWordSearchResult;
	
	// Production Labels
	
	@FXML
	private Label productionLines;

	@FXML
	private Label productionWords;

	@FXML
	private Label productionTypes;

	@FXML
	private Label productionTokens;

	@FXML
	private Label productionTTR;

	@FXML
	private Label productionWordSearchResult;

	// End Labels
		
	private class ComboBoxListener implements ChangeListener<Production> {

		@Override
		public void changed(ObservableValue<? extends Production> observable, Production oldValue,
				Production newValue) {

			productionLines.setText(String.valueOf(newValue.getNumberOfLines()));
			productionWords.setText(String.valueOf(newValue.getNumberOfWords()));
			productionTypes.setText(String.valueOf(newValue.getNumberOfTypes()));
			productionTokens.setText(String.valueOf(newValue.getNumberOfTokens()));
			productionTTR.setText(String.format(FORMAT, newValue.getTtr()) + "%");

			ObservableList<String> occurrences = FXCollections.observableArrayList();

			for (Map.Entry<String, Integer> entry : newValue.getOccurrences().entrySet()) {
				occurrences.add(entry.getKey() + " - " + entry.getValue() + " times");
			}

			productionOccurrences.setItems(occurrences);

			productionWordSearchResult.setText("No search item");

		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		productions = Analyzer.getInstance().productions.listAll();

		// General Tab
		
		
		totalLines.setText(String.format(FORMAT, Analyzer.getInstance().generalMeasurements.totalLines));
		totalWords.setText(String.format(FORMAT, Analyzer.getInstance().generalMeasurements.totalWords));
		totalTTR.setText(String.format(FORMAT, Analyzer.getInstance().generalMeasurements.totalTTR));
		
		meanLines.setText(String.format(FORMAT, Analyzer.getInstance().generalMeasurements.meanLines));
		meanWords.setText(String.format(FORMAT, Analyzer.getInstance().generalMeasurements.meanWords));
		meanTTR.setText(String.format(FORMAT, Analyzer.getInstance().generalMeasurements.meanTTR));
		
		medianLines.setText(String.format(FORMAT, Analyzer.getInstance().generalMeasurements.medianLines));
		medianWords.setText(String.format(FORMAT, Analyzer.getInstance().generalMeasurements.medianWords));
		medianTTR.setText(String.format(FORMAT, Analyzer.getInstance().generalMeasurements.medianTTR));
		
		modeLines.setText(String.format(FORMAT, Analyzer.getInstance().generalMeasurements.modeLines));
		modeWords.setText(String.format(FORMAT, Analyzer.getInstance().generalMeasurements.modeWords));
		modeTTR.setText(String.format(FORMAT, Analyzer.getInstance().generalMeasurements.modeTTR));
		
		sdLines.setText(String.format(FORMAT, Analyzer.getInstance().generalMeasurements.sdLines));
		sdWords.setText(String.format(FORMAT, Analyzer.getInstance().generalMeasurements.sdWords));
		sdTTR.setText(String.format(FORMAT, Analyzer.getInstance().generalMeasurements.sdTTR));
		
		
		
		// Production Tab
		
		// Fill ComboBox
		ObservableList<Production> options = FXCollections.observableArrayList(productions);

		productionComboBox.setItems(options);

		// Listener to the ComboBox
		productionComboBox.valueProperty().addListener(new ComboBoxListener());
		productionComboBox.setValue(productions.get(0));
		
		
		
		// Graphs Tab
		graphsTab.setContent((new ResultsGraphsFrame()).getAnchorPane());

	}

	@FXML
	private void productionWordSearch() {
		String word = productionWordSearch.getText().toLowerCase();

		Map<String, Integer> occurrences = productionComboBox.getValue().getOccurrences();

		if ((occurrences != null) && (occurrences.containsKey(word))) {

			int numberOfOccurrences = occurrences.get(word);
			productionWordSearchResult.setText(word + " - " + numberOfOccurrences + " times");

		} else {

			productionWordSearchResult.setText("No results for " + word);

		}
	}
	
	@FXML
	private void generalWordSearch() {
	}

}
