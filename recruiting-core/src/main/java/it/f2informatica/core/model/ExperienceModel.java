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
package it.f2informatica.core.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode(exclude = {"description"})
@ToString
public class ExperienceModel implements Serializable, Comparable<ExperienceModel> {
	private static final long serialVersionUID = 7350451606929379410L;

	private String id;

	private String companyName;

	private String position;

	private String locality;

	private Date periodFrom;

	private String formattedPeriodFrom;

	private String monthFrom;

	private String yearFrom;

	private Date periodTo;

	private String formattedPeriodTo;

	private String monthTo;

	private String yearTo;

	private boolean current;

	private String totalPeriodElapsed;

	private String description;

	private String submitEvent;

	@Override
	public int compareTo(ExperienceModel o) {
		if (this.periodFrom.before(o.getPeriodFrom()))
			return -1;
		else if (this.periodFrom.after(o.getPeriodFrom()))
			return 1;
		return 0;
	}
}
