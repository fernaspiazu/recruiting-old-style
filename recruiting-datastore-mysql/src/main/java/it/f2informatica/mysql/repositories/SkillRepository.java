package it.f2informatica.mysql.repositories;

import it.f2informatica.mysql.domain.Skill;
import it.f2informatica.mysql.domain.pk.SkillPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SkillRepository extends JpaRepository<Skill, SkillPK> {

  Skill findByIdSkill(String skill);

}
