package it.f2informatica.services.model;

import it.f2informatica.datastore.model.DataModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode
@ToString
public class EducationModel implements DataModel {
	private static final long serialVersionUID = 3982326514709392235L;

	private String school;

	private int startYear;

	private int endYear;

	private String schoolDegree;

	private String schoolFieldOfStudy;

	private String schoolGrade;

	private String schoolActivities;

	private boolean isCurrent;

	private String description;

}
