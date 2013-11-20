package it.f2informatica.acceptance.driver;

public class WebDriverFacade {

	public static WebDriverFactory getFirefoxDriverFactory() {
		return new FirefoxDriverFactory();
	}

	public static WebDriverFactory getHtmlUnitDriverFactory() {
		return new HtmlUnitDriverFactory();
	}

}
