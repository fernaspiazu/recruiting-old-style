package it.f2informatica.services.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode
@ToString
public class ExperienceModel implements Serializable {
	private static final long serialVersionUID = 7350451606929379410L;

	private String id;

	private String companyName;

	private String function;

	private String location;

	private Date periodFrom;

	private String periodFromStr;

	private Date periodTo;

	private String periodToStr;

	private boolean current;

	private String description;

}
