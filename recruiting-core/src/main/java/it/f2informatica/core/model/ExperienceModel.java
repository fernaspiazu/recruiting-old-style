package it.f2informatica.core.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode(exclude = {"description"})
@ToString
public class ExperienceModel implements Serializable {
	private static final long serialVersionUID = 7350451606929379410L;

	private String id;

	private String companyName;

	private String position;

	private String locality;

	private Date periodFrom;

	private String formattedPeriodFrom;

	private String monthFrom;

	private String yearFrom;

	private Date periodTo;

	private String formattedPeriodTo;

	private String monthTo;

	private String yearTo;

	private boolean current;

	private String totalPeriodElapsed;

	private String description;

	private String submitEvent;

}
