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
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

@Configuration
@ImportResource("classpath:spring/repository-populator-config.xml")
@PropertySource("classpath:mongodb-replicaset.properties")
@EnableMongoRepositories(
	basePackages = {
		"it.f2informatica.mongodb.repositories",
		"it.f2informatica.pagination.repository"},
	repositoryFactoryBeanClass = MongoPaginationRepositoryFactoryBean.class)
@MongoDB
public class MongoDBReplicaSetApplicationConfig extends AbstractMongoConfiguration {

	@Value("${mongo.host}")
	private String host;

	@Value("${mongo.node.alpha}")
	private int nodeAlpha;

	@Value("${mongo.node.beta}")
	private int nodeBeta;

	@Value("${mongo.node.omega}")
	private int nodeOmega;

	@Value("${mongo.database}")
	private String database;

	@Override
	protected String getDatabaseName() {
		return database;
	}

	@Bean
	@Override
	public Mongo mongo() throws UnknownHostException {
		return new MongoClient(replicaSet(), mongoOptions());
	}

	private MongoClientOptions mongoOptions() {
		return MongoClientOptions.builder()
				.connectionsPerHost(10)
				.threadsAllowedToBlockForConnectionMultiplier(4)
				.connectTimeout(10000)
				.maxWaitTime(5000)
				.socketKeepAlive(true)
				.socketTimeout(5000)
				.build();
	}

	private List<ServerAddress> replicaSet() throws UnknownHostException {
		return Arrays.asList(
				new ServerAddress(host, nodeAlpha),
				new ServerAddress(host, nodeBeta),
				new ServerAddress(host, nodeOmega));
	}

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}

}