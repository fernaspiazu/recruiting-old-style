package it.f2informatica.acceptance.page.user;

import com.google.common.collect.Lists;
import it.f2informatica.acceptance.page.Page;
import it.f2informatica.acceptance.page.element.Table;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class UserManagementPage extends Page {
	private static final String CREATE_USER_LINK_XPATH = "//a[contains(@href, '/user/createNewUser')]";
	private static final String USERS_TABLE_XPATH = "//table[@id='user-list']";

	public UserManagementPage(WebDriver driver, String baseUrl) {
		super(driver, baseUrl, "/user/loadUsers");
	}

	public CreateNewUserPage clickOnCreateNewUserButton() {
		click(findElement(CREATE_USER_LINK_XPATH));
		return new CreateNewUserPage(driver, baseUrl);
	}

	public String[] tableContainingUsers() {
		List<String> usernames = Lists.newArrayList();
		List<WebElement> rows = new Table(driver, USERS_TABLE_XPATH).tbody().rows();
		for (WebElement row : rows) {
			WebElement usernameColumn = row.findElements(By.tagName("td")).get(0);
			usernames.add(usernameColumn.getText());
		}
		return usernames.toArray(new String[usernames.size()]);
	}

}
