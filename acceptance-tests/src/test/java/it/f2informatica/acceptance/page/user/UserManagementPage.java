package it.f2informatica.acceptance.page.user;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import it.f2informatica.acceptance.page.Page;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class UserManagementPage extends Page {
	private static final String CREATE_USER_LINK_XPATH = "//a[contains(@href, '/user/new')]";
	private static final String USERS_TABLE_USERNAME_COLUMN_XPATH = "//table[@id='user-list']//tr/td[1]";

	public UserManagementPage(WebDriver driver, String baseUrl) {
		super(driver, baseUrl, "/user");
	}

	public CreateNewUserPage createNewUser() {
		click(findElement(CREATE_USER_LINK_XPATH));
		return new CreateNewUserPage(driver, baseUrl);
	}

	public UserEditPage editUserWithUsername(String username) {
		String editUserLinkXpath = "//table[@id='user-list']//tr[td='"+username+"']/td[6]/a[contains(@href, '/user/edit')]";
		WebElement editUserLink = findElement(editUserLinkXpath);
		String userId = resolveUserIdFromHrefAttribute(editUserLink);
		click(editUserLink);
		return new UserEditPage(driver, baseUrl, userId);
	}

	private String resolveUserIdFromHrefAttribute(WebElement editUserLink) {
		String[] hrefSplit = editUserLink.getAttribute("href").split("/");
		return hrefSplit[hrefSplit.length-1];
	}

	public UserManagementPage deleteUserWithUsername(String username) {
		String deleteUserLinkXpath = "//table[@id='user-list']//tr[td='"+username+"']/td[8]/a[contains(@href, '/user/delete')]";
		WebElement deleteUserLink = findElement(deleteUserLinkXpath);
		click(deleteUserLink);
		return new UserManagementPage(driver, baseUrl);
	}

	public String[] usernames() {
		List<WebElement> usernameColumn = findElements(USERS_TABLE_USERNAME_COLUMN_XPATH);
		List<String> usernameList = Lists.newArrayList(Iterables.transform(usernameColumn,
			new Function<WebElement, String>() {
				@Override
				public String apply(WebElement input) {
					return input.getText();
				}
			}
		));
		return usernameList.toArray(new String[usernameList.size()]);
	}
}
