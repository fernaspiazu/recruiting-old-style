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
package it.f2informatica.core.gateway.mongodb.converter;

import it.f2informatica.core.gateway.EntityToModelConverter;
import it.f2informatica.core.model.ExperienceModel;
import it.f2informatica.mongodb.domain.Experience;
import org.springframework.stereotype.Component;

import static it.f2informatica.core.model.builder.ExperienceModelBuilder.experienceModel;

@Component("experienceToModelConverter")
public class MongoDBExperienceToModelConverter
	extends EntityToModelConverter<Experience, ExperienceModel> {

	@Override
	public ExperienceModel convert(Experience experience) {
		return (experience == null) ? null :
			experienceModel()
				.withId(experience.getId())
				.inCompany(experience.getCompanyName())
				.withPosition(experience.getPosition())
				.locatedAt(experience.getLocation())
				.fromPeriod(experience.getPeriodFrom())
				.toPeriod(experience.getPeriodTo())
				.isThisTheCurrentJob(experience.isCurrent())
				.withDescription(experience.getDescription())
				.build();
	}

}
