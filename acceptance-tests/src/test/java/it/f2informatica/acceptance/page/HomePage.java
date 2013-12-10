package it.f2informatica.acceptance.page;

import org.openqa.selenium.WebDriver;

public class HomePage extends Page {
	public static final String USER_LOGGED_IN_SPAN_XPATH = "//span[@id='userLoggedIn']";

	public HomePage(WebDriver driver, String baseUrl) {
		super(driver, baseUrl, "/home");
	}

	public String getUserLoggedIn() {
		return findElement(USER_LOGGED_IN_SPAN_XPATH).getText();
	}

}
