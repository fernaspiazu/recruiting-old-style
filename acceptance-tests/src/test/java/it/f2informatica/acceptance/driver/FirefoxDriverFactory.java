package it.f2informatica.acceptance.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.concurrent.TimeUnit;

public class FirefoxDriverFactory implements WebDriverFactory {

	@Override
	public WebDriver create() throws Exception {
		FirefoxDriver firefoxDriver = new FirefoxDriver();
		firefoxDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		firefoxDriver.manage().window().maximize();
		return firefoxDriver;
	}

}
