package it.f2informatica.webapp.test.config;

import it.f2informatica.webapp.config.ApplicationConfig;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.fest.assertions.Assertions.*;

public class ApplicationConfigTest {

	static ApplicationConfig applicationConfig;

	@BeforeClass
	public static void beforeClass() {
		applicationConfig = new ApplicationConfig();
	}

	@Test
	public void test() {
		assertThat(applicationConfig).isNotNull();
	}

}
