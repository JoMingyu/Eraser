package com.planb.eraser.fx.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.planb.eraser.support.manage.UserData;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class GetUserData2Controller implements Initializable {

	@FXML
	private AnchorPane parent;

	@FXML
	private TextField minCapacityField;

	@FXML
	private TextField maxCapacityField;

	@FXML
	private ComboBox<String> capacityTypeComboBox;

	@FXML
	private Label number2Label;

	@FXML
	private Label waveDashLabel;

	@FXML
	private Button nextButton;

	UserData userData = new UserData();
	
	private Stage primaryStage;

	public void setPrimaryStage(Stage primaryStage) {
		// get primaryStage from Main class
		this.primaryStage = primaryStage;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		nextButton.setOnAction(event -> nextButtonOnAction(event));

		capacityTypeComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> selected, String oldValue, String newValue) {
				reset();
				switch (newValue) {
				case "모든 파일에 대해 검색":
					userData.setCapacityType("All");
					nextButton.setDisable(false);
					break;
				case "Byte":
				case "KB":
				case "MB":
				case "GB":
					userData.setCapacityType(newValue);
					number2Label.setDisable(false);
					waveDashLabel.setDisable(false);
					minCapacityField.setDisable(false);
					maxCapacityField.setDisable(false);
					nextButton.setDisable(false);
					break;
				} // switch
			} // changed
		}); // addListener
	} // Initialize

	public void nextButtonOnAction(ActionEvent event) {
		int minCapacity;
		int maxCapacity;
		
		// when any string in TextField, loop catch() because exception occurs in parseInt
		try {
			// first of all, check the capacity type equals "All"
			if(userData.getCapacityType().equals("All")) {
				userData.setCapacityType("All");
				changeScene();
				return;
				// when capacityType is "All", change the scene because need not any checking logics
			}
			
			// this block can arouse exception when any string in TextField
			minCapacity = Integer.parseInt(minCapacityField.getText());
			maxCapacity = Integer.parseInt(maxCapacityField.getText());
			
			// loop below codes TextField consist of only numeric data
			// if minCapacity is over the maxCapacity
			if(minCapacity > maxCapacity) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("다시 입력해주세요.");
				alert.setHeaderText("이런, 문제가 발생했군요.");
				alert.setContentText("최소 용량은 최대 용량보다 클 수 없습니다. 다시 입력해주세요.");
				alert.showAndWait();
				
				minCapacityField.clear();
				maxCapacityField.clear();
				
				return;
			}
			// if minCapacity or maxCapacity is a negative quantity
			if(minCapacity < 0 || maxCapacity < 0) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("다시 입력해주세요.");
				alert.setHeaderText("이런, 문제가 발생했군요.");
				alert.setContentText("용량은 음수가 될 수 없습니다. 다시 입력해주세요.");
				alert.showAndWait();
				
				minCapacityField.clear();
				maxCapacityField.clear();
				
				return;
			} // if
		} // try
		catch(Exception e) {			
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("다시 입력해주세요.");
			alert.setHeaderText("이런, 문제가 발생했군요.");
			alert.setContentText("용량의 범위 안엔 숫자만 들어갈 수 있습니다. 다시 입력해주세요.");
			alert.showAndWait();
			
			minCapacityField.clear();
			maxCapacityField.clear();
			
			return;
		}
		
		userData.setMinCapacity(minCapacity);
		userData.setMaxCapacity(maxCapacity);
		
		changeScene();
	}
	
	// if there's any exception in nextButtonOnAction(), reset to beginning
	public void reset() {
		nextButton.setDisable(true);
		number2Label.setDisable(true);
		waveDashLabel.setDisable(true);
		minCapacityField.setDisable(true);
		minCapacityField.clear();
		maxCapacityField.setDisable(true);
		maxCapacityField.clear();
	}

	public void changeScene() {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/com/planb/eraser/fx/layout/Layout_Get_UserData3.fxml"));
			Scene scene = new Scene(root);

			primaryStage = (Stage) parent.getScene().getWindow();
			primaryStage.setTitle("Eraser");
			primaryStage.getIcons().add(new Image("/com/planb/eraser/fx/layout/Eraser.png"));
			primaryStage.setScene(scene);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
