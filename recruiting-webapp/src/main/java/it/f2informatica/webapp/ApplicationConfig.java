package it.f2informatica.webapp;

import it.f2informatica.core.CoreApplicationConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

@Configuration
@Import({
  CoreApplicationConfig.class,
  WebApplicationConfig.class
})
@ImportResource({"classpath:spring-config/security-config.xml"})
public class ApplicationConfig {

}
