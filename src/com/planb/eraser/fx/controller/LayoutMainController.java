package com.planb.eraser.fx.controller;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.planb.eraser.support.logic.Logics;
import com.planb.eraser.support.manage.FileData;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class LayoutMainController implements Initializable {

	@FXML
	private AnchorPane parent;

	@FXML
	private Button fileChooserButton;

	@FXML
	private Button directoryChooserButton;

	@FXML
	private Button nextButton;

	@FXML
	private Label infoLabel;

	FileData fileData = new FileData();
	Logics logics = new Logics();
	
	private Stage primaryStage;

	public void setPrimaryStage(Stage primaryStage) {
		// get primaryStage from Main class
		this.primaryStage = primaryStage;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		fileChooserButton.setOnAction(event -> fileChooserOnAction(event));
		directoryChooserButton.setOnAction(event -> directoryChooserOnAction(event));
		nextButton.setOnAction(event -> nextButtonOnAction(event));
	}

	public void fileChooserOnAction(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("파일을 선택하세요.");

		// add extension filters
		fileChooser.getExtensionFilters().addAll(
				new ExtensionFilter("문서 파일", "*.txt", "*.rtf", "*.hwp", "*.pdf", "*.doc", "*.ppt"),
				new ExtensionFilter("이미지 파일", "*.png", "*.jpg", "*.gif", "*.bmp", "*.psd", "*.pdd", "*.tif"),
				new ExtensionFilter("오디오 파일", "*.wav", "*.mp3", "*.aac"),
				new ExtensionFilter("동영상 파일", "*.avi", "*.mp4", "*.mpeg", "*.mkv", "*.flv", "*.mov"),
				new ExtensionFilter("압축 파일", "*.zip", "*.egg", "*.rar", "*.tgz", "*.gz"),
				new ExtensionFilter("모든 파일", "*.*"));

		List<File> files = fileChooser.showOpenMultipleDialog(primaryStage);
		reset();

		// if multi opened files aren't null
		if (files != null) {
			for (File file : files) {
				// add file path for File form
				fileData.addFilePath(file);

				try {
					// add hash data for String form
					fileData.addFileHash(logics.extractFileHashSHA256(file));
				} catch (Exception e) {
					e.printStackTrace();
				}

			} // for
			
			if(fileData.getFileSize() > 1) {
				infoLabel.setText(fileData.getFileSize() + "개 파일 선택됨");
				nextButton.setDisable(false);
			}
			else {
				infoLabel.setText("2개 이상의 파일이 선택되어야 비교할 수 있습니다.");
			}
		} // if
	} // fileChooserOnAction

	public void directoryChooserOnAction(ActionEvent event) {

		DirectoryChooser directoryChooser = new DirectoryChooser();
		directoryChooser.setTitle("폴더를 선택하세요.");
		File dir = directoryChooser.showDialog(primaryStage);
		reset();

		if (dir != null) {
			logics.exploreFile(dir.getPath());
//			for (int i = 0; i < fileData.getFileSize(); i++) {
//				System.out.print(fileData.getFilePath(i) + " ");
//				System.out.println(fileData.getFileHash(i));
//			} // for

			if (fileData.getFileSize() != 0) {
				infoLabel.setText("[" + dir.getPath() + "] 에서 " + fileData.getFileSize() + "개 파일 선택됨");
				nextButton.setDisable(false);
			} else {
				infoLabel.setText("폴더에 파일 없음");
			} // inside if - else
		} else {
			infoLabel.setText("폴더를 찾을 수 없음");
		} // if - else
	} // directoryChooserOnAction

	public void nextButtonOnAction(ActionEvent event) {
		changeScene();
	}

	// when chose file or directory again, reset to beginning
	public void reset() {
		nextButton.setDisable(true);

		infoLabel.setText("");

		fileData.clearFileData();
	}

	public void changeScene() {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/com/planb/eraser/fx/layout/Layout_Get_UserData1.fxml"));
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
