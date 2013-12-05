package it.f2informatica.acceptance.page.user;

import it.f2informatica.acceptance.page.Page;
import org.openqa.selenium.WebDriver;

public class CreateNewUserPage extends Page {
	private static final String USERNAME_INPUT_XPATH = "//input[@name='username']";
	private static final String PASSWORD_INPUT_XPATH = "//input[@name='password']";
	private static final String ROLE_SELECT_XPATH = "//select[@id='roleId']";
	private static final String SAVE_USER_BUTTON_XPATH = "//input[@id='saveUser']";

	public CreateNewUserPage(WebDriver driver, String baseUrl) {
		super(driver, baseUrl, "/user/createNewUser");
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

	public UserManagementPage clickOnSaveUserButton() {
		click(findElement(SAVE_USER_BUTTON_XPATH));
		return new UserManagementPage(driver, baseUrl);
	}
}
