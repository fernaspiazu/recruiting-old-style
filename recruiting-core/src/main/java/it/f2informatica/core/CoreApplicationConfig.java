package it.f2informatica.core;

import it.f2informatica.mongodb.MongoDBApplicationConfig;
import it.f2informatica.mysql.MySQLApplicationConfig;
import it.f2informatica.pagination.PaginationConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages = {"it.f2informatica.core"})
@EnableAspectJAutoProxy
@Import({PaginationConfig.class, MongoDBApplicationConfig.class, MySQLApplicationConfig.class})
public class CoreApplicationConfig {

}
