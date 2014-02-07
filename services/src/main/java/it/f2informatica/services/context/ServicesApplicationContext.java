package it.f2informatica.services.context;

import com.google.common.collect.Sets;
import it.f2informatica.services.gateway.EntityToModelConverter;
import it.f2informatica.services.gateway.mongodb.converter.*;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ConversionServiceFactoryBean;

@Configuration
@ComponentScan(basePackages = {
		"it.f2informatica.services.domain",
		"it.f2informatica.services.gateway"
})
public class ServicesApplicationContext {

	@Bean
	public ConversionServiceFactoryBean mongodbConversionService() {
		ConversionServiceFactoryBean conversionServiceFactoryBean = new ConversionServiceFactoryBean();
		conversionServiceFactoryBean.setConverters(Sets.newHashSet(
				mongoDBUserToModelConverter(),
				mongoDBConsultantToModelConverter(),
				mongoDBAddressToModelConverter(),
				mongoDBExperienceToModelConverter(),
				mongoDBLanguageToModelConverter(),
				mongoDBEducationToModelConverter()
		));
		return conversionServiceFactoryBean;
	}

	@Bean(name = "userToModelConverter", autowire = Autowire.BY_NAME)
	public EntityToModelConverter mongoDBUserToModelConverter() {
		return new MongoDBUserToUserModelConverter();
	}

	@Bean(name = "consultantToModelConverter", autowire = Autowire.BY_NAME)
	public EntityToModelConverter mongoDBConsultantToModelConverter() {
		return new MongoDBConsultantToConsultantModelConverter();
	}

	@Bean(name = "addressToModelConverter", autowire = Autowire.BY_NAME)
	public EntityToModelConverter mongoDBAddressToModelConverter() {
		return new MongoDBAddressToAddressModelConverter();
	}

	@Bean(name = "experienceToModelConverter", autowire = Autowire.BY_NAME)
	public EntityToModelConverter mongoDBExperienceToModelConverter() {
		return new MongoDBExperienceToExperienceModel();
	}

	@Bean(name = "educationToModelConverter", autowire = Autowire.BY_NAME)
	public EntityToModelConverter mongoDBEducationToModelConverter() {
		return new MongoDBEducationToEducationModelConverter();
	}

	@Bean(name = "languageToModelConverter", autowire = Autowire.BY_NAME)
	public EntityToModelConverter mongoDBLanguageToModelConverter() {
		return new MongoDBLanguageToLanguageModelConverter();
	}

}
