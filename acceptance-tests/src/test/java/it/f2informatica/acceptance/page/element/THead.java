package it.f2informatica.acceptance.page.element;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class THead {
	private WebElement thead;

	public THead(WebElement table) {
		this.thead = table.findElement(By.tagName("thead"));
	}

	public List<WebElement> rows() {
		return this.thead.findElements(By.tagName("tr"));
	}
}
