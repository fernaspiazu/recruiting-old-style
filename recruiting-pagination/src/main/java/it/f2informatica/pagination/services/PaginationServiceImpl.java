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
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.mysema.query.Tuple;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.Expression;
import com.mysema.query.types.Order;
import com.mysema.query.types.OrderSpecifier;
import com.mysema.query.types.Predicate;
import com.mysema.query.types.path.EntityPathBase;
import it.f2informatica.pagination.repository.PaginationRepository;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.support.Repositories;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

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
  private ListableBeanFactory listableBeanFactory;

  private Repositories repositories;

  @PostConstruct
  public void init() {
    this.repositories = new Repositories(listableBeanFactory);
  }

  @Override
  @SuppressWarnings({"unchecked", "rawtypes"})
  public String getEmptyPaginatedResultAsJson(QueryParameters parameters) {
    Map<String, Object> datatableAttributes = createDatatableAttributes(parameters, new PageImpl(Lists.newArrayList()));
    return gson.toJson(datatableAttributes);
  }

  @Override
  public <T> String getPaginatedResultAsJson(QueryParameters parameters, Class<T> entityClass) {
    Page<T> paginatedQueryResult = getPaginatedResult(parameters, entityClass);
    return gson.toJson(createDatatableAttributes(parameters, paginatedQueryResult));
  }

  @Override
  public String getPaginatedResultAsJson(QueryParameters parameters, JPAQuery jpaQuery, Expression<?>... args) {
    Page<Tuple> paginatedQueryResult = getPaginatedResult(parameters, jpaQuery, args);
    return gson.toJson(createDatatableAttributes(parameters, paginatedQueryResult));
  }

  @Override
  public <T> String getPaginatedResultAsJson(QueryParameters parameters, Predicate predicate, Class<T> entityClass) {
    Page<T> paginatedQueryResult = getPaginatedResult(parameters, predicate, entityClass);
    return gson.toJson(createDatatableAttributes(parameters, paginatedQueryResult));
  }

  @Override
  public <T> String getPaginatedResultAsJson(QueryParameters parameters, Specification<T> specification, Class<T> entityClass) {
    Page<T> paginatedQueryResult = getPaginatedResult(parameters, specification, entityClass);
    return gson.toJson(createDatatableAttributes(parameters, paginatedQueryResult));
  }

  @Override
  public <T> Page<T> getPaginatedResult(QueryParameters parameters, Class<T> entityClass) {
    return getRepository(entityClass).findAll(getPageable(parameters));
  }

  @Override
  public Page<Tuple> getPaginatedResult(QueryParameters parameters, JPAQuery jpaQuery, Expression<?>... args) {
    List<Tuple> result = jpaQuery.orderBy(extractOrderSpecifier(parameters, args))
      .offset(parameters.getDisplayStart())
      .limit(parameters.getSize()).list(args);
    return new PageImpl<>(result, getPageable(parameters), jpaQuery.count());
  }

  @SuppressWarnings("unchecked")
  private OrderSpecifier extractOrderSpecifier(QueryParameters parameters, Expression<?>... args) {
    final String sortColumn = parameters.getSortColumn();
    final String sortDirection = parameters.getSortDirection();
    Order order = (Sort.Direction.ASC.compareTo(getDirection(sortDirection)) == 0) ? Order.ASC : Order.DESC;
    return new OrderSpecifier<>(order, new EntityPathBase(args[0].getType(), suppressUniquePrefixIfAny(sortColumn)));
  }

  @Override
  public <T> Page<T> getPaginatedResult(QueryParameters parameters, Predicate predicate, Class<T> entityClass) {
    return getRepository(entityClass).findAll(predicate, getPageable(parameters));
  }

  @Override
  public <T> Page<T> getPaginatedResult(QueryParameters parameters, Specification<T> specification, Class<T> entityClass) {
    return getRepository(entityClass).findAll(specification, getPageable(parameters));
  }

  private <T> Map<String, Object> createDatatableAttributes(QueryParameters parameters, Page<T> paginatedQueryResult) {
    final boolean hasContent = paginatedQueryResult.hasContent();
    final Object[] data = convertResultAsArrayOfObjects(paginatedQueryResult, parameters);
    Map<String, Object> datatableAttributes = Maps.newHashMap();
    datatableAttributes.put("sEcho", parameters.getEcho());
    datatableAttributes.put("iTotalRecords", (hasContent) ? paginatedQueryResult.getTotalElements() : 0L);
    datatableAttributes.put("iTotalDisplayRecords", (hasContent) ? paginatedQueryResult.getTotalElements() : 0L);
    datatableAttributes.put("aaData", data);
    return datatableAttributes;
  }

  private <T> Object[] convertResultAsArrayOfObjects(Page<T> paginatedQueryResult, QueryParameters parameters) {
    return paginatedQueryResult.hasContent()
      ? Iterables.toArray(constructDataFrom(parameters, paginatedQueryResult), Object.class)
      : ArrayUtils.EMPTY_OBJECT_ARRAY;
  }

  private <T> List<Map<String, Object>> constructDataFrom(QueryParameters parameters, Page<T> paginatedQueryResult) {
    List<Map<String, Object>> data = Lists.newArrayList();
    for (T entity : paginatedQueryResult.getContent()) {
      T tuple = resolveTuple(entity);
      Map<String, Object> row = Maps.newHashMap();
      for (int columnIndex = 0; columnIndex < parameters.getColumnsNumber(); columnIndex++) {
        String field = parameters.getColumnName(columnIndex);
        row.put(field, new SafeGetterMethodExecutor().invokeGetter(field, tuple));
      }
      data.add(row);
    }
    return data;
  }

  @SuppressWarnings("unchecked")
  private <T> T resolveTuple(T entity) {
    if (entity instanceof Tuple) {
      return (T) ((Tuple) entity).toArray()[0];
    }
    return entity;
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

  private Pageable getPageable(QueryParameters parameters) {
    int page = parameters.getPage(), size = parameters.getSize();
    Optional<Sort> sort = getSort(parameters);
    return sort.isPresent() ? new PageRequest(page, size, sort.get()) : new PageRequest(page, size);
  }

  private Optional<Sort> getSort(QueryParameters parameters) {
    final String sortColumn = parameters.getSortColumn();
    final String sortDirection = parameters.getSortDirection();

    return (StringUtils.hasText(sortColumn) && StringUtils.hasText(sortDirection))
      ? Optional.of(new Sort(getDirection(sortDirection), suppressUniquePrefixIfAny(sortColumn)))
      : Optional.<Sort>absent();
  }

  private Sort.Direction getDirection(String sortDirection) {
    Optional<Sort.Direction> direction = Optional.fromNullable(Sort.Direction.fromStringOrNull(sortDirection));
    return direction.isPresent() ? direction.get() : Sort.DEFAULT_DIRECTION;
  }

  /*
   * Since it's been switched Entity relationships from @OneToOne to
   *
   * @OneToMany / @ManyToOne, some getter fields has consequently changed by
   * adding the prefix 'unique', so, for each method Set<T> getEntity(), it will
   * be a T getUniqueEntity() method. This method helps to take away the unique
   * prefix in order to set the order by field correctly, otherwise an Exception
   * could be thrown.
   */
  private static String suppressUniquePrefixIfAny(String sortColumn) {
    if (sortColumn.contains(UNIQUE_PREFIX)) {
      final String separator = "_";
      List<String> realComposedField = Lists.newArrayList();
      for (String fakeField : sortColumn.split(separator)) {
        if (fakeField.startsWith(UNIQUE_PREFIX)) {
          realComposedField.add(fakeField.substring(UNIQUE_PREFIX.length()).toLowerCase());
          continue;
        }
        realComposedField.add(fakeField);
      }
      return org.apache.commons.lang3.StringUtils.join(realComposedField, separator).replace("_", ".");
    }
    return sortColumn.replace("_", ".");
  }

  /**
   * <p>Invoke the getter method of the given entity.</p>
   * <p>Parameter <code>fieldName</code> can be nested fields,
   * ie: <code>person.address.street.</code><br/>
   * In this way the scenario gotten will be:
   * <code>getPerson().getAddress().getStreet()</code></p>
   */
  private static class SafeGetterMethodExecutor {

    public <T> Object invokeGetter(String fieldName, T entity) {
      try {
        // -------------------------------------
        return _invokeGetter(fieldName, entity);
        // -------------------------------------
      } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
        e.printStackTrace();
      }
      return null;
    }

    private <T> Object _invokeGetter(String fieldName, T entity) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
      Optional<Object> result = Optional.absent();
      for (String field : fieldName.split("_")) {
        if (fieldName.endsWith(".") || 0 == field.length()) {
          throw new IllegalArgumentException("Invalid property name '" + fieldName + "'");
        }

        Method getter = entity.getClass().getMethod("get" + StringUtils.capitalize(field));
        result = Optional.fromNullable(getter.invoke(entity));

        if (!result.isPresent()) {
          break;
        } else if (!field.equals(fieldName)) {
        /*
				 * If the current field is not equals to the nested
				 * field properties, then it will call itself until
				 * reach the last property (via Recursive Call).
				 * On the other hand, fieldName.substring(field.length() + 1)
				 * means that I'm passing the entire nested field path
				 * to the next recursive call, except the current property
				 * evaluated considering also the 'dot'.
				 * For example, if we have "someProperty." (will be excluded)
				 */
          return _invokeGetter(fieldName.substring(field.length() + 1), result.get());
        }
      }
      return result.orNull(); // Holds the final result from the last getter method
    }

  }

}
