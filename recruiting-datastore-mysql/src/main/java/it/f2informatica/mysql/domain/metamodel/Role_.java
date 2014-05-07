package it.f2informatica.mysql.domain.metamodel;

import it.f2informatica.mysql.domain.Role;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Role.class)
public class Role_ {

  public static volatile SingularAttribute<Role, Long> id;
  public static volatile SingularAttribute<Role, String> name;

}
