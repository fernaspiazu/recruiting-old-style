package it.f2informatica.mysql.repositories;

import it.f2informatica.mysql.domain.Education;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EducationRepository extends JpaRepository<Education, Long> {
}
