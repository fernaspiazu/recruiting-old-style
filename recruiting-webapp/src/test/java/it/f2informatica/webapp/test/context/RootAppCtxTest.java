package it.f2informatica.webapp.test.context;

import it.f2informatica.webapp.ApplicationConfig;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class RootAppCtxTest {

  @Test
  public void test() {
    ApplicationConfig applicationConfig = new ApplicationConfig();
    assertThat(applicationConfig).isNotNull();
  }

}
