package it.f2informatica.services.domain.consultant.impl;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import it.f2informatica.mongodb.domain.Consultant;
import it.f2informatica.mongodb.domain.Profile;
import it.f2informatica.services.responses.ConsultantPaginatedResponse;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

import static it.f2informatica.services.responses.builders.ConsultantPaginatedResponseBuilder.consultantPaginatedResponse;

class ConsultantToConsultantPaginationResponseTransformer
		implements Function<Consultant, ConsultantPaginatedResponse> {
	private static final int MAX_SKILLS = 3;
	private static final String COMMA_DELIMITER = ", ";

	@Override
	public ConsultantPaginatedResponse apply(Consultant input) {
		return consultantPaginatedResponse()
				.withConsultantId(input.getId())
				.withConsultantNo(input.getConsultantNo())
				.withFirstName(input.getFirstName())
				.withLastName(input.getLastName())
				.withAge(calculateAge(input.getBirthDate()))
				.withSkills(getSkillSetInString(input.getProfile()))
				.build();
	}

	private int calculateAge(Date birthDate) {
		return new Period(new DateTime(birthDate), new DateTime()).getYears();
	}

	private String getSkillSetInString(Profile profile) {
		if (profile == null) {
			return "";
		}
		List<String> skillsLimited = Lists.newArrayList(Iterables.limit(profile.getSkills(), MAX_SKILLS));
		return StringUtils.collectionToDelimitedString(skillsLimited, COMMA_DELIMITER);
	}

}
