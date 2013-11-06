package it.f2informatica.mongodb.repositories;

import it.f2informatica.mongodb.domain.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, String> {

	Role findByName(String name);

}
