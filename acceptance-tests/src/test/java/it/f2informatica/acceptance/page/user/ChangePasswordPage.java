package it.f2informatica.acceptance.page.user;

import it.f2informatica.acceptance.page.Page;
import org.openqa.selenium.WebDriver;

public class ChangePasswordPage extends Page {

	public ChangePasswordPage(WebDriver driver, String baseUrl, String userId) {
		super(driver, baseUrl, "/user/changePassword/"+userId);
	}

	public void typeCurrentPassword(String currentPassword) {
		clearAndSendKeys(findElement("//input[@id='currentPassword']"), currentPassword);
	}

	public void typeNewPassword(String newPassword) {
		clearAndSendKeys(findElement("//input[@id='newPassword']"), newPassword);
	}

	public void confirmNewPassword(String confirmedPassword) {
		clearAndSendKeys(findElement("//input[@id='passwordConfirmed']"), confirmedPassword);
	}

	public void updatePassword() {
		click(findElement("//input[@id='changePasswordSubmit']"));
	}

}
