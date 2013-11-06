package it.f2informatica.mongodb.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Document
public class Consultant extends Identifiable<String> {

	@Indexed(unique = true)
	private String consultantNo;

	@Indexed(direction = IndexDirection.DESCENDING)
	private Date registrationDate;

	@Indexed(direction = IndexDirection.DESCENDING)
	private Date startActivityDate;

	@DBRef
	private Person person;

	@DBRef
	private Profile profile;

	private List<Skill> skills;

	private String contract;

	private String curriculum;

	private boolean enabled = false;

}
