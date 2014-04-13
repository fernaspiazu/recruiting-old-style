package it.f2informatica.core.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

@Data
@EqualsAndHashCode
@ToString
public class EducationModel implements Serializable {
  private static final long serialVersionUID = 3982326514709392235L;

  private String id;

  private String school;

  private Integer startYear;

  private Integer endYear;

  private String schoolDegree;

  private String schoolFieldOfStudy;

  private String schoolGrade;

  private String schoolActivities;

  private boolean current;

  private String description;

}
