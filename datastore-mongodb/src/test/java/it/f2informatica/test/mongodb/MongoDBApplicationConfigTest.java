package it.f2informatica.test.mongodb;

import com.mongodb.Mongo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = {"it.f2informatica.mongodb.repositories"})
public class MongoDBApplicationConfigTest extends AbstractMongoConfiguration {

	@Override
	protected String getDatabaseName() {
		return "recruiting_test";
	}

	@Override
	public Mongo mongo() throws Exception {
		return new Mongo("localhost", 27017);
	}

	@Bean(name = "mongoTemplateTest")
	public MongoTemplate mongoTemplateTest() throws Exception {
		return new MongoTemplate(mongo(), "recruiting_test");
	}

}
