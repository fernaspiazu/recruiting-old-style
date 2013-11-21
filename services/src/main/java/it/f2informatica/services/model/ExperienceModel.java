package it.f2informatica.services.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;

@Data
@EqualsAndHashCode
@ToString
public class ExperienceModel {

	private String companyName;

	private String function;

	private String location;

	private Date periodFrom;

	private Date periodTo;

	private boolean isCurrent;

	private String description;

}
