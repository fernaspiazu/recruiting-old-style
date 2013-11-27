package it.f2informatica.acceptance.context;

import it.f2informatica.mongodb.context.MongoDBApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Configuration
@Import({MongoDBApplicationContext.class})
@ComponentScan(basePackages = {"it.f2informatica.mongodb.repositories"})
public class AcceptanceTestContext {

	static {
		System.setProperty("mongodb.database.name", "recruiting-acceptance");
	}

	@Autowired
	private MongoTemplate mongoTemplate;

	@PostConstruct
	public void init() {
		System.out.println(
				"\n==========>> MongoDB host[" + getHost() + "]; " +
				"port[" + getPort() + "]; " +
				"database[" + getDatabaseName() + "]\n");
	}

	@PreDestroy
	public void dropDatabase() {
		System.out.println("==========>> Dropping DB[" + getDatabaseName() + "]\n");
		mongoTemplate.getDb().dropDatabase();
	}

	private String getHost() {
		return mongoTemplate.getDb().getMongo().getConnector().getAddress().getHost();
	}

	private int getPort() {
		return mongoTemplate.getDb().getMongo().getConnector().getAddress().getPort();
	}

	private String getDatabaseName() {
		return mongoTemplate.getDb().getName();
	}

}
