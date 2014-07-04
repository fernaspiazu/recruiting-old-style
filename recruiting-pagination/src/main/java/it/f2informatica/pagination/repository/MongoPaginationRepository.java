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
package it.f2informatica.pagination.repository;

import it.f2informatica.pagination.repository.mongodb.MongoDBQueryExecutor;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

/**
 * <p>
 *   Extend this repository in order to use the server-side pagination
 *   backed with MongoDB.
 * </p>
 *
 * @author Fernando Aspiazu
 *
 * @param <T> Entity Document Type
 * @param <ID> ID - PK Type
 */
@NoRepositoryBean
public interface MongoPaginationRepository<T, ID extends Serializable>
				extends MongoRepository<T, ID>, MongoDBQueryExecutor<T> {

}
