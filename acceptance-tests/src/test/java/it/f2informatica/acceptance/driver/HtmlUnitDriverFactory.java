package it.f2informatica.acceptance.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import java.util.concurrent.TimeUnit;

public class HtmlUnitDriverFactory implements WebDriverFactory {

	@Override
	public WebDriver create() throws Exception {
		HtmlUnitDriver htmlUnitDriver = new HtmlUnitDriver();
		htmlUnitDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		return htmlUnitDriver;
	}

}
