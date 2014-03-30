package it.f2informatica.mongodb.repositories;

import it.f2informatica.mongodb.domain.Role;
import it.f2informatica.mongodb.domain.User;
import it.f2informatica.mongodb.repositories.custom.UserRepositoryCustom;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserRepository extends PagingAndSortingRepository<User, String>, UserRepositoryCustom {

	User findByUsername(String username);

	User findByUsernameAndPassword(String username, String password);

	Iterable<User> findByRole(Role role);

}
