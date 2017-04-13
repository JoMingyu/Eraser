package com.planb.eraser.fx.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.planb.eraser.support.logic.Logics;
import com.planb.eraser.support.manage.FileData;
import com.planb.eraser.support.manage.TableDataModel;
import com.planb.eraser.support.manage.UserData;
import com.sun.jna.platform.win32.W32FileUtils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ShowEquivalentFilesController implements Initializable {
	@FXML
	private AnchorPane parent;

	@FXML
	private TableView<TableDataModel> tableView;

	W32FileUtils fileUtils = new W32FileUtils();
	FileData fileData = new FileData();
	UserData userData = new UserData();
	Logics logics = new Logics();

	public ObservableList<TableDataModel> data = FXCollections.observableArrayList();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		TableColumn fileNameCol = tableView.getColumns().get(0);
		fileNameCol.setCellValueFactory(new PropertyValueFactory("fileName"));

		TableColumn filePathCol = tableView.getColumns().get(1);
		filePathCol.setCellValueFactory(new PropertyValueFactory("filePath"));

		TableColumn currentStateCol = tableView.getColumns().get(2);
		currentStateCol.setCellValueFactory(new PropertyValueFactory("currentState"));
		
		// Connect table columns
		pickOutDuplicationFiles();
		
		try {
			showAndDelete();
		} catch (IOException e) {
			e.printStackTrace();
		}

		 tableView.setItems(data);
	}

	public void pickOutDuplicationFiles() {
		boolean[] checkedIndex = new boolean[fileData.getFileSize() + 1];
		// if duplicated each other files, checkedIndex[file's index] changes true

		for (int i = 0; i < fileData.getFileSize() - 1; i++) {
			boolean isFileDuplicated = false;
			// when any file equals fileHash[i], this variable changes true

			if (logics.compareUserData(fileData.getFilePath(i))) {
				for (int j = i + 1; j < fileData.getFileSize(); j++) {
					if (fileData.getFileHash(i).equals(fileData.getFileHash(j))
							&& !checkedIndex[i]
							&& !checkedIndex[j]
							&& logics.compareUserData(fileData.getFilePath(j))) { // if hash values are equivalent
						checkedIndex[j] = true; // check
						isFileDuplicated = true; // check
						fileData.addDuplicatedFilePath(fileData.getFilePath(j));
						fileData.addDuplicatedFileHash(fileData.getFileHash(j));
					}
				}
				if (isFileDuplicated) {
					checkedIndex[i] = true;
					fileData.addDuplicatedFilePath(fileData.getFilePath(i));
					fileData.addDuplicatedFileHash(fileData.getFileHash(i));
				}
			}
		}
		fileData.addDuplicatedFileHash("null");
		// to prevent IndexOutOfBoundsException
	}
	
	public void showAndDelete() throws IOException {
		for (int i = 0; i < fileData.getDuplicatedFileSize() - 1; i++) {
			// delete duplicated files
			if (userData.getDeleteOption().equals("'휴지통으로 보내기'")) {
				if (fileData.getDuplicatedFileHash(i).equals(fileData.getDuplicatedFileHash(i + 1))) {
				// eqauls next index from list
					fileUtils.moveToTrash(new File[] { new File(fileData.getDuplicatedFilePath(i).toString()) });
					fileData.addDuplicatedFileState("휴지통으로 이동");
				} else {
					fileData.addDuplicatedFileState("null");
				}
				
			} else if (userData.getDeleteOption().equals("'즉시 삭제'")) {
				if (fileData.getDuplicatedFileHash(i).equals(fileData.getDuplicatedFileHash(i + 1))) {
					File file = new File(fileData.getDuplicatedFilePath(i).toString());
					file.delete();
					fileData.addDuplicatedFileState("즉시 삭제됨");
				} else {
					fileData.addDuplicatedFileState("null");
				}
				
			} else if (userData.getDeleteOption().equals("'삭제하지 않고 알려주기'")) {
				fileData.addDuplicatedFileState("중복됨");
				// ..
			}
		}
		
		for (int i = 0; i < fileData.getDuplicatedFileStateSize(); i++) {
			if(fileData.getDuplicatedFileState(i) == null) {
				data.addAll(new TableDataModel(
						fileData.getDuplicatedFilePath(i).getName(), 
						fileData.getDuplicatedFilePath(i).toString(),
						"유지"
					)
				);
				continue;
			}
			data.addAll(new TableDataModel(
							fileData.getDuplicatedFilePath(i).getName(), 
							fileData.getDuplicatedFilePath(i).toString(),
							fileData.getDuplicatedFileState(i).toString()
						)
				);
		}
	}
}
