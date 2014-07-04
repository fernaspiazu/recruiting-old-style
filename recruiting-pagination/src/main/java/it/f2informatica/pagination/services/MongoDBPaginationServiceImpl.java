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

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import it.f2informatica.pagination.repository.MongoPaginationRepository;
import it.f2informatica.pagination.repository.mongodb.MongoQueryPredicate;
import it.f2informatica.pagination.response.PaginationResponse;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.repository.support.Repositories;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.Serializable;

/**
 * Executes queries and perform pagination with MongoDB.
 *
 * @author Fernando Aspiazu
 */
@Service
public class MongoDBPaginationServiceImpl implements MongoDBPaginationService {

	@Autowired
	private Gson gson;

	@Autowired
	private PageableFactory pageableFactory;

	@Autowired
	private PaginationResponse paginationResponse;

	@Autowired
	private ListableBeanFactory listableBeanFactory;

	private Repositories repositories;

	@PostConstruct
	public void init() {
		this.repositories = new Repositories(listableBeanFactory);
	}

	@Override
	@SuppressWarnings({"unchecked", "rawtypes"})
	public String getEmptyPaginatedResultAsJson(QueryParameters parameters) {
		return gson.toJson(paginationResponse.generateResponseAttributes(parameters, new PageImpl(Lists.newArrayList())));
	}

	@Override
	public <T> String getPaginatedResultAsJson(QueryParameters parameters, Class<T> entityClass) {
		Page<T> paginatedResult = getPaginatedResult(parameters, entityClass);
		return gson.toJson(paginationResponse.generateResponseAttributes(parameters, paginatedResult));
	}

	@Override
	public <T> String getPaginatedResultAsJson(QueryParameters parameters, MongoQueryPredicate<T> queryPredicate) {
		Page<T> paginatedResult = getPaginatedResult(parameters, queryPredicate);
		return gson.toJson(paginationResponse.generateResponseAttributes(parameters, paginatedResult));
	}

	@Override
	public <T> Page<T> getPaginatedResult(QueryParameters parameters, Class<T> entityClass) {
		return getRepository(entityClass).findAll(pageableFactory.getPageable(parameters));
	}

	@Override
	public <T> Page<T> getPaginatedResult(QueryParameters parameters, MongoQueryPredicate<T> queryPredicate) {
		return getRepository(queryPredicate.getEntityClass()).findAll(queryPredicate, pageableFactory.getPageable(parameters));
	}

	@SuppressWarnings({"unchecked", "rawtypes"})
	private <T, ID extends Serializable> MongoPaginationRepository<T, ID> getRepository(Class<T> entityClass) {
		Object repository = repositories.getRepositoryFor(entityClass);
		if (repository instanceof MongoPaginationRepository) {
			return (MongoPaginationRepository) repository;
		}
		throw new PaginationException("Repository for document [" + entityClass.getName() + "] " + "must extend ["
			+ MongoPaginationRepository.class.getName() + "] interface");
	}

}
