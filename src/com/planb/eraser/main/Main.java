package com.planb.eraser.main;

import com.planb.eraser.fx.controller.GetUserData1Controller;
import com.planb.eraser.fx.controller.GetUserData2Controller;
import com.planb.eraser.fx.controller.GetUserData3Controller;
import com.planb.eraser.fx.controller.LayoutMainController;
import com.planb.eraser.fx.controller.ShowEquivalentFilesController;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/com/planb/eraser/fx/layout/Layout_Main.fxml"));
		Scene scene = new Scene(root);
		
		LayoutMainController mainController = new LayoutMainController();
		mainController.setPrimaryStage(primaryStage);		// inject primaryStage to MainController class
		
		GetUserData1Controller getUserData1Controller = new GetUserData1Controller();
		getUserData1Controller.setPrimaryStage(primaryStage);
		
		GetUserData2Controller getUserData2Controller = new GetUserData2Controller();
		getUserData2Controller.setPrimaryStage(primaryStage);
		
		GetUserData3Controller getUserData3Controller = new GetUserData3Controller();
		getUserData3Controller.setPrimaryStage(primaryStage);
		
		primaryStage.setScene(scene);
		primaryStage.setTitle("Eraser");
		primaryStage.getIcons().add(new Image("/com/planb/eraser/fx/layout/Eraser.png"));
		primaryStage.show();
	}
	public static void main(String[] args) {
		launch(args);
	}
}
