package it.f2informatica.services;

import com.google.common.collect.Sets;
import it.f2informatica.mongodb.MongoDBApplicationContext;
import it.f2informatica.mongodb.domain.*;
import it.f2informatica.services.gateway.EntityToModelConverter;
import it.f2informatica.services.gateway.mongodb.converter.*;
import it.f2informatica.services.model.*;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.context.annotation.*;
import org.springframework.context.support.ConversionServiceFactoryBean;

@Configuration
@ComponentScan(basePackages = {"it.f2informatica.services.gateway.mongodb"})
@Import(MongoDBApplicationContext.class)
@Profile("mongodb")
public class MongoDBConversionServicesContext {

  @Bean
  @SuppressWarnings("unchecked")
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
  public EntityToModelConverter<User, UserModel> mongoDBUserToModelConverter() {
    return new MongoDBUserToUserModelConverter();
  }

  @Bean(name = "consultantToModelConverter", autowire = Autowire.BY_NAME)
  public EntityToModelConverter<Consultant, ConsultantModel> mongoDBConsultantToModelConverter() {
    return new MongoDBConsultantToConsultantModelConverter();
  }

  @Bean(name = "addressToModelConverter", autowire = Autowire.BY_NAME)
  public EntityToModelConverter<Address, AddressModel> mongoDBAddressToModelConverter() {
    return new MongoDBAddressToAddressModelConverter();
  }

  @Bean(name = "experienceToModelConverter", autowire = Autowire.BY_NAME)
  public EntityToModelConverter<Experience, ExperienceModel> mongoDBExperienceToModelConverter() {
    return new MongoDBExperienceToExperienceModel();
  }

  @Bean(name = "educationToModelConverter", autowire = Autowire.BY_NAME)
  public EntityToModelConverter<Education, EducationModel> mongoDBEducationToModelConverter() {
    return new MongoDBEducationToEducationModelConverter();
  }

  @Bean(name = "languageToModelConverter", autowire = Autowire.BY_NAME)
  public EntityToModelConverter<Language, LanguageModel> mongoDBLanguageToModelConverter() {
    return new MongoDBLanguageToLanguageModelConverter();
  }

}
