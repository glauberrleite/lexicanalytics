package org.lexicanalytics.control.controllers;

import org.lexicanalytics.model.BaseController;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;

public class ProcessingController extends BaseController{

	@FXML
	private ProgressIndicator progressIndicator;
	
	@FXML
	private Label status;
	
	public void setStatus(String status){
		this.status.setText("Status: " + status);
	}
	
	public void addProgress(double value){
		double actual = progressIndicator.getProgress();
		if(actual < 1)
			progressIndicator.setProgress(actual + (value * 0.01));
	}
}
