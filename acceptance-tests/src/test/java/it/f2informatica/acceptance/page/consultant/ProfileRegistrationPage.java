package it.f2informatica.acceptance.page.consultant;

import it.f2informatica.acceptance.page.Page;
import org.openqa.selenium.WebDriver;

public class ProfileRegistrationPage extends Page {
	private static final String INPUT_CONSULTANT_FULLNAME_XPATH = "//input[@id='consultantFullName']";

	public ProfileRegistrationPage(WebDriver driver, String baseUrl, String consultantId) {
		super(driver, baseUrl, "/consultant/profileDataRegistration/" + consultantId);
	}

	public String consultantWichWillBeAddedProfile() {
		return getValue(findElement(INPUT_CONSULTANT_FULLNAME_XPATH));
	}
}
