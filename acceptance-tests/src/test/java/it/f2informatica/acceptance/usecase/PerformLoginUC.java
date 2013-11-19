package it.f2informatica.acceptance.usecase;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import static org.fest.assertions.Assertions.assertThat;

public class PerformLoginUC {

	@Test
	public void loginAsAdmin() {
		WebDriver driver = new HtmlUnitDriver();
		driver.get("http://localhost:8081/recruiting/");

		WebElement txtUsername = driver.findElement(By.id("usernameId"));
		WebElement txtPassword = driver.findElement(By.id("passwordId"));

		txtUsername.sendKeys("admin");
		txtPassword.sendKeys("admin");

		driver.findElement(By.id("submit")).submit();

		String currentUrl = driver.getCurrentUrl();
		assertThat("http://localhost:8081/recruiting/home").isEqualTo(currentUrl);
	}

}
