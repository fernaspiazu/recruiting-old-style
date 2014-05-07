package it.f2informatica.mysql.domain.metamodel;

import it.f2informatica.mysql.domain.Language;
import it.f2informatica.mysql.domain.pk.LanguagePK;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Language.class)
public class Language_ {

  public static volatile SingularAttribute<Language, LanguagePK> id;
  public static volatile SingularAttribute<Language, String> proficiency;

}
