package it.f2informatica.acceptance.page;

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

	public LoginPage goToLoginPage() {
		driver.get(baseUrl);
		return new LoginPage(driver, baseUrl);
	}

	public HomePage goToHomePage() {
		driver.get(baseUrl + "/home");
		return new HomePage(driver, baseUrl);
	}

	public void logOut() {
		driver.get(baseUrl + "/logout");
	}

}
