package it.f2informatica.acceptance.page.element;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class TBody {
	private WebElement thead;

	public TBody(WebElement table) {
		this.thead = table.findElement(By.tagName("tbody"));
	}

	public List<WebElement> rows() {
		return this.thead.findElements(By.tagName("tr"));
	}
}
