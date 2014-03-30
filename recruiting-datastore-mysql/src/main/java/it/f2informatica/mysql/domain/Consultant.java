package it.f2informatica.mysql.domain;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "consultant")
public class Consultant implements Serializable {
  private static final long serialVersionUID = -2787221115919947228L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "consultant_no", unique = true, nullable = false)
  private String consultantNo;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "registr_date")
  private Date registrationDate;

  @Column(name = "fiscal_code")
  private String fiscalCode;

  @Column(name = "email")
  private String email;

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "last_name")
  private String lastName;

  @Column(name = "gender")
  private String gender;

  @Column(name = "phone_number")
  private String phoneNumber;

  @Column(name = "mobile_number")
  private String mobileNumber;

  @Temporal(TemporalType.DATE)
  @Column(name = "birth_date")
  private Date birthDate;

  @Column(name = "birth_city")
  private String birthCity;

  @Column(name = "birth_country")
  private String birthCountry;

  @Column(name = "identity_card")
  private String identityCard;

  @Column(name = "interests")
  private String interests;

  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "id.consultant", targetEntity = Skill.class)
  private Set<Skill> skills = Sets.newHashSet();

  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "consultant", targetEntity = Experience.class)
  private List<Experience> experiences = Lists.newArrayList();

  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "consultant", targetEntity = Education.class)
  private List<Education> educations = Lists.newArrayList();

  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "id.consultant", targetEntity = Language.class)
  private Set<Language> languages = Sets.newHashSet();

  @OneToOne(fetch = FetchType.LAZY, targetEntity = Address.class)
  @JoinColumn(name = "residence")
  private Address residence;

  @OneToOne(fetch = FetchType.LAZY, targetEntity = Address.class)
  @JoinColumn(name = "domicile")
  private Address domicile;

}
