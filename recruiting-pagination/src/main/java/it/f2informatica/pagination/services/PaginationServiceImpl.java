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

import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.mysema.query.Tuple;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.Expression;
import com.mysema.query.types.Order;
import com.mysema.query.types.OrderSpecifier;
import com.mysema.query.types.Predicate;
import com.mysema.query.types.path.EntityPathBase;
import it.f2informatica.pagination.repository.PaginationRepository;
import it.f2informatica.pagination.response.PaginationResponse;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.support.Repositories;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.List;

/**
 * Executes queries and perform pagination.
 *
 * @author Fernando Aspiazu
 */
@Service
public class PaginationServiceImpl implements PaginationService {
  private static final String UNIQUE_PREFIX = "unique";

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
    Page<T> paginatedQueryResult = getPaginatedResult(parameters, entityClass);
    return gson.toJson(paginationResponse.generateResponseAttributes(parameters, paginatedQueryResult));
  }

  @Override
  public String getPaginatedResultAsJson(QueryParameters parameters, JPAQuery jpaQuery, Expression<?>... args) {
    Page<Tuple> paginatedQueryResult = getPaginatedResult(parameters, jpaQuery, args);
    return gson.toJson(paginationResponse.generateResponseAttributes(parameters, paginatedQueryResult));
  }

  @Override
  public <T> String getPaginatedResultAsJson(QueryParameters parameters, Predicate predicate, Class<T> entityClass) {
    Page<T> paginatedQueryResult = getPaginatedResult(parameters, predicate, entityClass);
    return gson.toJson(paginationResponse.generateResponseAttributes(parameters, paginatedQueryResult));
  }

  @Override
  public <T> String getPaginatedResultAsJson(QueryParameters parameters, Specification<T> specification, Class<T> entityClass) {
    Page<T> paginatedQueryResult = getPaginatedResult(parameters, specification, entityClass);
    return gson.toJson(paginationResponse.generateResponseAttributes(parameters, paginatedQueryResult));
  }

  @Override
  public <T> Page<T> getPaginatedResult(QueryParameters parameters, Class<T> entityClass) {
    return getRepository(entityClass).findAll(pageableFactory.getPageable(parameters));
  }

  @Override
  public Page<Tuple> getPaginatedResult(QueryParameters parameters, JPAQuery jpaQuery, Expression<?>... args) {
    Optional<OrderSpecifier> orderSpecifier = getOrderSpecifier(parameters, args);
    if (orderSpecifier.isPresent()) {
      jpaQuery.orderBy(orderSpecifier.get());
    }
    List<Tuple> result = jpaQuery.offset(parameters.getDisplayStart()).limit(parameters.getSize()).list(args);
    return new PageImpl<>(result, pageableFactory.getPageable(parameters), jpaQuery.count());
  }

  @Override
  public <T> Page<T> getPaginatedResult(QueryParameters parameters, Predicate predicate, Class<T> entityClass) {
    return getRepository(entityClass).findAll(predicate, pageableFactory.getPageable(parameters));
  }

  @Override
  public <T> Page<T> getPaginatedResult(QueryParameters parameters, Specification<T> specification, Class<T> entityClass) {
    return getRepository(entityClass).findAll(specification, pageableFactory.getPageable(parameters));
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  private <T> PaginationRepository<T, ? extends Serializable> getRepository(Class<T> entityClass) {
    Object repository = repositories.getRepositoryFor(entityClass);
    if (repository instanceof PaginationRepository) {
      return (PaginationRepository) repository;
    }
    throw new PaginationException("Repository for entity [" + entityClass.getName() + "] " + "must extend ["
      + PaginationRepository.class.getName() + "] interface");
  }

  @SuppressWarnings("unchecked")
  private Optional<OrderSpecifier> getOrderSpecifier(QueryParameters parameters, Expression<?>... args) {
    final String sortColumn = parameters.getSortColumn();
    final String sortDirection = parameters.getSortDirection();
    if (StringUtils.hasText(sortColumn) && StringUtils.hasText(sortDirection)) {
      Order order = (Sort.Direction.ASC.compareTo(pageableFactory.getDirection(sortDirection)) == 0) ? Order.ASC : Order.DESC;
      return Optional.of(new OrderSpecifier(order, new EntityPathBase(args[0].getType(), suppressUniquePrefixIfAny(sortColumn))));
    }
    return Optional.absent();
  }

  private static String suppressUniquePrefixIfAny(String sortColumn) {
	  final String separator = "_";
    if (sortColumn.contains(UNIQUE_PREFIX)) {
      List<String> realComposedField = Lists.newArrayList();
      for (String fakeField : sortColumn.split(separator)) {
        if (fakeField.startsWith(UNIQUE_PREFIX)) {
          realComposedField.add(fakeField.substring(UNIQUE_PREFIX.length()).toLowerCase());
          continue;
        }
        realComposedField.add(fakeField);
      }
      return org.apache.commons.lang3.StringUtils.join(realComposedField, separator).replace(separator, ".");
    }
    return sortColumn.replace(separator, ".");
  }

}
