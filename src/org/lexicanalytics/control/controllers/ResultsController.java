package org.lexicanalytics.control.controllers;

import java.net.URL;
import java.util.List;
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
	private ListView<String> productionOccurrences;

	/*
	 * @FXML public SplitPane splitPane;
	 * 
	 * private BaseFrame general, ttr, occurrences, report;
	 */
	private class ComboBoxListener implements ChangeListener<Production> {

		@Override
		public void changed(ObservableValue<? extends Production> observable, Production oldValue,
				Production newValue) {

			productionLines.setText(String.valueOf(newValue.getNumberOfLines()));
			productionWords.setText(String.valueOf(newValue.getNumberOfWords()));
			productionTypes.setText(String.valueOf(newValue.getNumberOfTypes()));
			productionTokens.setText(String.valueOf(newValue.getNumberOfTokens()));
			productionTTR.setText(String.valueOf(newValue.getTtr()) + "%");

			ObservableList<String> occurrences = FXCollections.observableArrayList();
			productionOccurrences.setItems(occurrences);

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
	private void productionWordSearch(){
		
	}

}
