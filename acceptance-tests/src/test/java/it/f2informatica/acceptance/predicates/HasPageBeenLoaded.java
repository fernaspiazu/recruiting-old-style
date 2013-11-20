package it.f2informatica.acceptance.predicates;

import org.openqa.selenium.WebDriver;

public class HasPageBeenLoaded extends WebDriverPredicate<String> {

	public HasPageBeenLoaded(String url) {
		super(url);
	}

	@Override
	public boolean apply(WebDriver driver) {
		return driver.getCurrentUrl().contains(parameter);
	}

}
