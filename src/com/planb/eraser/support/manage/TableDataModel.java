package com.planb.eraser.support.manage;

import javafx.beans.property.SimpleStringProperty;

public class TableDataModel {
	private SimpleStringProperty fileName;
	private SimpleStringProperty filePath;
	private SimpleStringProperty currentState;
	
	public TableDataModel(String fileName, String filePath, String currentState) {
		this.setFileName(new SimpleStringProperty(fileName));
		this.setFilePath(new SimpleStringProperty(filePath));
		this.setCurrentState(new SimpleStringProperty(currentState));
	}

	public String getFileName() {
		return fileName.get();
	}

	public void setFileName(SimpleStringProperty fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return filePath.get();
	}

	public void setFilePath(SimpleStringProperty filePath) {
		this.filePath = filePath;
	}

	public String getCurrentState() {
		return currentState.get();
	}

	public void setCurrentState(SimpleStringProperty currentState) {
		this.currentState = currentState;
	}
}
