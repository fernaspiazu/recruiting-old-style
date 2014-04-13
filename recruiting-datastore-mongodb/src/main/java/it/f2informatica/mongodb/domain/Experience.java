package it.f2informatica.mongodb.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;

@Data
@EqualsAndHashCode
@ToString
public class Experience {

  private String id;

  private String companyName;

  private String position;

  private String location;

  private Date periodFrom;

  private Date periodTo;

  private boolean current;

  private String description;

}
