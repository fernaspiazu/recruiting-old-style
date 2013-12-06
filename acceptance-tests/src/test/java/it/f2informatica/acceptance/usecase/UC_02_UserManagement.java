package it.f2informatica.acceptance.usecase;

import it.f2informatica.acceptance.page.login.LoginPage;
import it.f2informatica.acceptance.page.user.CreateNewUserPage;
import it.f2informatica.acceptance.page.user.UserDetailsPage;
import it.f2informatica.acceptance.page.user.UserEditPage;
import it.f2informatica.acceptance.page.user.UserManagementPage;
import it.f2informatica.mongodb.domain.User;
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
		UserManagementPage userManagementPage = navigator.goToUserManagementPage();
		CreateNewUserPage createNewUserPage = userManagementPage.clickOnCreateNewUserButton();
		createNewUserPage.typeNewUsername("new_username");
		createNewUserPage.typeNewPassword("new_password");
		createNewUserPage.selectNormalUserRole();
		UserManagementPage userMngtAfterSave = createNewUserPage.clickOnSaveUserButton();
		assertThat("new_username").isIn(userMngtAfterSave.tableContainingUsers());
	}

	@Test
	public void updateUser() {
		String userId = createUser("user_to_modify");
		UserDetailsPage userDetailsPage = navigator.goToUserDetailsPage(userId);
		assertThat("user_to_modify").isEqualTo(userDetailsPage.getUsername());
		UserEditPage userEditPage = userDetailsPage.clickOnEditUserButton();
		userEditPage.typeNewUsername("username_updated");
		UserManagementPage userManagementPageAfterUpdate = userEditPage.clickOnUpdateUserButton();
		assertThat("username_updated").isIn(userManagementPageAfterUpdate.tableContainingUsers());
	}

	@Test
	public void deleteUser() {
		String userId = createUser("user_to_delete");
		UserDetailsPage userDetailsPage = navigator.goToUserDetailsPage(userId);
		assertThat("user_to_delete").isEqualTo(userDetailsPage.getUsername());
		UserManagementPage userManagementPageAfterDeletion = userDetailsPage.clickOnDeleteUserButton();
		assertThat("user_to_delete").isNotIn(userManagementPageAfterDeletion.tableContainingUsers());
	}

	private String createUser(String username) {
		User user = new User();
		user.setUsername(username);
		user.setPassword("pwd_update");
		user.setRole(roleRepository.findByName("User"));
		User saved = userRepository.save(user);
		return String.valueOf(saved.getId());
	}

}
