package it.f2informatica.test.mongodb.config;

import com.mongodb.Mongo;
import it.f2informatica.mongodb.MongoDBApplicationConfig;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import java.net.UnknownHostException;

import static org.fest.assertions.Assertions.assertThat;

public class MongoDBApplicationConfigTest {

	static MongoDBApplicationConfig mongoDBApplicationConfig;

	@BeforeClass
	public static void beforeClass() {
		mongoDBApplicationConfig = new MongoDBApplicationConfig();
	}

	@Test
	public void getDatabaseName() throws UnknownHostException {
		Mongo mongo = mongoDBApplicationConfig.mongo();
		assertThat(mongo.getAddress().getHost()).isEqualTo("localhost");
		assertThat(mongo.getAddress().getPort()).isEqualTo(27017);
	}

	@Test
	public void mongoDBFactory() throws Exception {
		SimpleMongoDbFactory mongoDbFactory = mongoDBApplicationConfig.mongoDbFactory();
		assertThat("recruiting").isEqualTo(mongoDbFactory.getDb().getName());
	}

}
