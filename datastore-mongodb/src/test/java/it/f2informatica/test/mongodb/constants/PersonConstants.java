package it.f2informatica.test.mongodb.constants;

import it.f2informatica.mongodb.domain.Gender;
import it.f2informatica.mongodb.domain.MaritalStatus;

import java.util.Date;
import java.util.GregorianCalendar;

public interface PersonConstants {

	final String PERSON_1_FIRSTNAME = "Mario";
	final String PERSON_1_LASTNAME = "Rossi";
	final Gender PERSON_1_GENDER = Gender.MALE;
	final Date PERSON_1_BIRTHDATE = new GregorianCalendar(20, 4, 1980).getTime();
	final String PERSON_1_BIRTHCITY = "Macerata";
	final String PERSON_1_BIRTHCOUNTRY = "Italia";
	final MaritalStatus PERSON_1_MARITALSTATUS = MaritalStatus.SINGLE;

}
