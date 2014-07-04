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

import it.f2informatica.pagination.repository.mongodb.SimpleMongoPaginationRepository;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.repository.support.MongoRepositoryFactory;
import org.springframework.data.mongodb.repository.support.MongoRepositoryFactoryBean;
import org.springframework.data.mongodb.repository.support.SimpleMongoRepository;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

import java.io.Serializable;

public class MongoPaginationRepositoryFactoryBean<R extends SimpleMongoRepository<T, ID>, T, ID extends Serializable>
				extends MongoRepositoryFactoryBean<R, T, ID> {

	@Override
	protected RepositoryFactorySupport getFactoryInstance(MongoOperations operations) {
		return new MongoPaginationRepositoryFactory(operations);
	}

	public static class MongoPaginationRepositoryFactory extends MongoRepositoryFactory {
		private MongoOperations mongoOperations;

		public MongoPaginationRepositoryFactory(MongoOperations mongoOperations) {
			super(mongoOperations);
			this.mongoOperations = mongoOperations;
		}

		@Override
		protected Object getTargetRepository(RepositoryMetadata metadata) {
			return new SimpleMongoPaginationRepository<>(getEntityInformation(metadata.getDomainType()), mongoOperations);
		}

		@Override
		protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
			return SimpleMongoPaginationRepository.class;
		}
	}

}
