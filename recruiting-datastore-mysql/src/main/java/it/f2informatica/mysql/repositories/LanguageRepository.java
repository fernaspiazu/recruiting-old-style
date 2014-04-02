package it.f2informatica.mysql.repositories;

import it.f2informatica.mysql.domain.Language;
import it.f2informatica.mysql.domain.pk.LanguagePK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LanguageRepository extends JpaRepository<Language, LanguagePK> {

  @Query(value = "select * from languages where consultant_id = :consultantId", nativeQuery = true)
  List<Language> findByConsultantId(@Param("consultantId") Long consultantId);

}
