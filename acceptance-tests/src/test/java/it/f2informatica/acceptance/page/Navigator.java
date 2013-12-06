package it.f2informatica.acceptance.page;

import it.f2informatica.acceptance.page.consultant.ConsultantManagementPage;
import it.f2informatica.acceptance.page.consultant.ProfileRegistrationPage;
import it.f2informatica.acceptance.page.login.DefaultLoginPage;
import it.f2informatica.acceptance.page.login.LoginPage;
import it.f2informatica.acceptance.page.user.UserDetailsPage;
import it.f2informatica.acceptance.page.user.UserManagementPage;
import org.openqa.selenium.WebDriver;

public class Navigator {
	private static final String DEFAULT_BASE_URL = "http://localhost:8082/recruiting";

	private String baseUrl;
	private WebDriver driver;

	public Navigator() {
		this.baseUrl = DEFAULT_BASE_URL;
	}

	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public WebDriver getDriver() {
		return driver;
	}

	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}

	public void logOut() {
		driver.get(baseUrl + "/logout");
		driver.manage().deleteAllCookies();
	}

	public LoginPage goToLoginPage() {
		driver.get(baseUrl + "/login");
		return new DefaultLoginPage(driver, baseUrl);
	}

	public HomePage goToHomePage() {
		driver.get(baseUrl + "/home");
		return new HomePage(driver, baseUrl);
	}

	public UserManagementPage goToUserManagementPage() {
		driver.get(baseUrl + "/user/loadUsers");
		return new UserManagementPage(driver, baseUrl);
	}

	public UserDetailsPage goToUserDetailsPage(String userId) {
		String path = "/user/findUser/" + userId;
		driver.get(baseUrl + path);
		return new UserDetailsPage(driver, baseUrl, userId);
	}

	public ConsultantManagementPage goToConsultantManagementPage() {
		driver.get(baseUrl + "/consultant");
		return new ConsultantManagementPage(driver, baseUrl);
	}

	public ProfileRegistrationPage goToProfileRegistrationPage(String consultantId) {
		String path = "/consultant/" + consultantId + "/register-profile";
		driver.get(baseUrl + path);
		return new ProfileRegistrationPage(driver, baseUrl, path);
	}
}
