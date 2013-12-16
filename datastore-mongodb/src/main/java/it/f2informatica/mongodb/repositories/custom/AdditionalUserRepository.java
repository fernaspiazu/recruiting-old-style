package it.f2informatica.mongodb.repositories.custom;

import it.f2informatica.mongodb.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdditionalUserRepository {

	Page<User> findAllExcludingUser(Pageable pageable, String username);

	Iterable<User> findByRoleName(String roleName);

	void deleteRemovableUser(String userId);

	boolean updatePassword(String userId, String currentPwd, String newPwd, String confirmedPwd);

	boolean isCurrentPasswordValid(String userId, String currentPwd);
}
