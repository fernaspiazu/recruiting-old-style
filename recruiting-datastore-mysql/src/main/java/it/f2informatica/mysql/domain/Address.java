/*
 * =============================================================================
 *
 *   Copyright (c) 2014, Fernando Aspiazu
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 * =============================================================================
 */
package it.f2informatica.mysql.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Data
@EqualsAndHashCode
@ToString(exclude = {"consultantResidence", "consultantDomicile"})
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

  @OneToOne(fetch = FetchType.LAZY, targetEntity = Consultant.class, mappedBy = "residence")
  private Consultant consultantResidence;

  @OneToOne(fetch = FetchType.LAZY, targetEntity = Consultant.class, mappedBy = "domicile")
  private Consultant consultantDomicile;

}
