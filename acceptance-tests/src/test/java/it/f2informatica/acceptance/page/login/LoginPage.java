package it.f2informatica.acceptance.page.login;

import it.f2informatica.acceptance.page.HomePage;
import it.f2informatica.acceptance.page.Page;
import org.openqa.selenium.WebDriver;

public abstract class LoginPage extends Page {
	private static final String LOGIN_BUTTON_XPATH = "//input[@id='loginButton']";

	public LoginPage(WebDriver driver, String baseUrl, String path) {
		super(driver, baseUrl, path);
	}

	public void typeUsername(String username) {
		clearAndSendKeys(findElement("//input[@name='username']"), username);
	}

	public void typePassword(String password) {
		clearAndSendKeys(findElement("//input[@name='password']"), password);
	}

	/**
	 * Must load the home page when login is successful
	 */
	public HomePage clickOnLoginSuccessButton() {
		click(findElement(LOGIN_BUTTON_XPATH));
		return new HomePage(driver, baseUrl);
	}

	/**
	 * Must load the login page showing the message which
	 * cause the login failure
	 */
	public LoginPage clickOnLoginFailureButton() {
		click(findElement(LOGIN_BUTTON_XPATH));
		return new FailLoginPage(driver, baseUrl);
	}


	public boolean isLoginErrorMessagePresent() {
		return findElement("//div[@id='login-error-msg']").isDisplayed();
	}

}
