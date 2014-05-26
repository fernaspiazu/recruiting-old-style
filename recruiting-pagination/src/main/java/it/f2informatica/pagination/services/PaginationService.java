package it.f2informatica.pagination.services;

import com.mysema.query.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;

/**
 * Executes queries and perform pagination.
 *
 * @author Fernando
 */
public interface PaginationService {

  String getEmptyPaginatedResultAsJson(QueryParameters parameters);

  <T> String getPaginatedResultAsJson(QueryParameters parameters, Class<T> entityClass);

  <T> String getPaginatedResultAsJson(QueryParameters parameters, Predicate predicate, Class<T> entityClass);

  <T> String getPaginatedResultAsJson(QueryParameters parameters, Specification<T> specification, Class<T> entityClass);

  <T> Page<T> getPaginatedResult(QueryParameters parameters, Class<T> entityClass);

  <T> Page<T> getPaginatedResult(QueryParameters parameters, Predicate predicate, Class<T> entityClass);

  <T> Page<T> getPaginatedResult(QueryParameters parameters, Specification<T> specification, Class<T> entityClass);
}
