package it.f2informatica.mysql.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

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

  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "consultant", targetEntity = Experience.class)
  private List<Experience> experiences;

  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "consultant", targetEntity = Education.class)
  private List<Education> educations;

  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "")
  private List<Language> languages;

}
