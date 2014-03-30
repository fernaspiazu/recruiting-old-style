package it.f2informatica.mysql.domain.pk;

import it.f2informatica.mysql.domain.Consultant;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Data
@EqualsAndHashCode
@ToString
@Embeddable
public class SkillPK implements Serializable {
  private static final long serialVersionUID = 3869057767286602427L;

  private String skill;

  @ManyToOne(fetch = FetchType.LAZY, targetEntity = Consultant.class)
  private Consultant consultant;

}
