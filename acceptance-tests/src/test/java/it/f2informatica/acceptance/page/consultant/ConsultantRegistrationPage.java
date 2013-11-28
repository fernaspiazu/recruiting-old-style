package it.f2informatica.acceptance.page.consultant;

import it.f2informatica.acceptance.page.Page;
import org.openqa.selenium.WebDriver;

import java.util.Calendar;

public class ConsultantRegistrationPage extends Page {
	private static final String SELECT_REGISTRATIONDATE_DAY_XPATH = "//select[@id='registrationdate_day']";
	private static final String SELECT_REGISTRATIONDATE_MONTH_XPATH = "//select[@id='registrationdate_month']";
	private static final String SELECT_REGISTRATIONDATE_YEAR_XPATH = "//select[@id='registrationdate_year']";

	private static final String INPUT_FIRSTNAME_XPATH = "//input[@id='firstName']";
	private static final String INPUT_LASTNAME_XPATH = "//input[@id='lastName']";
	private static final String SELECT_GENDER_XPATH = "//select[@id='gender']";
	private static final String INPUT_EMAIL_XPATH = "//input[@id='email']";
	private static final String INPUT_FISCALCODE_XPATH = "//input[@id='fiscalCode']";

	private static final String SELECT_BIRTHDATE_DAY_XPATH = "//select[@id='birthdate_day']";
	private static final String SELECT_BIRTHDATE_MONTH_XPATH = "//select[@id='birthdate_month']";
	private static final String SELECT_BIRTHDATE_YEAR_XPATH = "//select[@id='birthdate_year']";

	private static final String INPUT_BIRTHCITY_XPATH = "//input[@id='birthCity']";
	private static final String INPUT_BIRTHCOUNTRY_XPATH = "//input[@id='birthCountry']";
	private static final String INPUT_PHONENUMBER_XPATH = "//input[@id='phoneNumber']";
	private static final String INPUT_MOBILENUMBER_XPATH = "//input[@id='mobileNumber']";

	private static final String SAVE_AND_CONTINUE_PROFILE_PAGE_BUTTON = "//input[@id='saveAndContinueProfile']";

	public ConsultantRegistrationPage(WebDriver driver, String baseUrl) {
		super(driver, baseUrl, "/consultant/create");
	}

	public void selectRegistrationDate() {
		selectByValue(findElement(SELECT_REGISTRATIONDATE_DAY_XPATH), currentDay());
		selectByValue(findElement(SELECT_REGISTRATIONDATE_MONTH_XPATH), currentMonth());
		selectByValue(findElement(SELECT_REGISTRATIONDATE_YEAR_XPATH), currentYear());
	}

	private String currentDay() {
		return String.valueOf(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
	}

	private String currentMonth() {
		return String.valueOf(Calendar.getInstance().get(Calendar.MONTH));
	}

	private String currentYear() {
		return String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
	}

	public void typeFirstName(String firstName) {
		clearAndSendKeys(findElement(INPUT_FIRSTNAME_XPATH), firstName);
	}

	public void typeLastName(String lastName) {
		clearAndSendKeys(findElement(INPUT_LASTNAME_XPATH), lastName);
	}

	public void selectMaleGender() {
		selectByValue(findElement(SELECT_GENDER_XPATH), "M");
	}

	public void typeEmail(String email) {
		clearAndSendKeys(findElement(INPUT_EMAIL_XPATH), email);
	}

	public void typeFiscalCode(String fiscalCode) {
		clearAndSendKeys(findElement(INPUT_FISCALCODE_XPATH), fiscalCode);
	}

	public void selectBirthDate() {
		selectByValue(findElement(SELECT_BIRTHDATE_DAY_XPATH), String.valueOf(5));
		selectByValue(findElement(SELECT_BIRTHDATE_MONTH_XPATH), String.valueOf(Calendar.JUNE));
		selectByValue(findElement(SELECT_BIRTHDATE_YEAR_XPATH), String.valueOf(1978));
	}

	public void typeBirthCity(String birthCity) {
		clearAndSendKeys(findElement(INPUT_BIRTHCITY_XPATH), birthCity);
	}

	public void typeBirthCountry(String birthCountry) {
		clearAndSendKeys(findElement(INPUT_BIRTHCOUNTRY_XPATH), birthCountry);
	}

	public void typePhoneNumber(String phoneNumber) {
		clearAndSendKeys(findElement(INPUT_PHONENUMBER_XPATH), phoneNumber);
	}

	public void typeMobileNumber(String mobileNumber) {
		clearAndSendKeys(findElement(INPUT_MOBILENUMBER_XPATH), mobileNumber);
	}

	public void clickOnSaveAndContinueRegisteringProfile() {
		click(findElement(SAVE_AND_CONTINUE_PROFILE_PAGE_BUTTON));
	}
}
