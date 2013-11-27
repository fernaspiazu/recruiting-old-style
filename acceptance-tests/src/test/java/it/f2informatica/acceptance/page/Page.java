package it.f2informatica.acceptance.page;

import it.f2informatica.acceptance.predicates.HasPageBeenLoaded;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
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
		System.out.println("----------------- Loading URL: [" + this.url + "]");
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(new HasPageBeenLoaded(url));
	}

	protected void click(WebElement element) {
		element.click();
	}

	protected void submit(WebElement element) {
		element.submit();
	}

	protected WebElement findElement(String xpath) {
		return driver.findElement(By.xpath(xpath));
	}

	protected WebElement findElement(WebElement element, String xpath) {
		return element.findElement(By.xpath(xpath));
	}

	protected String getValue(WebElement element) {
		return element.getAttribute("value");
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

	protected void select(WebElement element, String value) {
		new Select(element).selectByVisibleText(value);
	}

	protected void selectByValue(WebElement element, String value) {
		new Select(element).selectByValue(value);
	}

}
