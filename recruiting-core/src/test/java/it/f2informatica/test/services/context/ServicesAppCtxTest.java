package it.f2informatica.test.services.context;

import it.f2informatica.core.CoreApplicationConfig;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class ServicesAppCtxTest {

  @Test
  public void test() {
    CoreApplicationConfig coreApplicationConfig = new CoreApplicationConfig();
    assertThat(coreApplicationConfig).isNotNull();
  }

}
