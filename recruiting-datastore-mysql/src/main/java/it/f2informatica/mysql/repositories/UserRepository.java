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
package it.f2informatica.mysql.repositories;

import it.f2informatica.mysql.domain.Role;
import it.f2informatica.mysql.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByUsername(String username);

	User findByUsernameAndPassword(String username, String password);

	Iterable<User> findByRoleName(String roleName);

	@Query("SELECT u FROM User u WHERE u.username NOT IN (:usernameToExclude, 'admin')")
	List<User> findAllExcludingCurrentUser(@Param("usernameToExclude") String usernameToExclude);

	@Query("SELECT u FROM User u WHERE u.id = :userId AND u.password = :password")
	User findByIdAndPassword(@Param("userId") Long userId, @Param("password") String password);

	@Modifying
	@Query("UPDATE User u SET u.password = :newPwd WHERE u.id = :userId AND u.password = :currentPwd")
	int updatePassword(@Param("userId") Long userId,
	                   @Param("currentPwd") String currentPwd,
	                   @Param("newPwd") String newPwd);

	@Modifying
	@Query("UPDATE User u SET " +
		"u.username = :username, " +
		"u.firstName = :firstName, " +
		"u.lastName = :lastName, " +
		"u.email = :email, " +
		"u.role = :role " +
		"WHERE u.id = :userId")
	int updateUser(@Param("userId") Long userId,
	               @Param("username") String username,
	               @Param("firstName") String firstName,
	               @Param("lastName") String lastName,
	               @Param("email") String email,
	               @Param("role") Role role);

}
