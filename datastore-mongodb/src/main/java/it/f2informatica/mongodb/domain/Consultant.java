package it.f2informatica.mongodb.domain;

import com.google.common.collect.Lists;
import it.f2informatica.datastore.constant.Gender;
import it.f2informatica.datastore.constant.MaritalStatus;
import it.f2informatica.datastore.domain.MongoDBDocument;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Document
public class Consultant extends Identifiable<String> implements MongoDBDocument {
	private static final long serialVersionUID = 6643483509995605407L;

	@Indexed(unique = true)
	private String consultantNo;

	@Indexed(direction = IndexDirection.DESCENDING)
	private Date registrationDate;

	private String fiscalCode;

	private String email;

	@Indexed(direction = IndexDirection.ASCENDING)
	private String firstName;

	@Indexed(direction = IndexDirection.ASCENDING)
	private String lastName;

	private Gender gender;

	private String phoneNumber;

	private String mobileNumber;

	private Date birthDate;

	private String birthCity;

	private String birthCountry;

	private String identityCardNo;

	private String passportNo;

	private MaritalStatus maritalStatus;

	private List<Experience> experiences = Lists.newArrayList();

	private List<Education> educationList = Lists.newArrayList();

	private List<Language> languages = Lists.newArrayList();

	private List<String> skills = Lists.newArrayList();

	private String interests;

	private Address residence;

	private Address domicile;

	private String curriculum; //TODO: GridFSFile

	private String photo; //TODO: GridFSFile

}
