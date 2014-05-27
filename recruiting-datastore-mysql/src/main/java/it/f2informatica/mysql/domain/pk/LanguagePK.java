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
package it.f2informatica.mysql.domain.pk;

import it.f2informatica.mysql.domain.Consultant;
import lombok.*;

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
