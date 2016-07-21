package org.lexicanalytics.control.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import org.lexicanalytics.control.Analyser;
import org.lexicanalytics.model.BaseController;

/**
 * 
 * @author glauberrleite
 *
 */

public class ResultsOccurrencesController extends BaseController implements Initializable{

	@FXML
	private ListView<String> topOccurrences;

	@FXML
	private TextField wordInput;

	@FXML
	private Label searchResult;

	private ObservableList<String> items;
	
	@FXML
	public void searchWord() {
		String word = wordInput.getText().toLowerCase();

/*		if ((Analyser.getInstance().getOccurrences() != null)
				&& (Analyser.getInstance().getOccurrences().containsKey(word))) {
			
			int numberOfOccurrences = Analyser.getInstance().getOccurrences()
					.get(wordInput.getText().toLowerCase());
			searchResult.setText(word + " - " + numberOfOccurrences + " times");
			
		} else {
			
			searchResult.setText("No results for " + word);
			
		}
*/
	}

	public void addItemToList(String word, int occurrencies) {
		items.add(word + " - " + occurrencies + " times");
	}

	public void cleanFrame() {
		items.clear();
		searchResult.setText("none");
		wordInput.setText("");
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		items = FXCollections.observableArrayList ();
		topOccurrences.setItems(items);
		
	}

}