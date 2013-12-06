package it.f2informatica.acceptance.driver;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.os.CommandLine;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.phantomjs.PhantomJSDriverService.Builder;

public class PhantomJSDriverFactory implements WebDriverFactory {

	@Override
	public WebDriver create() throws Exception {
		WebDriver driver = new PhantomJSDriver(phantomjsDriverBuilder().build(), capabilities());
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		driver.manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);
		driver.manage().window().setSize(new Dimension(1024, 768));
		return driver;
	}

	private DesiredCapabilities capabilities() {
		DesiredCapabilities capabilities = DesiredCapabilities.phantomjs();
		capabilities.setJavascriptEnabled(true);
		capabilities.setCapability(CapabilityType.VERSION, "1.0.4");
		capabilities.setCapability(CapabilityType.TAKES_SCREENSHOT, true);
		capabilities.setCapability(CapabilityType.SUPPORTS_LOCATION_CONTEXT, true);
		capabilities.setCapability(CapabilityType.PLATFORM, Platform.ANY);
		return capabilities;
	}

	@Deprecated
	private Builder phantomjsDriverBuilder() {
		return new Builder()
			.withLogFile(new File("target/log/phantomjs.log"))
			.usingCommandLineArguments(new String[]{"--webdriver-loglevel=ERROR"})
			.usingPhantomJSExecutable(new File(CommandLine.find("phantomjs")));
	}

}
