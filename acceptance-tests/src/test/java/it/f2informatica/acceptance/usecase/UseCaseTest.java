package it.f2informatica.acceptance.usecase;

import it.f2informatica.acceptance.context.AcceptanceTestContext;
import it.f2informatica.acceptance.page.Navigator;
import it.f2informatica.acceptance.page.login.LoginPage;
import it.f2informatica.acceptance.predicates.HasPageBeenLoaded;
import it.f2informatica.mongodb.repositories.ConsultantRepository;
import it.f2informatica.mongodb.repositories.RoleRepository;
import it.f2informatica.mongodb.repositories.UserRepository;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AcceptanceTestContext.class})
public abstract class UseCaseTest {

	protected Navigator navigator;

	@Autowired
	protected WebDriver driver;

	@Autowired
	protected MongoTemplate mongoTemplate;

	@Autowired
	protected UserRepository userRepository;

	@Autowired
	protected RoleRepository roleRepository;

	@Autowired
	protected ConsultantRepository consultantRepository;

	@Before
	public void initializeDriver() throws Exception {
		navigator = new Navigator();
		navigator.setDriver(driver);
	}

	protected void login() {
		LoginPage loginPage = navigator.goToLoginPage();
		loginPage.typeUsername("admin");
		loginPage.typePassword("admin");
		loginPage.clickOnLoginSuccessButton();
	}

	protected void logout() {
		navigator.logOut();
	}

	protected void waitThirtySeconds() {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(new HasPageBeenLoaded(driver.getCurrentUrl()));
	}

}
