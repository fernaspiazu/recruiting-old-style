package it.f2informatica.webapp.gateway;

import it.f2informatica.services.domain.consultant.ConsultantService;
import it.f2informatica.services.model.ConsultantModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.UUID;

import static it.f2informatica.services.model.builder.ConsultantModelBuilder.consultantModel;

@Service
public class ConsultantServiceGateway {

	@Autowired
	private ConsultantService consultantService;

	public ConsultantModel findConsultantById(String consultantId) {
		return consultantService.findConsultantById(consultantId);
	}

	public Page<ConsultantModel> showAllConsultants(Pageable pageable) {
		return consultantService.showAllConsultants(pageable);
	}

	public ConsultantModel registerConsultantMasterData(ConsultantModel consultantModel) {
		return consultantService.registerConsultantMasterData(consultantModel);
	}

	public ConsultantModel prepareForNewConsultantModel() {
		return consultantModel()
				.withConsultantNo(consultantService.generateConsultantNumber())
				.withRegistrationDate(Calendar.getInstance().getTime())
				.build();
	}
}
