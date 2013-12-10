package it.f2informatica.acceptance.page.user;

import it.f2informatica.acceptance.page.Page;
import org.openqa.selenium.WebDriver;

public abstract class UserFormPage extends Page {
	protected static final String USERNAME_INPUT_XPATH = "//input[@id='username']";
	protected static final String PASSWORD_INPUT_XPATH = "//input[@id='password']";
	protected static final String ROLE_SELECT_XPATH = "//select[@id='roleId']";
	protected static final String LASTNAME_INPUT_XPATH = "//input[@id='lastName']";
	protected static final String FIRSTNAME_INPUT_XPATH = "//input[@id='firstName']";
	protected static final String EMAIL_INPUT_XPATH = "//input[@id='email']";

	public UserFormPage(WebDriver driver, String baseUrl, String path) {
		super(driver, baseUrl, path);
	}

	public void typeNewUsername(String username) {
		clearAndSendKeys(findElement(USERNAME_INPUT_XPATH), username);
	}

	public void typeNewPassword(String password) {
		clearAndSendKeys(findElement(PASSWORD_INPUT_XPATH), password);
	}

	public void selectNormalUserRole() {
		select(findElement(ROLE_SELECT_XPATH), "User");
	}

	public void typeLastName(String lastName) {
		clearAndSendKeys(findElement(LASTNAME_INPUT_XPATH), lastName);
	}

	public void typeFirstName(String firstName) {
		clearAndSendKeys(findElement(FIRSTNAME_INPUT_XPATH), firstName);
	}

	public void typeEmail(String email) {
		clearAndSendKeys(findElement(EMAIL_INPUT_XPATH), email);
	}
}
