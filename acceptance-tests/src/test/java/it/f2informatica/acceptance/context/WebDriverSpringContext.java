package it.f2informatica.acceptance.context;

import it.f2informatica.acceptance.driver.WebDriverFacade;
import org.openqa.selenium.WebDriver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebDriverSpringContext {

	@Bean
	public WebDriver phantomjsDriver() throws Exception {
		return WebDriverFacade.getPhantomJSDriverFactory().create();
	}

}
