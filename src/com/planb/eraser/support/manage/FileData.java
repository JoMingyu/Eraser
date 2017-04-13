package com.planb.eraser.support.manage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

// this class manages file data(file path, file hash)
public class FileData {
	private static List<File> filePath = new ArrayList<File>();
	private static List<String> fileHash = new ArrayList<String>();
	
	private static List<File> duplicatedFilePath = new ArrayList<File>();
	private static List<String> duplicatedFileHash = new ArrayList<String>();
	// 'only' duplicated files data in these list
	
	private static List<String> duplicatedFileState = new ArrayList<String>();
	
	public void addFilePath(File file) {
		FileData.filePath.add(file);
	}
	
	public File getFilePath(int index) {
		return filePath.get(index);
	}
	
	public void addFileHash(String hash) {
		FileData.fileHash.add(hash);
	}
	
	public String getFileHash(int index) {
		return fileHash.get(index);
	}
	
	public int getFileSize() {
		return filePath.size();
	}
	
	public void addDuplicatedFilePath(File duplicatedFilePath) {
		FileData.duplicatedFilePath.add(duplicatedFilePath);
	}
	
	public File getDuplicatedFilePath(int index) {
		return duplicatedFilePath.get(index);
	}
	
	public void addDuplicatedFileHash(String duplicatedFileHash) {
		FileData.duplicatedFileHash.add(duplicatedFileHash);
	}
	
	public String getDuplicatedFileHash(int index) {
		return duplicatedFileHash.get(index);
	}
	
	public int getDuplicatedFileSize() {
		return duplicatedFilePath.size();
	}

	public void clearFileData() {
		FileData.filePath.clear();
		FileData.fileHash.clear();
	}

	public String getDuplicatedFileState(int index) {
		return duplicatedFileState.get(index);
	}

	public void addDuplicatedFileState(String duplicatedFileState) {
		FileData.duplicatedFileState.add(duplicatedFileState);
	}
	
	public int getDuplicatedFileStateSize() {
		return FileData.duplicatedFileState.size();
	}
	
	/* -- how to get filePathes --
	    for(int i = 0; i < FileData.filePath.size(); i++) {
				System.out.println(FileData.filePath.get(i));
		}
	 */
}
