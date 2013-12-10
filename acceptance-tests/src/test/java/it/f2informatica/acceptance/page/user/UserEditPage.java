package it.f2informatica.acceptance.page.user;

import org.openqa.selenium.WebDriver;

public class UserEditPage extends UserFormPage {

	public UserEditPage(WebDriver driver, String baseUrl, String userId) {
		super(driver, baseUrl, "/user/edit/"+userId);
	}

	public UserManagementPage updateUser() {
		click(findElement("//input[@id='updateUser']"));
		return new UserManagementPage(driver, baseUrl);
	}

}
