package it.f2informatica.acceptance.usecase;

import it.f2informatica.acceptance.page.HomePage;
import it.f2informatica.acceptance.page.login.LoginPage;
import it.f2informatica.acceptance.page.user.ChangePasswordPage;
import it.f2informatica.acceptance.page.user.CreateNewUserPage;
import it.f2informatica.acceptance.page.user.UserManagementPage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class UC_03_PasswordModification extends UseCaseTest {

	@Before
	public void init() {
		login();
	}

	@After
	public void teardown() {
		logout();
	}

	@Test
	public void updatePassword() {
		UserManagementPage userPage = navigator.goToUserManagementPage();
		CreateNewUserPage newUserPage = userPage.createNewUser();
		newUserPage.typeNewUsername("zoe.paterson");
		newUserPage.typeNewPassword("zoe.paterson");
		newUserPage.selectNormalUserRole();
		newUserPage.typeLastName("Paterson");
		newUserPage.typeFirstName("Zoe");
		newUserPage.typeEmail("zoe.paterson@risecomp.com");
		UserManagementPage userPage_2 = newUserPage.clickOnSaveUserButton();
		ChangePasswordPage changePasswordPage = userPage_2.updatePasswordWithUsername("zoe.paterson");
		changePasswordPage.typeCurrentPassword("zoe.paterson");
		changePasswordPage.typeNewPassword("new.password");
		changePasswordPage.confirmNewPassword("new.password");
		changePasswordPage.updatePassword();
		logout();
		LoginPage loginPage = navigator.goToLoginPage();
		loginPage.typeUsername("zoe.paterson");
		loginPage.typePassword("new.password");
		HomePage homePage = loginPage.clickOnLoginSuccessButton();
		assertThat(homePage.getUserLoggedIn()).isEqualTo("zoe.paterson");
	}

}
