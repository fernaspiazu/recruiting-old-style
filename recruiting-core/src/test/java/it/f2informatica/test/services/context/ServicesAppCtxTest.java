package it.f2informatica.test.services.context;

import it.f2informatica.core.ServicesApplicationContext;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class ServicesAppCtxTest {

	@Test
	public void test() {
		ServicesApplicationContext servicesApplicationContext = new ServicesApplicationContext();
		assertThat(servicesApplicationContext).isNotNull();
	}

}
