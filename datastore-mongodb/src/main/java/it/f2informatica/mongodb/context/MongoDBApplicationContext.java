package it.f2informatica.mongodb.context;

import com.mongodb.Mongo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.util.StringUtils;

import java.net.UnknownHostException;

@Configuration
@ImportResource("classpath:spring/repository-populator-config.xml")
@EnableMongoRepositories(basePackages = {"it.f2informatica.mongodb.repositories"})
@PropertySource("classpath:mongodb.properties")
public class MongoDBApplicationContext extends AbstractMongoConfiguration {
	private static final String OTHER_DATABASE = System.getProperty("mongodb.database.name");

	@Value("${mongodb.host}")
	private String host;

	@Value("${mongodb.port}")
	private String defaultPort;

	@Value("${mongodb.database}")
	private String database;

	@Override
	protected String getDatabaseName() {
		return StringUtils.hasText(OTHER_DATABASE) ? OTHER_DATABASE : database;
	}

	@Bean
	@Override
	public Mongo mongo() throws UnknownHostException {
		Mongo mongo = new Mongo(host, Integer.parseInt(defaultPort));
		mongo.getMongoOptions().setConnectionsPerHost(10);
		mongo.getMongoOptions().setThreadsAllowedToBlockForConnectionMultiplier(4);
		mongo.getMongoOptions().setConnectTimeout(5000);
		mongo.getMongoOptions().setMaxWaitTime(3000);
		mongo.getMongoOptions().setAutoConnectRetry(true);
		mongo.getMongoOptions().setSocketKeepAlive(true);
		mongo.getMongoOptions().setSocketTimeout(3000);
		return mongo;
	}

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}

}