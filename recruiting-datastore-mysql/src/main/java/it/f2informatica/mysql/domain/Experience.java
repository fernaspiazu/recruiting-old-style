package it.f2informatica.mysql.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode
@ToString(exclude = "consultant")
@Entity
@Table(name = "experience")
public class Experience implements Serializable {
  private static final long serialVersionUID = -3499814695049172754L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "company")
  private String companyName;

  @Column(name = "job_position")
  private String jobPosition;

  @Column(name = "location")
  private String location;

  @Temporal(TemporalType.DATE)
  @Column(name = "period_from")
  private Date periodFrom;

  @Temporal(TemporalType.DATE)
  @Column(name = "period_to")
  private Date periodTo;

  @Column(name = "is_current")
  private boolean current;

  @Column(name = "description")
  private String description;

  @ManyToOne(fetch = FetchType.LAZY, targetEntity = Consultant.class)
  @JoinColumn(name = "consultant_id")
  private Consultant consultant;
}
