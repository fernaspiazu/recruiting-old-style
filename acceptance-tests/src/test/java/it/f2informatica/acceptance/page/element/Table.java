package it.f2informatica.acceptance.page.element;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Table {
	private WebElement table;

	public Table(WebDriver driver, String xpath) {
		this(driver, By.xpath(xpath));
	}

	public Table(WebDriver driver, By by) {
		table = driver.findElement(by);
	}

	public THead thead() {
		return new THead(this.table);
	}

	public TBody tbody() {
		return new TBody(this.table);
	}
}