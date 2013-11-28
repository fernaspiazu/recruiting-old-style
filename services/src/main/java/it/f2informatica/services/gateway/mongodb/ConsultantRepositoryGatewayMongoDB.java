package it.f2informatica.services.gateway.mongodb;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import it.f2informatica.mongodb.domain.Consultant;
import it.f2informatica.mongodb.repositories.ConsultantRepository;
import it.f2informatica.services.gateway.ConsultantRepositoryGateway;
import it.f2informatica.services.gateway.EntityToModelConverter;
import it.f2informatica.services.model.ConsultantModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static it.f2informatica.mongodb.domain.builder.ConsultantBuilder.consultant;
import static it.f2informatica.services.model.builder.ConsultantModelBuilder.consultantModel;

@Service
public class ConsultantRepositoryGatewayMongoDB implements ConsultantRepositoryGateway {

	@Autowired
	private ConsultantRepository consultantRepository;

	@Autowired
	@Qualifier("consultantToModelConverter")
	private EntityToModelConverter<Consultant, ConsultantModel> consultantToModelConverter;

	@Override
	public Page<ConsultantModel> findAllConsultants(Pageable pageable) {
		Page<Consultant> consultantPage = consultantRepository.findAll(pageable);
		return new PageImpl<>(Lists.newArrayList(Iterables.transform(consultantPage,
			new Function<Consultant, ConsultantModel>() {
				@Override
				public ConsultantModel apply(Consultant consultant) {
					return consultantModel()
						.withId(consultant.getId())
						.withConsultantNo(consultant.getConsultantNo())
						.withRegistrationDate(consultant.getRegistrationDate())
						.withFirstName(consultant.getFirstName())
						.withLastName(consultant.getLastName())
						.withBirthDate(consultant.getBirthDate())
						.build();
				}
			}
		)));
	}

	@Override
	public ConsultantModel savePersonalData(ConsultantModel consultantModel) {
		Consultant consultant = consultant()
			.withConsultantNo(consultantModel.getConsultantNo())
			.withRegistrationDate(consultantModel.getRegistrationDate())
			.withFirstName(consultantModel.getFirstName())
			.withLastName(consultantModel.getLastName())
			.withGender(consultantModel.getGender())
			.withEmail(consultantModel.getEmail())
			.withFiscalCode(consultantModel.getFiscalCode())
			.withBirthDate(consultantModel.getBirthDate())
			.withBirthCity(consultantModel.getBirthCity())
			.withBirthCountry(consultantModel.getBirthCountry())
			.withPhoneNumber(consultantModel.getPhoneNumber())
			.withMobileNo(consultantModel.getMobileNumber())
			.build();
		Consultant consultantRegistered = consultantRepository.save(consultant);
		return consultantToModelConverter.convert(consultantRegistered);
	}

}
