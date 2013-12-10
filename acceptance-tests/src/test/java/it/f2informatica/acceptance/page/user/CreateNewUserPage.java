package it.f2informatica.acceptance.page.user;

import org.openqa.selenium.WebDriver;

public class CreateNewUserPage extends UserFormPage {

	public CreateNewUserPage(WebDriver driver, String baseUrl) {
		super(driver, baseUrl, "/user/new");
	}

	public UserManagementPage clickOnSaveUserButton() {
		click(findElement("//input[@id='saveUser']"));
		return new UserManagementPage(driver, baseUrl);
	}
}
