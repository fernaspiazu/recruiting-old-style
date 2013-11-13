package it.f2informatica.services.domain.consultant.impl;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import it.f2informatica.mongodb.domain.Consultant;
import it.f2informatica.mongodb.domain.Profile;
import it.f2informatica.mongodb.repositories.ConsultantRepository;
import it.f2informatica.services.domain.consultant.ConsultantService;
import it.f2informatica.services.responses.ConsultantPaginatedResponse;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;

import static it.f2informatica.services.responses.builders.ConsultantPaginatedResponseBuilder.consultantPaginatedResponse;

@Service
public class ConsultantServiceImpl implements ConsultantService {
	public static final String COMMA_DELIMITER = ", ";

	@Autowired
	private ConsultantRepository consultantRepository;

	@Override
	public Page<ConsultantPaginatedResponse> findAll(Pageable pageable) {
		Page<Consultant> consultants = consultantRepository.findAll(pageable);
		return new PageImpl<>(Lists.newArrayList(
				Iterables.transform(consultants, transformToConsultantPaginatedResponse())
		));
	}

	private Function<Consultant, ConsultantPaginatedResponse> transformToConsultantPaginatedResponse() {
		return new Function<Consultant, ConsultantPaginatedResponse>() {
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
		};
	}

	private int calculateAge(Date birthDate) {
		return new Period(new DateTime(birthDate), new DateTime()).getYears();
	}

	private String getSkillSetInString(Profile profile) {
		return profile == null ? ""
				: StringUtils.collectionToDelimitedString(profile.getSkills(), COMMA_DELIMITER);
	}

}
