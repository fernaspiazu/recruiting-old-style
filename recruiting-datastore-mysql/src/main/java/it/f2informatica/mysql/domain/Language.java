package it.f2informatica.mysql.domain;

import it.f2informatica.mysql.domain.pk.LanguagePK;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Data
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "languages")
public class Language implements Serializable {
  private static final long serialVersionUID = -5571076826003486772L;

  @EmbeddedId
  @AttributeOverride(name = "lang", column = @Column(name = "lang"))
  private LanguagePK id;

  @Column(name = "proficiency")
  private String proficiency;

}
