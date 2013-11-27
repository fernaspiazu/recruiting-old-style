package it.f2informatica.services.context;

import it.f2informatica.mongodb.context.MongoDBApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({MongoDBApplicationContext.class})
@ComponentScan(basePackages = "it.f2informatica.services")
public class ServicesApplicationContext {

}
