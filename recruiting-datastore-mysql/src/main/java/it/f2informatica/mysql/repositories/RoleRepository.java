package it.f2informatica.mysql.repositories;

import it.f2informatica.mysql.domain.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Long> {

  Role findByName(String name);

}
