package it.f2informatica.services;

import com.google.common.collect.Sets;
import it.f2informatica.mysql.MySQLApplicationContext;
import it.f2informatica.mysql.domain.*;
import it.f2informatica.services.gateway.EntityToModelConverter;
import it.f2informatica.services.gateway.mysql.converter.*;
import it.f2informatica.services.model.*;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.context.annotation.*;
import org.springframework.context.support.ConversionServiceFactoryBean;

@Configuration
@ComponentScan(basePackages = {"it.f2informatica.services.gateway.mysql"})
@Import(MySQLApplicationContext.class)
@Profile("mysql")
public class MySQLConversionServicesContext {

  @Bean
  @SuppressWarnings("unchecked")
  public ConversionServiceFactoryBean mysqlConversionService() {
    ConversionServiceFactoryBean conversionServiceFactoryBean = new ConversionServiceFactoryBean();
    conversionServiceFactoryBean.setConverters(Sets.newHashSet(
      mySQLUserToModelConverter(),
      mySQLConsultantToModelConverter(),
      mySQLExperienceToModelConverter(),
      mySQLLanguageToModelConverter(),
      mySQLEducationToModelConverter(),
      mySQLAddressToModelConverter(),
      mySQLSkillToStringConverter()
    ));
    return conversionServiceFactoryBean;
  }

  @Bean(name = "mysqlUserToModelConverter", autowire = Autowire.BY_NAME)
  public EntityToModelConverter<User, UserModel> mySQLUserToModelConverter() {
    return new MySQLUserToModelConverter();
  }

  @Bean(name = "mysqlConsultantToModelConverter", autowire = Autowire.BY_NAME)
  public EntityToModelConverter<Consultant, ConsultantModel> mySQLConsultantToModelConverter() {
    return new MySQLConsultantToModelConverter();
  }

  @Bean(name = "mysqlAddressToModelConverter", autowire = Autowire.BY_NAME)
  public EntityToModelConverter<Address, AddressModel> mySQLAddressToModelConverter() {
    return new MySQLAddressToModelConverter();
  }

  @Bean(name = "mysqlExperienceToModelConverter", autowire = Autowire.BY_NAME)
  public EntityToModelConverter<Experience, ExperienceModel> mySQLExperienceToModelConverter() {
    return new MySQLExperienceToModelConverter();
  }

  @Bean(name = "mysqlEducationToModelConverter", autowire = Autowire.BY_NAME)
  public EntityToModelConverter<Education, EducationModel> mySQLEducationToModelConverter() {
    return new MySQLEducationToModelConverter();
  }

  @Bean(name = "mysqlLanguageToModelConverter", autowire = Autowire.BY_NAME)
  public EntityToModelConverter<Language, LanguageModel> mySQLLanguageToModelConverter() {
    return new MySQLLanguageToModelConverter();
  }

  @Bean(name = "mysqlSkillToStringConverter", autowire = Autowire.BY_NAME)
  public EntityToModelConverter<Skill, String> mySQLSkillToStringConverter() {
    return new MySQLSkillToStringConverter();
  }

}
