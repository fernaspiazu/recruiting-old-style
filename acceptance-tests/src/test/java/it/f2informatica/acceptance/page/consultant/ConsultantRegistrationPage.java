package it.f2informatica.acceptance.page.consultant;

import it.f2informatica.acceptance.page.Page;
import org.openqa.selenium.WebDriver;

public class ConsultantRegistrationPage extends Page {
	private static final String INPUT_REGISTRATION_DATE_XPATH = "//input[@id='registrationDate']";
	private static final String INPUT_CONSULTANT_NUMBER_XPATH = "//input[@id='consultantNo']";
	private static final String INPUT_FIRSTNAME_XPATH = "//input[@id='firstName']";
	private static final String INPUT_LASTNAME_XPATH = "//input[@id='lastName']";
	private static final String SELECT_GENDER_XPATH = "//select[@id='gender']";
	private static final String INPUT_EMAIL_XPATH = "//input[@id='email']";
	private static final String INPUT_FISCALCODE_XPATH = "//input[@id='fiscalCode']";
	private static final String INPUT_BIRTHDATE_XPATH = "//input[@id='birthDate']";
	private static final String INPUT_BIRTHCITY_XPATH = "//input[@id='birthCity']";
	private static final String INPUT_BIRTHCOUNTRY_XPATH = "//input[@id='birthCountry']";
	private static final String INPUT_PHONENUMBER_XPATH = "//input[@id='phoneNumber']";
	private static final String INPUT_MOBILENUMBER_XPATH = "//input[@id='mobileNumber']";

	private static final String SAVE_AND_CONTINUE_PROFILE_PAGE_BUTTON = "//input[@id='saveAndContinueProfile']";

	public ConsultantRegistrationPage(WebDriver driver, String baseUrl) {
		super(driver, baseUrl, "/consultant/create");
	}

	public String registrationDate() {
		return getValue(findElement(INPUT_REGISTRATION_DATE_XPATH));
	}

	public String consultantNumber() {
		return getValue(findElement(INPUT_CONSULTANT_NUMBER_XPATH));
	}

	public void typeFirstName(String firstName) {
		clearAndSendKeys(findElement(INPUT_FIRSTNAME_XPATH), firstName);
	}

	public void typeLastName(String lastName) {
		clearAndSendKeys(findElement(INPUT_LASTNAME_XPATH), lastName);
	}

	public void selectMaleGender() {
		selectByValue(findElement(SELECT_GENDER_XPATH), "MALE");
	}

	public void typeEmail(String email) {
		clearAndSendKeys(findElement(INPUT_EMAIL_XPATH), email);
	}

	public void typeFiscalCode(String fiscalCode) {
		clearAndSendKeys(findElement(INPUT_FISCALCODE_XPATH), fiscalCode);
	}

	public void typeBirthDate(String birthDate) {
		clearAndSendKeys(findElement(INPUT_BIRTHDATE_XPATH), birthDate);
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
