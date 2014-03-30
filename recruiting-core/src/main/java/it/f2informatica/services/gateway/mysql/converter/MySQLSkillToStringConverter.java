package it.f2informatica.services.gateway.mysql.converter;

import it.f2informatica.mysql.domain.Skill;
import it.f2informatica.services.gateway.EntityToModelConverter;

public class MySQLSkillToStringConverter extends EntityToModelConverter<Skill, String> {

  @Override
  public String convert(Skill skill) {
    return (skill == null || skill.getId() == null) ? null : skill.getId().getSkill();
  }

}
