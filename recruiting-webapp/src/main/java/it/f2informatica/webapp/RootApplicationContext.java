package it.f2informatica.webapp;

import it.f2informatica.mongodb.MongoDBApplicationContext;
import it.f2informatica.mysql.MySQLApplicationContext;
import it.f2informatica.services.ServicesApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

@Configuration
@Import({
	MongoDBApplicationContext.class,
  MySQLApplicationContext.class,
	ServicesApplicationContext.class,
	WebAppContext.class
})
@ImportResource({"classpath:spring-config/security-config.xml"})
public class RootApplicationContext {

}
