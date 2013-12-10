/*
package it.f2inf.accept.selenium;

import org.openqa.selenium.*;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.os.CommandLine;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.phantomjs.PhantomJSDriverService.Builder;

public class SeleniumTest {

	private static WebDriver driver;

	public static void main(String[] args) throws Exception {
		driver = phantomjsDriver();
		login();
		driver.get("http://localhost:8080/recruiting/user");
		List<WebElement> elements = driver.findElements(By.xpath("//table[@id='user-list']//tr[td='fumandito']/td[6]/a[contains(@href, '/user/edit')]"));
		System.out.println("Celle in username column: " + elements.size());
		for (WebElement element : elements) {
			String href = element.getAttribute("href");
			System.out.println(href);
			String[] hrefSplit = href.split("/");
			System.out.println(hrefSplit[hrefSplit.length-1]);
		}
		driver.quit();
	}

	private static void login() {
		driver.get("http://localhost:8080/recruiting/login");
		driver.findElement(By.id("usernameId")).sendKeys("admin");
		driver.findElement(By.id("passwordId")).sendKeys("admin");
		driver.findElement(By.id("loginButton")).click();
	}

	private static WebDriver phantomjsDriver() throws Exception {
		WebDriver driver = new PhantomJSDriver(phantomjsDriverBuilder().build(), capabilities());
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		driver.manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);
		driver.manage().window().setSize(new Dimension(1024, 768));
		return driver;
	}

	private static DesiredCapabilities capabilities() {
		DesiredCapabilities capabilities = DesiredCapabilities.phantomjs();
		capabilities.setJavascriptEnabled(true);
		capabilities.setCapability(CapabilityType.VERSION, "1.0.4");
		capabilities.setCapability(CapabilityType.TAKES_SCREENSHOT, true);
		capabilities.setCapability(CapabilityType.SUPPORTS_LOCATION_CONTEXT, true);
		capabilities.setCapability(CapabilityType.PLATFORM, Platform.ANY);
		return capabilities;
	}

	@Deprecated
	private static Builder phantomjsDriverBuilder() {
		return new Builder()
				.withLogFile(new File("target/log/phantomjs.log"))
				.usingCommandLineArguments(new String[]{"--webdriver-loglevel=ERROR"})
				.usingPhantomJSExecutable(new File(CommandLine.find("phantomjs")));
	}

}
*/
