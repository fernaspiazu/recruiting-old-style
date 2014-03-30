package it.f2informatica.services;

import com.google.common.collect.Sets;
import it.f2informatica.mysql.MySQLApplicationContext;
import it.f2informatica.mysql.domain.User;
import it.f2informatica.services.gateway.EntityToModelConverter;
import it.f2informatica.services.gateway.mysql.converter.MySQLUserToModelConverter;
import it.f2informatica.services.model.UserModel;
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
      mySQLUserToModelConverter()
    ));
    return conversionServiceFactoryBean;
  }

  @Bean(name = "mysqlUserToModelConverter", autowire = Autowire.BY_NAME)
  public EntityToModelConverter<User, UserModel> mySQLUserToModelConverter() {
    return new MySQLUserToModelConverter();
  }

}
