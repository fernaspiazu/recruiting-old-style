package it.f2informatica.mysql.domain.metamodel;

import it.f2informatica.mysql.domain.Address;
import it.f2informatica.mysql.domain.Consultant;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Address.class)
public class Address_ {

  public static volatile SingularAttribute<Address, Long> id;
  public static volatile SingularAttribute<Address, String> street;
  public static volatile SingularAttribute<Address, String> houseNo;
  public static volatile SingularAttribute<Address, String> zipCode;
  public static volatile SingularAttribute<Address, String> city;
  public static volatile SingularAttribute<Address, String> province;
  public static volatile SingularAttribute<Address, String> region;
  public static volatile SingularAttribute<Address, String> country;
  public static volatile SingularAttribute<Address, Consultant> consultantResidence;
  public static volatile SingularAttribute<Address, Consultant> consultantDomicile;

}
