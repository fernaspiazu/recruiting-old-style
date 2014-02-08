package it.f2informatica.acceptance.page.consultant;

import it.f2informatica.acceptance.page.Page;
import org.openqa.selenium.WebDriver;

public class ProfileRegistrationPage extends Page {
	private static final String CONSULTANT_FULLNAME_H3_XPATH = "//h3[@id='consultantFullName']";
	private static final String EXPERIENCE_SECTION_DIV_XPATH = "//div[@id='experienceSection']";
	private static final String ADD_NEW_EXPERIENCE_BUTTON_XPATH = "//button[@id='addNewExperience']";
	private static final String ADD_EXPERIENCE_FORM_XPATH = "//form[@name='addExperienceForm']";
	private static final String COMPANY_NAME_INPUT_XPATH = "//input[@id='companyName']";
	private static final String FUNCTION_INPUT_XPATH = "//input[@id='function']";
	private static final String COMPANY_LOCATION_INPUT_XPATH = "//input[@id='location']";
	private static final String MONTH_PERIOD_FROM_SELECT_XPATH = "//select[@name='monthPeriodFrom']";
	private static final String YEAR_PERIOD_FROM_INPUT_XPATH = "//input[@name='yearPeriodFrom']";
	private static final String MONTH_PERIOD_TO_SELECT_XPATH = "//select[@name='monthPeriodTo']";
	private static final String YEAR_PERIOD_TO_INPUT_XPATH = "//input[@name='yearPeriodTo']";
	private static final String DESCRIPTION_TEXTAREA_XPATH = "//textarea[@id='description']";
	private static final String SAVE_EXPERIENCE_BUTTON_XPATH = "//input[@id='saveExperience']";

	private static final String EXPERIENCES_PRESENT_DIV_XPATH = "//div[@id='experiences']/*";

	public ProfileRegistrationPage(WebDriver driver, String baseUrl, String consultantId) {
		super(driver, baseUrl, "/consultant/profile/" + consultantId);
	}

	public String consultantWichWillBeAddedProfile() {
		return findElement(CONSULTANT_FULLNAME_H3_XPATH).getText();
	}

	public boolean isExperienceSectionPresent() {
		return findElement(EXPERIENCE_SECTION_DIV_XPATH).isDisplayed();
	}

	public void addNewExperience() {
		click(findElement(ADD_NEW_EXPERIENCE_BUTTON_XPATH));
	}

	public boolean isNewExperienceFormPresent() {
		return findElement(ADD_EXPERIENCE_FORM_XPATH).isDisplayed();
	}

	public boolean isAddNewExperienceButtonDisabled() {
		return !findElement(ADD_NEW_EXPERIENCE_BUTTON_XPATH).isEnabled();
	}

	public void typeCompanyName(String companyName) {
		clearAndSendKeys(findElement(COMPANY_NAME_INPUT_XPATH), companyName);
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

	public int experiencesNumber() {
		return findElements(EXPERIENCES_PRESENT_DIV_XPATH).size();
	}
}
