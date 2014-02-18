package it.f2informatica.test.mongodb;

import it.f2informatica.mongodb.MongoDBApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Configuration
@Import({MongoDBApplicationContext.class})
public class DatastoreApplicationContextTest {

	static {
		System.setProperty("mongodb.database.name", "recruiting-unit-test");
	}

	@PostConstruct
	public void init() {
		printDBInitInformation();
	}

	@PreDestroy
	public void dropDatabase() {
		printDBInfoToDestroy();
		dropAcceptanceDatabase();
	}

	private void dropAcceptanceDatabase() {
		mongoTemplate.getDb().dropDatabase();
	}

	@Autowired
	private MongoTemplate mongoTemplate;

	private void printDBInitInformation() {
		System.out.println(
			"\n================== "
			+ "MongoDB host[" + getHost() + "]; "
			+ "port[" + getPort() + "]; "
			+ "database[" + getDatabaseName() + "] "
			+ "==================\n");
	}

	private void printDBInfoToDestroy() {
		System.out.println(
			"================== "
			+ "Dropping DB[" + getDatabaseName() + "] "
			+ "==================\n");
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
