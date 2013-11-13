package it.f2informatica.mongodb.repositories.customrepositories;

import it.f2informatica.mongodb.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserRepositoryCustom {

	Page<User> findAllExcludingUser(Pageable pageable, String username);

	Iterable<User> findByRoleName(String roleName);

	void deleteRemovableUser(String userId);

}
