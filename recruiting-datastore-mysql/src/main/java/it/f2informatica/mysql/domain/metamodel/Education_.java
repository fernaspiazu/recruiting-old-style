package it.f2informatica.mysql.domain.metamodel;

import it.f2informatica.mysql.domain.Consultant;
import it.f2informatica.mysql.domain.Education;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Education.class)
public class Education_ {

  public static volatile SingularAttribute<Education, Long> id;
  public static volatile SingularAttribute<Education, String> schoolName;
  public static volatile SingularAttribute<Education, Integer> startYear;
  public static volatile SingularAttribute<Education, Integer> endYear;
  public static volatile SingularAttribute<Education, Boolean> current;
  public static volatile SingularAttribute<Education, String> schoolDegree;
  public static volatile SingularAttribute<Education, String> fieldsOfStudy;
  public static volatile SingularAttribute<Education, String> grade;
  public static volatile SingularAttribute<Education, String> activities;
  public static volatile SingularAttribute<Education, String> description;
  public static volatile SingularAttribute<Education, Consultant> consultant;

}
