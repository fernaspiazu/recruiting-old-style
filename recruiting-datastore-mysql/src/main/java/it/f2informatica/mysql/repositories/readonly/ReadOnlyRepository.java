package it.f2informatica.mysql.repositories.readonly;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import java.io.Serializable;

@NoRepositoryBean
public interface ReadOnlyRepository<T, ID extends Serializable> extends Repository<T, ID> {

  T findOne(ID id);

  Iterable<T> findAll();

  Iterable<T> findAll(Sort sort);

  Page<T> findAll(Pageable pageable);

}
