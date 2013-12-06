package it.f2informatica.mongodb.domain;

import it.f2informatica.datastore.domain.MongoDBDocument;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;

@Data
@EqualsAndHashCode
@ToString
public class Experience implements MongoDBDocument {

	private String companyName;

	private String function;

	private String location;

	private Date periodFrom;

	private Date periodTo;

	private boolean current;

	private String description;

}
