package com.planb.eraser.support.manage;

// this class manages the user's settings
public class UserData {
	private static String deleteOption;
	
	private static String capacityType = "All";
	private static int maxCapacity;
	private static int minCapacity;
	
	private static int startYear;
	private static int startMonth;
	private static int startDate;
	private static int endYear;
	private static int endMonth;
	private static int endDate;

	public void setDeleteOption(String deleteOption) {
		UserData.deleteOption = deleteOption;
	}
	
	public String getDeleteOption() {
		String[] s = deleteOption.split("]");
		return s[1];
	}

	public void setCapacityType(String capacityType) {
		UserData.capacityType = capacityType;
	}
	
	public String getCapacityType() {
		return capacityType;
	}

	public void setMinCapacity(int minCapacity) {
		UserData.minCapacity = minCapacity;
	}
	
	public int getMinCapacity() {
		return minCapacity;
	}

	public void setMaxCapacity(int maxCapacity) {
		UserData.maxCapacity = maxCapacity;
	}
	
	public int getMaxCapacity() {
		return maxCapacity;
	}

	public void setStartYear(int startYear) {
		UserData.startYear = startYear;
	}
	
	public int getStartYear() {
		return startYear;
	}
	
	public void setStartMonth(int startMonth) {
		UserData.startMonth = startMonth;
	}
	
	public int getStartMonth() {
		return startMonth;
	}
	
	public void setStartDate(int startDate) {
		UserData.startDate = startDate;
	}
	
	public int getStartDate() {
		return startDate;
	}
	
	public void setEndYear(int endYear) {
		UserData.endYear = endYear;
	}
	
	public int getEndYear() {
		return endYear;
	}
	
	public void setEndMonth(int endMonth) {
		UserData.endMonth = endMonth;
	}
	
	public int getEndMonth() {
		return endMonth;
	}
	
	public void setEndDate(int endDate) {
		UserData.endDate = endDate;
	}
	
	public int getEndDate() {
		return endDate;
	}
}
