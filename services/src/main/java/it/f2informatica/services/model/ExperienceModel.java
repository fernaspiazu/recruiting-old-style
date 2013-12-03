package it.f2informatica.services.model;

import it.f2informatica.datastore.model.DataModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;

@Data
@EqualsAndHashCode
@ToString
public class ExperienceModel implements DataModel {

	private String companyName;

	private String function;

	private String location;

	private Date periodFrom;

	private Date periodTo;

	private boolean isCurrent;

	private String description;

}
