package it.f2informatica.mysql.domain.metamodel;

import it.f2informatica.mysql.domain.Role;
import it.f2informatica.mysql.domain.User;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(User.class)
public class User_ {

  public static volatile SingularAttribute<User, Long> id;
  public static volatile SingularAttribute<User, String> username;
  public static volatile SingularAttribute<User, String> password;
  public static volatile SingularAttribute<User, String> firstName;
  public static volatile SingularAttribute<User, String> lastName;
  public static volatile SingularAttribute<User, String> email;
  public static volatile SingularAttribute<User, Role> role;

}
