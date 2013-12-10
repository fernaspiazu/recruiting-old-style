package it.f2informatica.acceptance.page.user;

import org.openqa.selenium.WebDriver;

public class UserEditPage extends UserFormPage {
	protected static final String UPDATE_USER_BUTTON_XPATH = "//input[@id='updateUser']";

	public UserEditPage(WebDriver driver, String baseUrl, String userId) {
		super(driver, baseUrl, "/user/edit/"+userId);
	}

	public UserManagementPage updateUser() {
		click(findElement(UPDATE_USER_BUTTON_XPATH));
		return new UserManagementPage(driver, baseUrl);
	}

}
