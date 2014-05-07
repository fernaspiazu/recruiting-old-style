package it.f2informatica.mysql.domain.metamodel;

import it.f2informatica.mysql.domain.Skill;
import it.f2informatica.mysql.domain.pk.SkillPK;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Skill.class)
public class Skill_ {

  public static volatile SingularAttribute<Skill, SkillPK> id;

}
