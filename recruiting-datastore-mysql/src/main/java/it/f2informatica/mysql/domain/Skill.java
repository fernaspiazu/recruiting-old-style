package it.f2informatica.mysql.domain;

import it.f2informatica.mysql.domain.pk.SkillPK;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Data
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "skills")
public class Skill implements Serializable {
  private static final long serialVersionUID = -6986844527781037145L;

  @EmbeddedId
  @AttributeOverride(name = "skill", column = @Column(name = "skill"))
  private SkillPK id;
}
