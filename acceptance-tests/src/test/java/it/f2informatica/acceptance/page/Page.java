package it.f2informatica.acceptance.page;

import it.f2informatica.acceptance.predicates.HasPageBeenLoaded;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Page {

	protected WebDriver driver;
	protected String baseUrl;
	protected String path;
	protected String url;

	public Page(WebDriver driver, String baseUrl, String path) {
		this.driver = driver;
		this.baseUrl = baseUrl;
		this.path = path;
		this.url = baseUrl + path;
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(new HasPageBeenLoaded(url));
	}

	protected void click(WebElement element) {
		element.click();
	}

	protected WebElement findElement(String xpath) {
		return driver.findElement(By.xpath(xpath));
	}

	protected void clearAndSendKeys(WebElement element, String input) {
		this.clear(element);
		this.sendKeys(element, input);
	}

	protected void clear(WebElement element) {
		element.clear();
	}

	protected void sendKeys(WebElement element, String input) {
		element.sendKeys(input);
	}

}
