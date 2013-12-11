package it.f2informatica.test.mongodb;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * In order to run this unit test correctly, it is necessary
 * to have a MongoDB instance running on localhost port: 27018.
 * See {@link DatastoreApplicationContextTest} for more information
 * about the configuration of this test.
 * @see DatastoreApplicationContextTest
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DatastoreApplicationContextTest.class})
public abstract class DatastoreUtils {

	@Autowired
	protected MongoTemplate mongoTemplateTest;

	protected static <T> T getBean(Class<T> clazz) {
		return getBean(null, clazz);
	}

	protected static <T> T getBean(String beanName, Class<T> clazz) {
		ApplicationContext appContext = new AnnotationConfigApplicationContext(DatastoreApplicationContextTest.class);
		return beanName == null ? appContext.getBean(clazz) : appContext.getBean(beanName, clazz);
	}

}
