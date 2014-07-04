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
package it.f2informatica.pagination.services;

import it.f2informatica.pagination.repository.mongodb.MongoQueryPredicate;
import org.springframework.data.domain.Page;

/**
 * Executes queries and perform pagination with MongoDB.
 *
 * @author Fernando Aspiazu
 */
public interface MongoDBPaginationService {

	String getEmptyPaginatedResultAsJson(QueryParameters parameters);

	<T> String getPaginatedResultAsJson(QueryParameters parameters, Class<T> entityClass);

	<T> String getPaginatedResultAsJson(QueryParameters parameters, MongoQueryPredicate<T> queryPredicate);

	<T> Page<T> getPaginatedResult(QueryParameters parameters, Class<T> entityClass);

	<T> Page<T> getPaginatedResult(QueryParameters parameters, MongoQueryPredicate<T> queryPredicate);

}
