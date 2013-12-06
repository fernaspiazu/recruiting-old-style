package it.f2informatica.acceptance.page.login;

import org.openqa.selenium.WebDriver;

public class DefaultLoginPage extends LoginPage {

	public DefaultLoginPage(WebDriver driver, String baseUrl) {
		super(driver, baseUrl, "/login");
	}

}
