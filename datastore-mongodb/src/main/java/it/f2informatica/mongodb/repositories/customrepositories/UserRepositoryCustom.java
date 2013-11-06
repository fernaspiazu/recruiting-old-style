package it.f2informatica.mongodb.repositories.customrepositories;

import it.f2informatica.mongodb.domain.User;

public interface UserRepositoryCustom {

	Iterable<User> findByRoleName(String roleName);

	void deleteRemovableUser(String userId);

}
