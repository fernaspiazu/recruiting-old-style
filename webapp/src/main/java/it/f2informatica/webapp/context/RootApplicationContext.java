package it.f2informatica.webapp.context;

import it.f2informatica.mongodb.context.MongoDBApplicationContext;
import it.f2informatica.services.context.ServicesApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

@Configuration
@Import({
		MongoDBApplicationContext.class,
		ServicesApplicationContext.class,
		WebApplicationContext.class
})
@ImportResource({"classpath:spring-config/security-config.xml"})
public class RootApplicationContext {
}
