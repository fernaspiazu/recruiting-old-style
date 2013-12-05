package it.f2informatica.acceptance.usecase;

import it.f2informatica.acceptance.context.AcceptanceTestContext;
import it.f2informatica.acceptance.driver.WebDriverFacade;
import it.f2informatica.acceptance.driver.WebDriverFactory;
import it.f2informatica.acceptance.page.Navigator;
import it.f2informatica.mongodb.repositories.ConsultantRepository;
import it.f2informatica.mongodb.repositories.RoleRepository;
import it.f2informatica.mongodb.repositories.UserRepository;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.IOException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AcceptanceTestContext.class})
public abstract class UseCaseTest {

	protected WebDriverFactory driverFactory;

	protected Navigator navigator;

	protected WebDriver driver;

	@Autowired
	protected MongoTemplate mongoTemplate;

	@Autowired
	protected UserRepository userRepository;

	@Autowired
	protected RoleRepository roleRepository;

	@Autowired
	protected ConsultantRepository consultantRepository;

	public UseCaseTest() {
		//this.driverFactory = WebDriverFacade.getPhantomJSDriverFactory();
		this.driverFactory = WebDriverFacade.getHtmlUnitDriverFactory();
	}

	@Before
	public void initializeDriver() throws Exception {
		driver = driverFactory.create();
		navigator = new Navigator();
		navigator.setDriver(driver);
	}

	@After
	public void quitBrowser() {
		driver.quit();
	}

	protected void takeScreenshot(String filename) throws IOException {
		File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(screenshot, new File("/tmp/" + filename));
	}

}
