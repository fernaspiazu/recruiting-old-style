package it.f2informatica.webapp.test.context;

import it.f2informatica.webapp.context.RootApplicationContext;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class RootAppCtxTest {

	@Test
	public void test() {
		RootApplicationContext rootApplicationContext = new RootApplicationContext();
		assertThat(rootApplicationContext).isNotNull();
	}

}
