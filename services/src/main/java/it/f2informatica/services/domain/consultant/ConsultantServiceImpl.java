package it.f2informatica.services.domain.consultant;

import it.f2informatica.services.gateway.ConsultantRepositoryGateway;
import it.f2informatica.services.model.ConsultantModel;
import it.f2informatica.services.model.ExperienceModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

@Service
public class ConsultantServiceImpl implements ConsultantService {
	private static final String YEAR_MONTH_MILLISECONDS_FORMAT = "yyyyMMSSS";

	@Autowired
	private ConsultantRepositoryGateway consultantRepositoryGateway;

	@Override
	public Page<ConsultantModel> showAllConsultants(Pageable pageable) {
		return consultantRepositoryGateway.findAllConsultants(pageable);
	}

	@Override
	public ConsultantModel registerConsultantMasterData(ConsultantModel consultantModel) {
		return consultantRepositoryGateway.saveMasterData(consultantModel);
	}

	@Override
	public ConsultantModel findConsultantById(String consultantId) {
		return consultantRepositoryGateway.findConsultantById(consultantId);
	}

	@Override
	public String generateConsultantNumber() {
		String uuid = UUID.randomUUID().toString();
		String[] components = uuid.split("-");
		return getTimePrefixFormat() + "-" + components[components.length - 1].toUpperCase();
	}

	private String getTimePrefixFormat() {
		DateFormat dateFormat = new SimpleDateFormat(YEAR_MONTH_MILLISECONDS_FORMAT);
		return dateFormat.format(Calendar.getInstance().getTime());
	}

	@Override
	public boolean saveConsultantExperience(ExperienceModel experienceModel, String consultantId) {
		return consultantRepositoryGateway.saveConsultantExperience(experienceModel, consultantId);
	}

	@Override
	public List<ExperienceModel> findExperiences(String consultantId) {
		return consultantRepositoryGateway.findExperiencesByConsultantId(consultantId);
	}

}
