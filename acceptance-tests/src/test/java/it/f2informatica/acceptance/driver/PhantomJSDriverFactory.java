package it.f2informatica.acceptance.driver;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.os.CommandLine;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.phantomjs.PhantomJSDriverService.Builder;

public class PhantomJSDriverFactory implements WebDriverFactory {

	@Override
	public WebDriver create() throws Exception {
		WebDriver driver = new PhantomJSDriver(phantomjsBuilder().build(), capabilities());
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		driver.manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);
		driver.manage().window().setSize(new Dimension(1024, 768));
		return driver;
	}

	private DesiredCapabilities capabilities() {
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setJavascriptEnabled(true);
		capabilities.setCapability("takesScreenshot", false);
		return capabilities;
	}

	private Builder phantomjsBuilder() {
		return new Builder()
				.withLogFile(new File("./target/log/phantomjs.log"))
				.usingCommandLineArguments(new String[] {"--webdriver-loglevel=ERROR"})
				.usingPhantomJSExecutable(new File(CommandLine.find("phantomjs")));
	}

}
