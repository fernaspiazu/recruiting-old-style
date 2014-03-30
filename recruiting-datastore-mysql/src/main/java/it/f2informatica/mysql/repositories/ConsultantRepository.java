package it.f2informatica.mysql.repositories;

import it.f2informatica.mysql.domain.Consultant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsultantRepository extends JpaRepository<Consultant, Long> {
}
