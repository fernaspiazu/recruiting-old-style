package it.f2informatica.test.mongodb;

import it.f2informatica.mongodb.domain.Consultant;
import it.f2informatica.mongodb.domain.Role;
import it.f2informatica.mongodb.domain.User;
import org.junit.AfterClass;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * In order to run this unit test correctly, it is necessary
 * to have a MongoDB instance running on localhost port: 27018.
 * See {@link MongoDBApplicationConfigTest} for more information
 * about the configuration of this test.
 * @see MongoDBApplicationConfigTest
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {MongoDBApplicationConfigTest.class})
public abstract class DatastoreUtils {

	@Autowired
	@Qualifier("mongoTemplateTest")
	protected MongoTemplate mongoTemplateTest;

	protected static <T> T getBean(Class<T> clazz) {
		return getBean(null, clazz);
	}

	protected static <T> T getBean(String beanName, Class<T> clazz) {
		ApplicationContext appContext = new AnnotationConfigApplicationContext(MongoDBApplicationConfigTest.class);
		if (beanName == null) {
			return appContext.getBean(clazz);
		}
		return appContext.getBean(beanName, clazz);
	}

	@AfterClass
	public static void turnOffDatastoreTests() {
		MongoTemplate _mongoTemplateTest = getBean("mongoTemplateTest", MongoTemplate.class);
		_mongoTemplateTest.dropCollection(Role.class);
		_mongoTemplateTest.dropCollection(User.class);
		_mongoTemplateTest.dropCollection(Consultant.class);
	}

}
