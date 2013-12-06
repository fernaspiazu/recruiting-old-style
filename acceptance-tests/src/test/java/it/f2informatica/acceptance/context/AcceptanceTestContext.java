package it.f2informatica.acceptance.context;

import it.f2informatica.mongodb.context.MongoDBApplicationContext;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Configuration
@Import({MongoDBApplicationContext.class, WebDriverSpringContext.class})
@ComponentScan(basePackages = {"it.f2informatica.mongodb.repositories"})
public class AcceptanceTestContext {

	static {
		System.setProperty("mongodb.database.name", "recruiting-acceptance");
	}

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private WebDriver phantomjsDriver;

	@PostConstruct
	public void init() {
		printDBInitInformation();
	}

	@PreDestroy
	public void dropDatabase() {
		printDBInfoToDestroy();
		dropAcceptanceDatabase();
		quitDriver();
	}

	private void dropAcceptanceDatabase() {
		mongoTemplate.getDb().dropDatabase();
	}

	private void quitDriver() {
		phantomjsDriver.close();
		phantomjsDriver.quit();
	}

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
