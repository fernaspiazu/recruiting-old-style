package it.f2informatica.core.gateway.mysql.converter;

import it.f2informatica.core.gateway.EntityToModelConverter;
import it.f2informatica.mysql.domain.Skill;
import org.springframework.stereotype.Component;

@Component("mysqlSkillToStringConverter")
public class MySQLSkillToStringConverter extends EntityToModelConverter<Skill, String> {

  @Override
  public String convert(Skill skill) {
    return (skill == null || skill.getId() == null) ? null : skill.getId().getSkill();
  }

}
