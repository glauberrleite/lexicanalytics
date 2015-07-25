package org.lexicanalytics.control.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;

import org.lexicanalytics.model.BaseController;

/**
 * 
 * @author glauberrleite
 *
 */

public class ResultsController extends BaseController implements Initializable {

	@FXML
	public ComboBox<String> resultsComboBox;

	@FXML
	public AnchorPane pane;

	@FXML
	public void newSelection() {
		String choice = resultsComboBox.getValue();
		if (choice.equals("General")) {
			// implement general frame
		} else if (choice.equals("TTR")) {
			// implement TTR frame
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		ObservableList<String> options = FXCollections.observableArrayList(
				"General", "TTR");

		resultsComboBox.setItems(options);
		resultsComboBox.setValue("General");

	}
}
