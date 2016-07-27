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
import javafx.scene.control.TextField;

import org.lexicanalytics.control.Analyser;
import org.lexicanalytics.model.BaseController;
import org.lexicanalytics.model.Production;

/**
 * 
 * @author glauberrleite
 *
 */

public class ResultsController extends BaseController implements Initializable {

	List<Production> productions;

	@FXML
	private ComboBox<Production> productionComboBox;

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
	private TextField productionWordSearch;

	@FXML
	private Label productionWordSearchResult;

	@FXML
	private ListView<String> productionOccurrences;

	private class ComboBoxListener implements ChangeListener<Production> {

		@Override
		public void changed(ObservableValue<? extends Production> observable, Production oldValue,
				Production newValue) {

			productionLines.setText(String.valueOf(newValue.getNumberOfLines()));
			productionWords.setText(String.valueOf(newValue.getNumberOfWords()));
			productionTypes.setText(String.valueOf(newValue.getNumberOfTypes()));
			productionTokens.setText(String.valueOf(newValue.getNumberOfTokens()));
			productionTTR.setText(String.format("%.2f", newValue.getTtr()) + "%");

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

		productions = Analyser.getInstance().productions.listAll();

		// Fill ComboBox
		ObservableList<Production> options = FXCollections.observableArrayList(productions);

		productionComboBox.setItems(options);

		// Listener to the ComboBox
		productionComboBox.valueProperty().addListener(new ComboBoxListener());
		productionComboBox.setValue(productions.get(0));

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

}
