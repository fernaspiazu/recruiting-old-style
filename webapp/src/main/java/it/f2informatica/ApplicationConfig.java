package it.f2informatica;

import it.f2informatica.mongodb.MongoDBApplicationConfig;
import it.f2informatica.services.ServicesConfig;
import it.f2informatica.webapp.WebApplicationConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

@Configuration
@Import({
		MongoDBApplicationConfig.class,
		ServicesConfig.class,
		WebApplicationConfig.class
})
@ImportResource({
		"file:./src/main/webapp/WEB-INF/spring-config/repository-populator-config.xml",
		"file:./src/main/webapp/WEB-INF/spring-config/security-config.xml"
})
public class ApplicationConfig {
}
