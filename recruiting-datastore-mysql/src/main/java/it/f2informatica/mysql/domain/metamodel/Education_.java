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
package it.f2informatica.mysql.domain.metamodel;

import it.f2informatica.mysql.domain.Consultant;
import it.f2informatica.mysql.domain.Education;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Education.class)
public class Education_ {

  public static volatile SingularAttribute<Education, Long> id;
  public static volatile SingularAttribute<Education, String> schoolName;
  public static volatile SingularAttribute<Education, Integer> startYear;
  public static volatile SingularAttribute<Education, Integer> endYear;
  public static volatile SingularAttribute<Education, Boolean> current;
  public static volatile SingularAttribute<Education, String> schoolDegree;
  public static volatile SingularAttribute<Education, String> fieldsOfStudy;
  public static volatile SingularAttribute<Education, String> grade;
  public static volatile SingularAttribute<Education, String> activities;
  public static volatile SingularAttribute<Education, String> description;
  public static volatile SingularAttribute<Education, Consultant> consultant;

}
