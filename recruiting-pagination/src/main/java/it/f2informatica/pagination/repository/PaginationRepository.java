package it.f2informatica.pagination.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

/**
 * <p>Extend this repository in order to use the server-side pagination.</p>
 * <p>
 * This interface works with {@link org.springframework.data.jpa.domain.Specification}
 * and {@link QueryDslPredicateExecutor} of QueryDsl.
 * </p>
 *
 * @author Fernando Aspiazu
 *
 * @param <T> Entity Type
 * @param <ID> ID - PK Type
 */
@NoRepositoryBean
public interface PaginationRepository<T, ID extends Serializable>
  extends JpaRepository<T, ID>, JpaSpecificationExecutor<T>, QueryDslPredicateExecutor<T> {

}
