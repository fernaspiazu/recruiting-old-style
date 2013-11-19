package it.f2informatica.webapp.config;

import it.f2informatica.mongodb.config.MongoDBApplicationConfig;
import it.f2informatica.services.ServicesConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

@Configuration
@Import({
		MongoDBApplicationConfig.class,
		ServicesConfig.class,
		WebApplicationConfig.class
})
@ImportResource({"classpath:spring-config/security-config.xml"})
public class ApplicationConfig {
}
