package it.f2informatica.acceptance.page.user;

import it.f2informatica.acceptance.page.Page;
import org.openqa.selenium.WebDriver;

public abstract class UserFormPage extends Page {

	public UserFormPage(WebDriver driver, String baseUrl, String path) {
		super(driver, baseUrl, path);
	}

	public void typeNewUsername(String username) {
		clearAndSendKeys(findElement("//input[@id='username']"), username);
	}

	public void typeNewPassword(String password) {
		clearAndSendKeys(findElement("//input[@id='password']"), password);
	}

	public void selectNormalUserRole() {
		select(findElement("//select[@id='roleId']"), "User");
	}

	public void typeLastName(String lastName) {
		clearAndSendKeys(findElement("//input[@id='lastName']"), lastName);
	}

	public void typeFirstName(String firstName) {
		clearAndSendKeys(findElement("//input[@id='firstName']"), firstName);
	}

	public void typeEmail(String email) {
		clearAndSendKeys(findElement("//input[@id='email']"), email);
	}
}
