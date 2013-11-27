package it.f2informatica.acceptance.page.user;

import it.f2informatica.acceptance.page.Page;
import org.openqa.selenium.WebDriver;

public class UserEditPage extends Page {
	private static final String USERNAME_INPUT_XPATH = "//input[@id='username']";
	protected static final String UPDATE_USER_BUTTON_XPATH = "//input[@id='updateUser']";

	public UserEditPage(WebDriver driver, String baseUrl, String path) {
		super(driver, baseUrl, path);
	}

	public void typeNewUsername(String username) {
		clearAndSendKeys(findElement(USERNAME_INPUT_XPATH), username);
	}

	public UserManagementPage clickOnUpdateUserButton() {
		submit(findElement(UPDATE_USER_BUTTON_XPATH));
		return new UserManagementPage(driver, baseUrl);
	}
}
