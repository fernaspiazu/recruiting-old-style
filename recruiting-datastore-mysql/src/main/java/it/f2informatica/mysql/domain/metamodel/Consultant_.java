package it.f2informatica.mysql.domain.metamodel;

import it.f2informatica.mysql.domain.Address;
import it.f2informatica.mysql.domain.Consultant;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.util.Date;

@StaticMetamodel(Consultant.class)
public class Consultant_ {

  public static volatile SingularAttribute<Consultant, Long> id;
  public static volatile SingularAttribute<Consultant, String> consultantNo;
  public static volatile SingularAttribute<Consultant, Date> registrationDate;
  public static volatile SingularAttribute<Consultant, String> fiscalCode;
  public static volatile SingularAttribute<Consultant, String> email;
  public static volatile SingularAttribute<Consultant, String> firstName;
  public static volatile SingularAttribute<Consultant, String> lastName;
  public static volatile SingularAttribute<Consultant, String> gender;
  public static volatile SingularAttribute<Consultant, String> phoneNumber;
  public static volatile SingularAttribute<Consultant, String> mobileNumber;
  public static volatile SingularAttribute<Consultant, Date> birthDate;
  public static volatile SingularAttribute<Consultant, String> birthCity;
  public static volatile SingularAttribute<Consultant, String> birthCountry;
  public static volatile SingularAttribute<Consultant, String> identityCard;
  public static volatile SingularAttribute<Consultant, String> interests;
  public static volatile SingularAttribute<Consultant, Address> residence;
  public static volatile SingularAttribute<Consultant, Address> domicile;

}
