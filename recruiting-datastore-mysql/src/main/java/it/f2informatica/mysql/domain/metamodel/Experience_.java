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
import it.f2informatica.mysql.domain.Experience;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.util.Date;

@StaticMetamodel(Experience.class)
public class Experience_ {

	public static volatile SingularAttribute<Experience, Long> id;
	public static volatile SingularAttribute<Experience, String> companyName;
	public static volatile SingularAttribute<Experience, String> jobPosition;
	public static volatile SingularAttribute<Experience, String> location;
	public static volatile SingularAttribute<Experience, Date> periodFrom;
	public static volatile SingularAttribute<Experience, Date> periodTo;
	public static volatile SingularAttribute<Experience, Boolean> current;
	public static volatile SingularAttribute<Experience, String> description;
	public static volatile SingularAttribute<Experience, Consultant> consultant;

}
