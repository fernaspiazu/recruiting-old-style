package it.f2informatica.acceptance.page.consultant;

import it.f2informatica.acceptance.page.Page;
import org.openqa.selenium.WebDriver;

public class ConsultantManagementPage extends Page {
	private static final String REGISTER_NEW_CONSULTANT_BUTTON_XPATH = "//input[@id='newConsultantRegistration']";

	public ConsultantManagementPage(WebDriver driver, String baseUrl) {
		super(driver, baseUrl, "/consultant");
	}

	public ConsultantRegistrationPage consultantRegistrationForm() {
		click(findElement(REGISTER_NEW_CONSULTANT_BUTTON_XPATH));
		return new ConsultantRegistrationPage(driver, baseUrl);
	}
}
