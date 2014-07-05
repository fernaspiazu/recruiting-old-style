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
import it.f2informatica.core.model.*;
import it.f2informatica.mongodb.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import static it.f2informatica.core.model.builder.ConsultantModelBuilder.consultantModel;

@Component("consultantToModelConverter")
public class MongoDBConsultantToModelConverter
	extends EntityToModelConverter<Consultant, ConsultantModel> {

	@Autowired
	@Qualifier("addressToModelConverter")
	private EntityToModelConverter<Address, AddressModel> addressToModelConverter;

	@Autowired
	@Qualifier("experienceToModelConverter")
	private EntityToModelConverter<Experience, ExperienceModel> experienceToModelConverter;

	@Autowired
	@Qualifier("educationToModelConverter")
	private EntityToModelConverter<Education, EducationModel> educationToModelConverter;

	@Autowired
	@Qualifier("languageToModelConverter")
	private EntityToModelConverter<Language, LanguageModel> languageToModelConverter;

	@Override
	public ConsultantModel convert(Consultant consultant) {
		return (consultant == null) ? null :
			consultantModel()
				.withId(String.valueOf(consultant.getId()))
				.withConsultantNo(consultant.getConsultantNo())
				.withRegistrationDate(consultant.getRegistrationDate())
				.withFirstName(consultant.getFirstName())
				.withLastName(consultant.getLastName())
				.withGender(consultant.getGender())
				.withEmail(consultant.getEmail())
				.withFiscalCode(consultant.getFiscalCode())
				.withBirthDate(consultant.getBirthDate())
				.withBirthCity(consultant.getBirthCity())
				.withBirthCountry(consultant.getBirthCountry())
				.withPhoneNumber(consultant.getPhoneNumber())
				.withMobileNo(consultant.getMobileNumber())
				.withIdentityCardNo(consultant.getIdentityCardNo())
				.withPassportNo(consultant.getPassportNo())
				.withResidence(addressToModelConverter.convert(consultant.getResidence()))
				.withDomicile(addressToModelConverter.convert(consultant.getDomicile()))
				.withExperiencesIn(experienceToModelConverter.convertList(consultant.getExperiences()))
				.withEducationIn(educationToModelConverter.convertList(consultant.getEducationList()))
				.speakingLanguages(languageToModelConverter.convertList(consultant.getLanguages()))
				.withSkills(consultant.getSkills())
				.withInterestsIn(consultant.getInterests())
				.build();
	}

}
