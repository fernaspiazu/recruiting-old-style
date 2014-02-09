package it.f2informatica.acceptance.page.consultant;

import it.f2informatica.acceptance.page.Page;
import org.openqa.selenium.WebDriver;

public abstract class ExperienceFormPage extends Page {
	protected static final String CONSULTANT_ID_HIDDEN_XPATH = "//input[@id='consultantId']";
	protected static final String COMPANY_NAME_INPUT_XPATH = "//input[@id='companyName']";
	protected static final String FUNCTION_INPUT_XPATH = "//input[@id='function']";
	protected static final String COMPANY_LOCATION_INPUT_XPATH = "//input[@id='location']";
	protected static final String MONTH_PERIOD_FROM_SELECT_XPATH = "//select[@name='monthPeriodFrom']";
	protected static final String YEAR_PERIOD_FROM_INPUT_XPATH = "//input[@name='yearPeriodFrom']";
	protected static final String MONTH_PERIOD_TO_SELECT_XPATH = "//select[@name='monthPeriodTo']";
	protected static final String YEAR_PERIOD_TO_INPUT_XPATH = "//input[@name='yearPeriodTo']";
	protected static final String DESCRIPTION_TEXTAREA_XPATH = "//textarea[@id='experienceDescription']";
	protected static final String SAVE_EXPERIENCE_BUTTON_XPATH = "//input[@id='saveExperience']";

	public ExperienceFormPage(WebDriver driver, String baseUrl, String path) {
		super(driver, baseUrl, path);
	}

	public String getConsultantIdValue() {
		return getValue(findElement(CONSULTANT_ID_HIDDEN_XPATH));
	}

	public void typeCompanyName(String companyName) {
		clearAndSendKeys(findElement(COMPANY_NAME_INPUT_XPATH), companyName);
	}

	public String getCompanyName() {
		return getValue(findElement(COMPANY_NAME_INPUT_XPATH));
	}

	public void typeFunction(String function) {
		clearAndSendKeys(findElement(FUNCTION_INPUT_XPATH), function);
	}

	public void typeLocation(String location) {
		clearAndSendKeys(findElement(COMPANY_LOCATION_INPUT_XPATH), location);
	}

	public void selectPeriodFromApril() {
		selectByValue(findElement(MONTH_PERIOD_FROM_SELECT_XPATH), "3");
	}

	public void typePeriofFromYear(String year) {
		clearAndSendKeys(findElement(YEAR_PERIOD_FROM_INPUT_XPATH), year);
	}

	public void selectPeriodToDecember() {
		selectByValue(findElement(MONTH_PERIOD_TO_SELECT_XPATH), "11");
	}

	public void typePeriodToYear(String year) {
		clearAndSendKeys(findElement(YEAR_PERIOD_TO_INPUT_XPATH), year);
	}

	public void typeDescription(String description) {
		clearAndSendKeys(findElement(DESCRIPTION_TEXTAREA_XPATH), description);
	}

	public void saveExperience() {
		click(findElement(SAVE_EXPERIENCE_BUTTON_XPATH));
	}
}
