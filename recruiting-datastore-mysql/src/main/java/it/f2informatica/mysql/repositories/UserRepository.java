package it.f2informatica.mysql.repositories;

import it.f2informatica.mysql.domain.User;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {

  User findByUsername(String username);

}
