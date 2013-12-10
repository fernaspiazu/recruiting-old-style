package it.f2informatica.acceptance.usecase;

import it.f2informatica.acceptance.page.login.LoginPage;
import it.f2informatica.acceptance.page.user.CreateNewUserPage;
import it.f2informatica.acceptance.page.user.UserEditPage;
import it.f2informatica.acceptance.page.user.UserManagementPage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class UC_02_UserManagement extends UseCaseTest {

	@Before
	public void login() {
		LoginPage loginPage = navigator.goToLoginPage();
		loginPage.typeUsername("admin");
		loginPage.typePassword("admin");
		loginPage.clickOnLoginSuccessButton();
	}

	@After
	public void logout() {
		navigator.logOut();
	}

	@Test
	public void newUserCreation() {
		UserManagementPage userPage = navigator.goToUserManagementPage();
		CreateNewUserPage newUserPage = userPage.createNewUser();
		newUserPage.typeNewUsername("cameron.robertson");
		newUserPage.typeNewPassword("cameron.robertson");
		newUserPage.selectNormalUserRole();
		newUserPage.typeLastName("Robertson");
		newUserPage.typeFirstName("Cameron");
		newUserPage.typeEmail("cameron.robertson@risecomp.com");
		UserManagementPage userMngtAfterSave = newUserPage.clickOnSaveUserButton();
		assertThat("cameron.robertson").isIn(userMngtAfterSave.usernames());
	}

	@Test
	public void updateUser() {
		UserManagementPage userPage = navigator.goToUserManagementPage();
		CreateNewUserPage newUserPage = userPage.createNewUser();
		newUserPage.typeNewUsername("jane.lambert");
		newUserPage.typeNewPassword("jane.lambert");
		newUserPage.selectNormalUserRole();
		newUserPage.typeLastName("Lambert");
		newUserPage.typeFirstName("Jane");
		newUserPage.typeEmail("jane.lambert@risecomp.com");
		UserManagementPage userPage_2 = newUserPage.clickOnSaveUserButton();
		UserEditPage userEditPage = userPage_2.editUserWithUsername("jane.lambert");
		userEditPage.typeNewUsername("jane.lambert.sharp");
		UserManagementPage userPage_3 = userEditPage.updateUser();
		assertThat("jane.lambert.sharp").isIn(userPage_3.usernames());
	}

	@Test
	public void deleteUser() {
		UserManagementPage userPage = navigator.goToUserManagementPage();
		CreateNewUserPage newUserPage = userPage.createNewUser();
		newUserPage.typeNewUsername("lillian.ellison");
		newUserPage.typeNewPassword("lillian.ellison");
		newUserPage.selectNormalUserRole();
		newUserPage.typeLastName("Ellison");
		newUserPage.typeFirstName("Lillian");
		newUserPage.typeEmail("lillian.ellison@risecomp.com");
		UserManagementPage userPage_2 = newUserPage.clickOnSaveUserButton();
		UserManagementPage userPage_3 = userPage_2.deleteUserWithUsername("lillian.ellison");
		assertThat("lillian.ellison").isNotIn(userPage_3.usernames());
	}

}
