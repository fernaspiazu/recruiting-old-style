package it.f2informatica.test.services.config;

import it.f2informatica.services.ServicesConfig;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class ServicesConfigTest {

	static ServicesConfig servicesConfig;

	@BeforeClass
	public static void beforeClass() {
		servicesConfig = new ServicesConfig();
	}

	@Test
	public void test() {
		assertThat(servicesConfig).isNotNull();
	}

}
