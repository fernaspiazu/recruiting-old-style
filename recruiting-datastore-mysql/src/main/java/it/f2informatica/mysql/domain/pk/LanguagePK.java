package it.f2informatica.mysql.domain.pk;

import it.f2informatica.mysql.domain.Consultant;
import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Data
@EqualsAndHashCode
@ToString(exclude = "consultant")
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class LanguagePK implements Serializable {
  private static final long serialVersionUID = 5238767976519604774L;

  private String lang;

  @ManyToOne(fetch = FetchType.LAZY, targetEntity = Consultant.class)
  private Consultant consultant;

}
