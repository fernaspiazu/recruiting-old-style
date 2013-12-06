package it.f2informatica.acceptance.page.login;

import org.openqa.selenium.WebDriver;

public class FailLoginPage extends LoginPage {

	public FailLoginPage(WebDriver driver, String baseUrl) {
		super(driver, baseUrl, "/login_failed");
	}

}
