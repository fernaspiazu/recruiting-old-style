package it.f2informatica.acceptance.usecase;

import it.f2informatica.acceptance.page.HomePage;
import it.f2informatica.acceptance.page.LoginPage;
import org.junit.After;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class PerformLoginUC extends UseCaseTest {

	@After
	public void logout() {
		navigator.logOut();
	}

	@Test
	public void loginAsAdmin() {
		LoginPage loginPage = navigator.goToLoginPage();
		loginPage.typeUsername("admin");
		loginPage.typePassword("admin");
		HomePage homePage = loginPage.clickOnLoginButton();
		assertThat("admin").isEqualTo(homePage.getUserLoggedIn());
	}

	@Test
	public void loginExpectingFailure() {
		LoginPage loginPage = navigator.goToLoginPage();
		loginPage.typeUsername("unknown_user");
		loginPage.typePassword("unknown_password");
		LoginPage loginPageAfterLoginFailure = loginPage.clickOnLoginButtonExpectingFailure();
		assertThat(loginPageAfterLoginFailure.isLoginErrorMessagePresent()).isTrue();
	}

}
