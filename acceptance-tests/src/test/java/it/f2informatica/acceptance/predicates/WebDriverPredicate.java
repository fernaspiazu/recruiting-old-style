package it.f2informatica.acceptance.predicates;

import com.google.common.base.Predicate;
import org.openqa.selenium.WebDriver;

public abstract class WebDriverPredicate<T> implements Predicate<WebDriver> {

	protected T parameter;

	public WebDriverPredicate(T parameter) {
		this.parameter = parameter;
	}

}
