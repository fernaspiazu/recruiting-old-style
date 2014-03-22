package it.f2informatica.webapp.utils;

public class Month {

	private String monthNumber;
	private String monthLabel;

	public Month(String monthNumber, String monthLabel) {
		this.monthNumber = monthNumber;
		this.monthLabel = monthLabel;
	}

	public String getMonthLabel() {
		return monthLabel;
	}

	public String getMonthNumber() {
		return monthNumber;
	}
}
