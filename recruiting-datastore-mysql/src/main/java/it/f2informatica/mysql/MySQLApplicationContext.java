package it.f2informatica.mysql;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.beans.PropertyVetoException;

@Configuration
@EnableJpaRepositories(basePackages = "it.f2informatica.mysql.repositories")
@EnableTransactionManagement
@PropertySource(value = "classpath:mysql.properties")
@Profile("mysql")
public class MySQLApplicationContext {
  private static final Logger logger = LoggerFactory.getLogger(MySQLApplicationContext.class);

  private static final String DOMAIN_PACKAGE = "it.f2informatica.mysql.domain";

  @Value("${mysql.driver}")
  private String driver;

  @Value("${mysql.url}")
  private String url;

  @Value("${mysql.user}")
  private String user;

  @Value("${mysql.password}")
  private String password;

  @Bean(destroyMethod = "close")
  public ComboPooledDataSource dataSource() {
    ComboPooledDataSource dataSource = new ComboPooledDataSource();
    try {
      dataSource.setDriverClass(driver);
      dataSource.setJdbcUrl(url);
      dataSource.setUser(user);
      dataSource.setPassword(password);
    } catch (PropertyVetoException e) {
      logger.error("Error on C3P0 DataSource construction...", e);
    }
    return dataSource;
  }

  @Bean
  public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
    HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
    vendorAdapter.setDatabase(Database.MYSQL);
    vendorAdapter.setGenerateDdl(false);
    vendorAdapter.setShowSql(true);

    LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
    factoryBean.setJpaVendorAdapter(vendorAdapter);
    factoryBean.setPackagesToScan(DOMAIN_PACKAGE);
    factoryBean.setDataSource(dataSource());
    return factoryBean;
  }

  @Bean
  public PlatformTransactionManager transactionManager() {
    JpaTransactionManager txManager = new JpaTransactionManager();
    txManager.setEntityManagerFactory(entityManagerFactory().getObject());
    return txManager;
  }

  @Bean
  public PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
    return new PropertySourcesPlaceholderConfigurer();
  }

}
