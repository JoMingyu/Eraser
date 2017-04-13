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
import javafx.scene.control.Button;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class GetUserData1Controller implements Initializable {

	@FXML
	private AnchorPane parent;

	@FXML
	private ToggleGroup deleteOptionToggle;

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

		deleteOptionToggle.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			@Override
			public void changed(ObservableValue<? extends Toggle> value, Toggle oldValue, Toggle newValue) {
				userData.setDeleteOption(deleteOptionToggle.getSelectedToggle().toString());
				nextButton.setDisable(false);
			} // changed
		}); // addListener
	} // initialize

	public void nextButtonOnAction(ActionEvent event) {
		changeScene();
	}

	public void changeScene() {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/com/planb/eraser/fx/layout/Layout_Get_UserData2.fxml"));
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
