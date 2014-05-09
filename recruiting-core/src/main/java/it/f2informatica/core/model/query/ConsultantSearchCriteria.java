package it.f2informatica.core.model.query;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode
@ToString
public class ConsultantSearchCriteria {

  private String name;

  private String lastName;

  private String skills;

}
