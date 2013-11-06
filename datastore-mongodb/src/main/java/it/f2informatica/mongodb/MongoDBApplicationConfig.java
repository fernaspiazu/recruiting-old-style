package it.f2informatica.mongodb;

import com.mongodb.Mongo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.net.UnknownHostException;

@Configuration
@EnableMongoRepositories(basePackages = {"it.f2informatica.mongodb.repositories"})
public class MongoDBApplicationConfig extends AbstractMongoConfiguration {
	private static final String DEFAULT_HOST = "localhost";
	private static final int DEFAULT_PORT = 27017;

	@Override
	protected String getDatabaseName() {
		return "recruiting";
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