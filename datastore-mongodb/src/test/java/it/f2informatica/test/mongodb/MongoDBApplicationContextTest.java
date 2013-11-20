package it.f2informatica.test.mongodb;

import com.mongodb.Mongo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = {"it.f2informatica.mongodb.repositories"})
@PropertySource("classpath:mongodb-test.properties")
public class MongoDBApplicationContextTest extends AbstractMongoConfiguration {

	@Value("${mongodb.host}")
	private String host;

	@Value("${mongodb.port}")
	private String defaultPort;

	@Value("${mongodb.database}")
	private String database;

	@Override
	protected String getDatabaseName() {
		return database;
	}

	@Override
	public Mongo mongo() throws Exception {
		return new Mongo(host, Integer.parseInt(defaultPort));
	}

	@Bean(name = "mongoTemplateTest")
	public MongoTemplate mongoTemplateTest() throws Exception {
		return new MongoTemplate(mongo(), database);
	}

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}

}
