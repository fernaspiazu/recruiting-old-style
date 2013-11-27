package it.f2informatica.acceptance.page.consultant;

import it.f2informatica.acceptance.page.Page;
import org.openqa.selenium.WebDriver;

public class ConsultantManagementPage extends Page {

	public ConsultantManagementPage(WebDriver driver, String baseUrl) {
		super(driver, baseUrl, "/consultant");
	}

	public ConsultantRegistrationPage consultantRegistrationForm() {
		driver.get(baseUrl + "/consultant/create");
		return new ConsultantRegistrationPage(driver, baseUrl);
	}
}
