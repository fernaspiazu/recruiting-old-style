package it.f2informatica.services.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

@Data
@EqualsAndHashCode
@ToString
public class EducationModel implements Serializable {

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