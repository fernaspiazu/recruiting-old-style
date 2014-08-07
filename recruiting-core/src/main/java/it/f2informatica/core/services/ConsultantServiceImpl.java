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
package it.f2informatica.core.services;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import it.f2informatica.core.gateway.ConsultantRepositoryGateway;
import it.f2informatica.core.model.ConsultantModel;
import it.f2informatica.core.model.EducationModel;
import it.f2informatica.core.model.ExperienceModel;
import it.f2informatica.core.model.LanguageModel;
import it.f2informatica.core.model.query.ConsultantSearchCriteria;
import org.apache.commons.lang3.ArrayUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import static it.f2informatica.core.model.builder.ConsultantModelBuilder.consultantModel;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Service
public class ConsultantServiceImpl implements ConsultantService {
	private static final String YEAR_MONTH_MILLISECONDS_FORMAT = "yyyyMMSSS";

	private static final Predicate<LanguageModel> DOES_NOT_HAVE_ITEM_TO_ADD = new Predicate<LanguageModel>() {
		@Override
		public boolean apply(LanguageModel input) {
			return input == null
				|| isBlank(input.getLanguage())
				|| isBlank(input.getProficiency());
		}
	};

	@Autowired
	private ConsultantRepositoryGateway consultantRepositoryGateway;

	@Override
	public ConsultantModel buildNewConsultantModel() {
		return consultantModel()
			.withConsultantNo(generateConsultantNumber())
			.withRegistrationDate(Calendar.getInstance().getTime())
			.build();
	}

	@Override
	public Page<ConsultantModel> paginateConsultants(Pageable pageable) {
		return consultantRepositoryGateway.findAllConsultants(pageable);
	}

	@Override
	public Page<ConsultantModel> paginateConsultants(ConsultantSearchCriteria searchCriteria, Pageable pageable) {
		return consultantRepositoryGateway.paginateConsultants(searchCriteria, pageable);
	}

	@Override
	public ConsultantModel savePersonalDetails(ConsultantModel consultantModel) {
		return consultantRepositoryGateway.savePersonalDetails(consultantModel);
	}

	@Override
	public void updatePersonalDetails(ConsultantModel consultantModel, String consultantId) {
		consultantRepositoryGateway.updatePersonalDetails(consultantModel, consultantId);
	}

	@Override
	public Optional<ConsultantModel> findConsultantById(String consultantId) {
		return Optional.fromNullable(consultantRepositoryGateway.findOneConsultant(consultantId));
	}

	@Override
	public String generateConsultantNumber() {
		String[] UUIDs = UUID.randomUUID().toString().split("-");
		return getTimePrefixFormat() + UUIDs[UUIDs.length - 1].toUpperCase();
	}

	private String getTimePrefixFormat() {
		return new DateTime().toString(YEAR_MONTH_MILLISECONDS_FORMAT);
	}

	@Override
	public void addConsultantExperience(ExperienceModel experienceModel, String consultantId) {
		consultantRepositoryGateway.addExperience(experienceModel, consultantId);
	}

	@Override
	public void updateConsultantExperience(ExperienceModel experienceModel, String consultantId) {
		consultantRepositoryGateway.updateExperience(experienceModel, consultantId);
	}

	@Override
	public void removeExperience(String consultantId, String experienceId) {
		consultantRepositoryGateway.removeExperience(consultantId, experienceId);
	}

	@Override
	public Optional<ExperienceModel> findExperience(String consultantId, String experienceId) {
		return Optional.fromNullable(consultantRepositoryGateway.findOneExperience(consultantId, experienceId));
	}

	@Override
	public void addLanguages(LanguageModel[] languageModelArray, String consultantId) {
		consultantRepositoryGateway.addLanguages(removeFurtherEmptyLanguages(languageModelArray), consultantId);
	}

	private LanguageModel[] removeFurtherEmptyLanguages(LanguageModel[] languageModels) {
		List<LanguageModel> languages = Lists.newArrayList(languageModels);
		Iterables.removeIf(languages, DOES_NOT_HAVE_ITEM_TO_ADD);
		return Iterables.toArray(languages, LanguageModel.class);
	}

	@Override
	public void addSkills(String skills, String consultantId) {
		String[] tmpSkillArray = isNotBlank(skills) ? makeItArray(skills) : ArrayUtils.EMPTY_STRING_ARRAY;
		consultantRepositoryGateway.addSkills(removeEmptyElementsFrom(tmpSkillArray), consultantId);
	}

	private String[] makeItArray(String skills) {
		return skills.replaceAll("\\s+", "").split(",+");
	}

	private String[] removeEmptyElementsFrom(String[] skills) {
		return FluentIterable.from(Arrays.asList(skills)).filter(new Predicate<String>() {
			@Override
			public boolean apply(String input) {
				return isNotBlank(input);
			}
		}).toArray(String.class);
	}

	@Override
	public Optional<EducationModel> findEducation(String consultantId, String educationId) {
		return Optional.fromNullable(consultantRepositoryGateway.findOneEducation(consultantId, educationId));
	}

	@Override
	public void addConsultantEducation(EducationModel educationModel, String consultantId) {
		consultantRepositoryGateway.addEducation(educationModel, consultantId);
	}

	@Override
	public void updateConsultantEducation(EducationModel educationModel, String consultantId) {
		consultantRepositoryGateway.updateEducation(educationModel, consultantId);
	}

	@Override
	public void removeEducation(String consultantId, String educationId) {
		consultantRepositoryGateway.removeEducation(consultantId, educationId);
	}

}
