package it.f2informatica.acceptance.page;

import org.openqa.selenium.WebDriver;

public class LoginPage extends Page {
	private static final String USERNAME_INPUT_XPATH = "//input[@name='username']";
	private static final String PASSWORD_INPUT_XPATH = "//input[@name='password']";
	private static final String LOGIN_BUTTON_XPATH = "//input[@id='submit']";

	public LoginPage(WebDriver driver, String baseUrl) {
		super(driver, baseUrl, "/");
	}

	public void typeUsername(String username) {
		clearAndSendKeys(findElement(USERNAME_INPUT_XPATH), username);
	}

	public void typePassword(String password) {
		clearAndSendKeys(findElement(PASSWORD_INPUT_XPATH), password);
	}

	public HomePage clickOnLoginButton() {
		click(findElement(LOGIN_BUTTON_XPATH));
		return new HomePage(driver, baseUrl);
	}

	public LoginPage clickOnLoginButtonExpectingFailure() {
		click(findElement(LOGIN_BUTTON_XPATH));
		return new LoginPage(driver, baseUrl);
	}

}
