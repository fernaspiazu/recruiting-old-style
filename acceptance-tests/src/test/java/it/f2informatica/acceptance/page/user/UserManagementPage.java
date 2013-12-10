package it.f2informatica.acceptance.page.user;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import it.f2informatica.acceptance.page.Page;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class UserManagementPage extends Page {

	public UserManagementPage(WebDriver driver, String baseUrl) {
		super(driver, baseUrl, "/user");
	}

	public CreateNewUserPage createNewUser() {
		String createNewUserXpath = "//a[contains(@href, '/user/new')]";
		click(findElement(createNewUserXpath));
		return new CreateNewUserPage(driver, baseUrl);
	}

	public UserEditPage editUserWithUsername(String username) {
		String editUserLinkXpath = buildXPathFromUserTable(username, "/user/edit", 6);
		WebElement editUserLink = findElement(editUserLinkXpath);
		String userId = resolveUserIdFromHrefAttribute(editUserLink);
		click(editUserLink);
		return new UserEditPage(driver, baseUrl, userId);
	}

	public ChangePasswordPage updatePasswordWithUsername(String username) {
		String chgPwdLinkXpath = buildXPathFromUserTable(username, "/user/changePassword", 7);
		WebElement chgPwdLink = findElement(chgPwdLinkXpath);
		String userId = resolveUserIdFromHrefAttribute(chgPwdLink);
		click(chgPwdLink);
		return new ChangePasswordPage(driver, baseUrl, userId);
	}

	private String resolveUserIdFromHrefAttribute(WebElement editUserLink) {
		String[] hrefSplit = editUserLink.getAttribute("href").split("/");
		return hrefSplit[hrefSplit.length-1];
	}

	public UserManagementPage deleteUserWithUsername(String username) {
		String deleteUserLinkXpath = buildXPathFromUserTable(username, "/user/delete", 8);
		WebElement deleteUserLink = findElement(deleteUserLinkXpath);
		click(deleteUserLink);
		return new UserManagementPage(driver, baseUrl);
	}

	private String buildXPathFromUserTable(String username, String url, int column) {
		return "//table[@id='user-list']//tr[td='"+username+"']/td["+column+"]/a[contains(@href, '"+url+"')]";
	}

	public String[] usernames() {
		String xpath = "//table[@id='user-list']//tr/td[1]";
		List<WebElement> usernameColumn = findElements(xpath);
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
