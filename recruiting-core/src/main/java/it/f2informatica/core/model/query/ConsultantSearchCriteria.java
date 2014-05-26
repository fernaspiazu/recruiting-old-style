package it.f2informatica.core.model.query;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class ConsultantSearchCriteria {

  private String name;

  private String lastName;

  private String skills;

}
