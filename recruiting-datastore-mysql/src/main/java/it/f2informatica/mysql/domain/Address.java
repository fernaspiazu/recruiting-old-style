package it.f2informatica.mysql.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Data
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "address")
public class Address implements Serializable {
  private static final long serialVersionUID = -189944310625378498L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "street")
  private String street;

  @Column(name = "house_no")
  private String houseNo;

  @Column(name = "zip_code")
  private String zipCode;

  @Column(name = "city")
  private String city;

  @Column(name = "province")
  private String province;

  @Column(name = "region")
  private String region;

  @Column(name = "country")
  private String country;

  @OneToOne(fetch = FetchType.LAZY, targetEntity = Consultant.class)
  private Consultant consultant;

}
