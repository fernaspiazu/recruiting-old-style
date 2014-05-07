package it.f2informatica.mysql.domain.metamodel;

import it.f2informatica.mysql.domain.Consultant;
import it.f2informatica.mysql.domain.Experience;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.util.Date;

@StaticMetamodel(Experience.class)
public class Experience_ {

  public static volatile SingularAttribute<Experience, Long> id;
  public static volatile SingularAttribute<Experience, String> companyName;
  public static volatile SingularAttribute<Experience, String> jobPosition;
  public static volatile SingularAttribute<Experience, String> location;
  public static volatile SingularAttribute<Experience, Date> periodFrom;
  public static volatile SingularAttribute<Experience, Date> periodTo;
  public static volatile SingularAttribute<Experience, Boolean> current;
  public static volatile SingularAttribute<Experience, String> description;
  public static volatile SingularAttribute<Experience, Consultant> consultant;

}
