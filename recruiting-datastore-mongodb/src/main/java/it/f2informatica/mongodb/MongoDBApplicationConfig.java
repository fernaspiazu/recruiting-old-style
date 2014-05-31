/*
 * =============================================================================
 *
 *   Copyright (c) 2014, Fernando Aspiazu
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 * =============================================================================
 */
package it.f2informatica.mongodb;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.authentication.UserCredentials;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.util.StringUtils;

import java.net.UnknownHostException;

@Configuration
@ImportResource("classpath:spring/repository-populator-config.xml")
@EnableMongoRepositories(basePackages = {"it.f2informatica.mongodb.repositories"})
@PropertySource("classpath:mongodb.properties")
@MongoDB
public class MongoDBApplicationConfig extends AbstractMongoConfiguration {
  private static final String OTHER_DATABASE = System.getProperty("mongodb.database.name");

  @Value("${mongodb.host}")
  private String host;

  @Value("${mongodb.port}")
  private String defaultPort;

  @Value("${mongodb.database}")
  private String database;

  @Value("${mongodb.user}")
  private String user;

  @Value("${mongodb.password}")
  private String password;

  @Override
  protected String getDatabaseName() {
    return StringUtils.hasText(OTHER_DATABASE) ? OTHER_DATABASE : database;
  }

  @Bean
  @Override
  public Mongo mongo() throws UnknownHostException {
    Mongo mongo = new MongoClient(new ServerAddress(host, Integer.parseInt(defaultPort)));
    mongo.getMongoOptions().setConnectionsPerHost(10);
    mongo.getMongoOptions().setThreadsAllowedToBlockForConnectionMultiplier(4);
    mongo.getMongoOptions().setConnectTimeout(5000);
    mongo.getMongoOptions().setMaxWaitTime(3000);
    mongo.getMongoOptions().setAutoConnectRetry(true);
    mongo.getMongoOptions().setSocketKeepAlive(true);
    mongo.getMongoOptions().setSocketTimeout(3000);
    return mongo;
  }

  protected UserCredentials getUserCredentials() {
    return new UserCredentials(user, password);
  }

  @Bean
  public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
    return new PropertySourcesPlaceholderConfigurer();
  }

}