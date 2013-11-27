package it.f2informatica.acceptance.page;

import org.openqa.selenium.WebDriver;

public class UserPage extends Page {

	public UserPage(WebDriver driver, String baseUrl) {
		super(driver, baseUrl, "/loadUsers");
	}

}
