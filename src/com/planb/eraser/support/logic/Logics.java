package com.planb.eraser.support.logic;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.security.MessageDigest;
import java.util.Calendar;
import java.util.GregorianCalendar;

import com.planb.eraser.support.manage.FileData;
import com.planb.eraser.support.manage.UserData;

public class Logics {
	FileData fileData = new FileData();
	UserData userData = new UserData();

	public void exploreFile(String source) {
		// explore files in sub-directory for recursive calling

		File dir = new File(source); // directory path
		File[] fileList = dir.listFiles(); // directory's files
		for (int i = 0; i < fileList.length; i++) {
			if (fileList[i].isFile()) {
				// add file path for File form
				fileData.addFilePath(fileList[i]);
				try {
					// add hash data for String form
					fileData.addFileHash(this.extractFileHashSHA256(fileList[i]));
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (fileList[i].isDirectory()) {
				try {
					// recursive call(serve directory path)
					exploreFile(fileList[i].getCanonicalPath().toString());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public String extractFileHashSHA256(File filePath) throws Exception {
		// returns SHA256 hash value

		String SHA = "";
		int buff = 16384;
		try {
			RandomAccessFile file = new RandomAccessFile(filePath, "r");
			// access file

			MessageDigest hashSum = MessageDigest.getInstance("SHA-256");

			byte[] buffer = new byte[buff];
			byte[] partialHash = null;

			long read = 0;

			// calculate the hash of the hole file for the test
			long offset = file.length();
			int unitSize;
			while (read < offset) {
				unitSize = (int) (((offset - read) >= buff) ? buff : (offset - read));
				file.read(buffer, 0, unitSize);

				hashSum.update(buffer, 0, unitSize);

				read += unitSize;
			}

			file.close();
			partialHash = new byte[hashSum.getDigestLength()];
			partialHash = hashSum.digest();

			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < partialHash.length; i++) {
				sb.append(Integer.toString((partialHash[i] & 0xff) + 0x100, 16).substring(1));
			}
			SHA = sb.toString();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return SHA;
	}

	public boolean compareDate(int firstYear, int firstMonth, int firstDate, int secondYear, int secondMonth, int secondDate) {
		// when first date is in the past than second date, return true

		if (firstYear < secondYear) {
			return true;
		} else if (firstYear == secondYear) {
			if (firstMonth < secondMonth) {
				return true;
			} else if (firstMonth == secondMonth) {
				if (firstDate <= secondDate) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public boolean compareUserData(File filePath) {
		Calendar fileCreationTime = new GregorianCalendar();
		fileCreationTime.setTimeInMillis(filePath.lastModified());

		int year = Integer.parseInt("" + fileCreationTime.get(Calendar.YEAR));
		int month = Integer.parseInt("" + (fileCreationTime.get(Calendar.MONTH) + 1));
		int date = Integer.parseInt("" + fileCreationTime.get(Calendar.DATE));
		// get creation time

		long minCapacity;
		long maxCapacity;

		if (userData.getCapacityType().equals("Byte")) {
			minCapacity = userData.getMinCapacity();
			maxCapacity = userData.getMaxCapacity();
		} else if (userData.getCapacityType().equals("KB")) {
			minCapacity = userData.getMinCapacity() * 1024;
			maxCapacity = userData.getMaxCapacity() * 1024;
		} else if (userData.getCapacityType().equals("MB")) {
			minCapacity = userData.getMinCapacity() * 1048576;
			maxCapacity = userData.getMaxCapacity() * 1048576;
		} else if (userData.getCapacityType().equals("GB")) {
			minCapacity = userData.getMinCapacity() * 1073741874;
			maxCapacity = userData.getMaxCapacity() * 1073741874;
		} else {
			minCapacity = 0;
			maxCapacity = Long.MAX_VALUE;
		}

		if (minCapacity < filePath.length()
			&& maxCapacity > filePath.length()
			&& compareDate(userData.getStartYear(), userData.getStartMonth(), userData.getStartDate(), year, month, date)
			&& compareDate(year, month, date, userData.getEndYear(), userData.getEndMonth(), userData.getEndDate())) {
			return true;
		}

		return false;
	}
}
