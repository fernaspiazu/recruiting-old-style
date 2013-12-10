package it.f2informatica.acceptance.page.user;

import org.openqa.selenium.WebDriver;

public class CreateNewUserPage extends UserFormPage {
	private static final String SAVE_USER_BUTTON_XPATH = "//input[@id='saveUser']";

	public CreateNewUserPage(WebDriver driver, String baseUrl) {
		super(driver, baseUrl, "/user/new");
	}

	public UserManagementPage clickOnSaveUserButton() {
		click(findElement(SAVE_USER_BUTTON_XPATH));
		return new UserManagementPage(driver, baseUrl);
	}
}
