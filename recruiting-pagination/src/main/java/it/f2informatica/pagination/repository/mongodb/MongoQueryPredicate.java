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
package it.f2informatica.pagination.repository.mongodb;

import org.springframework.data.mongodb.core.query.Query;

public abstract class MongoQueryPredicate<T> {

	private Class<T> entityClass;

	public MongoQueryPredicate(Class<T> clazz) {
		this.entityClass = clazz;
	}

	public abstract Query queryPredicate();

	public Class<T> getEntityClass() {
		return entityClass;
	}

}
