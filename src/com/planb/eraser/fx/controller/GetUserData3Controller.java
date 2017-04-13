package com.planb.eraser.fx.controller;

import java.net.URL;
import java.util.Calendar;
import java.util.ResourceBundle;

import com.planb.eraser.support.logic.Logics;
import com.planb.eraser.support.manage.UserData;

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
import javafx.scene.control.DatePicker;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class GetUserData3Controller implements Initializable {

	@FXML
	private AnchorPane parent;

	@FXML
	private DatePicker startDatePicker;

	@FXML
	private DatePicker endDatePicker;

	@FXML
	private Button nextButton;

	Calendar calendar = Calendar.getInstance();
	int todayYear = calendar.get(Calendar.YEAR);
	int todayMonth = calendar.get(Calendar.MONTH) + 1;
	int todayDate = calendar.get(Calendar.DATE);
	
	UserData userData = new UserData();
	Logics logics = new Logics();
	
	private Stage primaryStage;

	public void setPrimaryStage(Stage primaryStage) {
		// get primaryStage from Main class
		this.primaryStage = primaryStage;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		nextButton.setOnAction(event -> nextButtonOnAction(event));
		endDatePicker.setPromptText(todayYear + ". " + todayMonth + ". " + todayDate);
	}

	public void nextButtonOnAction(ActionEvent event) {
		String[] startDateSplit;
		String[] endDateSplit;

		// first, set start date and end date to UserData class
		if (startDatePicker.getValue() != null) {
			startDateSplit = startDatePicker.getValue().toString().split("-");
			userData.setStartYear(Integer.parseInt(startDateSplit[0]));
			userData.setStartMonth(Integer.parseInt(startDateSplit[1]));
			userData.setStartDate(Integer.parseInt(startDateSplit[2]));
		}
		// if startDatePicker is null, set start date 2000-01-01
		else {
			userData.setStartYear(2000);
			userData.setStartMonth(1);
			userData.setStartDate(1);
		}

		if (endDatePicker.getValue() != null) {
			endDateSplit = endDatePicker.getValue().toString().split("-");
			userData.setEndYear(Integer.parseInt(endDateSplit[0]));
			userData.setEndMonth(Integer.parseInt(endDateSplit[1]));
			userData.setEndDate(Integer.parseInt(endDateSplit[2]));
		}
		// if endDatePicker is null, set end date today
		else {
			userData.setEndYear(todayYear);
			userData.setEndMonth(todayMonth);
			userData.setEndDate(todayDate);
		}
		
		// check date : dates are in the past than today & start date is in the past than end date
		if(logics.compareDate(userData.getStartYear(), userData.getStartMonth(), userData.getStartDate(), todayYear, todayMonth, todayDate)
		&& logics.compareDate(userData.getEndYear(), userData.getEndMonth(), userData.getEndDate(), todayYear, todayMonth, todayDate)) {
			if(logics.compareDate(userData.getStartYear(), userData.getStartMonth(), userData.getStartDate(), userData.getEndYear(), userData.getEndMonth(), userData.getEndDate())) {
				changeScene();
			} else {
				alertDialog();
			}
		} else {
			alertDialog();
		}
	}

	public void alertDialog() {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("다시 입력해주세요.");
		alert.setHeaderText("이런, 문제가 발생했군요.");
		alert.setContentText("날짜의 시작과 끝의 범위가 정상적이지 않거나, 날짜가 오늘보다 미래입니다.\n다시 확인해주세요.");
		alert.showAndWait();
	}

	public void changeScene() {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/com/planb/eraser/fx/layout/Layout_Show_Equivalent_Files.fxml"));
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
