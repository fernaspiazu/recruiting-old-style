package it.f2informatica.acceptance.context;

import com.mongodb.ServerAddress;
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
		ServerAddress mongoAddress = mongoTemplate.getDb().getMongo().getConnector().getAddress();
		String host = mongoAddress.getHost();
		int port = mongoAddress.getPort();
		String database = mongoTemplate.getDb().getName();
		System.out.println("MongoDB host[" + host + "]; port[" + port + "]; database[" + database + "]");
	}

	@PreDestroy
	public void dropDatabase() {
		String database = mongoTemplate.getDb().getName();
		System.out.println("Dropping DB[" + database + "]");
		mongoTemplate.getDb().dropDatabase();
	}

}
