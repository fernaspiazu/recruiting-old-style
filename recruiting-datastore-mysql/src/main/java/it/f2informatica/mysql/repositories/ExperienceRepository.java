package it.f2informatica.mysql.repositories;

import it.f2informatica.mysql.domain.Experience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;

public interface ExperienceRepository extends JpaRepository<Experience, Long> {

  @Modifying
  @Query("UPDATE Experience e SET " +
    "e.companyName = :companyName, " +
    "e.jobPosition = :jobPosition, " +
    "e.location = :location, " +
    "e.periodFrom = :periodFrom, " +
    "e.periodTo = :periodTo, " +
    "e.current = :current, " +
    "e.description = :description " +
    "WHERE e.id = :id")
  int updateExperience(@Param("id") Long id,
                       @Param("companyName") String companyName,
                       @Param("jobPosition") String jobPosition,
                       @Param("location") String location,
                       @Param("periodFrom") Date periodFrom,
                       @Param("periodTo") Date periodTo,
                       @Param("current") boolean current,
                       @Param("description") String description);

}
