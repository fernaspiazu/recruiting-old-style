package it.f2informatica.mongodb.config;

import com.mongodb.Mongo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.util.StringUtils;

import java.net.UnknownHostException;

@Configuration
@ImportResource("classpath:repository-populator-config.xml")
@EnableMongoRepositories(basePackages = {"it.f2informatica.mongodb.repositories"})
public class MongoDBApplicationConfig extends AbstractMongoConfiguration {
	private static final String DEFAULT_HOST = "localhost";
	private static final int DEFAULT_PORT = 27017;

	private static final String DEFAULT_PRODUCTION_DATABASE = "recruiting";
	private static final String ACCEPTANCE_DATABASE = System.getProperty("mongodb.database.name");

	@Override
	protected String getDatabaseName() {
		return StringUtils.hasText(ACCEPTANCE_DATABASE) ? ACCEPTANCE_DATABASE : DEFAULT_PRODUCTION_DATABASE;
	}

	@Bean
	@Override
	public Mongo mongo() throws UnknownHostException {
		Mongo mongo = new Mongo(DEFAULT_HOST, DEFAULT_PORT);
		mongo.getMongoOptions().setConnectionsPerHost(8);
		mongo.getMongoOptions().setThreadsAllowedToBlockForConnectionMultiplier(4);
		mongo.getMongoOptions().setConnectTimeout(1000);
		mongo.getMongoOptions().setMaxWaitTime(1500);
		mongo.getMongoOptions().setAutoConnectRetry(true);
		mongo.getMongoOptions().setSocketKeepAlive(true);
		mongo.getMongoOptions().setSocketTimeout(1500);
		return mongo;
	}

}