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
package it.f2informatica.mongodb.repositories;

import it.f2informatica.mongodb.domain.User;
import it.f2informatica.pagination.repository.MongoPaginationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;

public interface UserRepository extends MongoPaginationRepository<User, String> {

  User findByUsername(String username);

  User findByUsernameAndPassword(String username, String password);

	@Query(value = "{ 'username' : { $ne : ?0 } }")
	Page<User> findAllExcludingUser(String username, Pageable pageable);

	@Query(value = "{ '_id' : ?0, 'notRemovable' : false }", delete = true)
	void deleteUser(String userId);

}
