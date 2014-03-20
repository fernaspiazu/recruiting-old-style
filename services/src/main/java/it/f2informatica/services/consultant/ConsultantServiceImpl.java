package it.f2informatica.services.consultant;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import it.f2informatica.services.gateway.ConsultantRepositoryGateway;
import it.f2informatica.services.model.ConsultantModel;
import it.f2informatica.services.model.ExperienceModel;
import it.f2informatica.services.model.LanguageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import static it.f2informatica.services.model.builder.ConsultantModelBuilder.consultantModel;
import static it.f2informatica.services.model.builder.ExperienceModelBuilder.experienceModel;
import static it.f2informatica.services.model.builder.LanguageModelBuilder.languageModel;

@Service
public class ConsultantServiceImpl implements ConsultantService {
	private static final String YEAR_MONTH_MILLISECONDS_FORMAT = "yyyyMMSSS";

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
	public ExperienceModel buildNewExperienceModel() {
		return experienceModel().build();
	}

	@Override
	public LanguageModel buildNewLanguageModel() {
		return languageModel().build();
	}

	@Override
	public Page<ConsultantModel> showAllConsultants(Pageable pageable) {
		return consultantRepositoryGateway.findAllConsultants(pageable);
	}

	@Override
	public ConsultantModel savePersonalDetails(ConsultantModel consultantModel) {
		return consultantRepositoryGateway.savePersonalDetails(consultantModel);
	}

	@Override
	public ConsultantModel findConsultantById(String consultantId) {
		return consultantRepositoryGateway.findOneConsultant(consultantId);
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
	public boolean addConsultantExperience(ExperienceModel experienceModel, String consultantId) {
		return consultantRepositoryGateway.addExperience(experienceModel, consultantId);
	}

	@Override
	public boolean updateConsultantExperience(ExperienceModel experienceModel, String consultantId) {
		return consultantRepositoryGateway.updateExperience(experienceModel, consultantId);
	}

	@Override
	public void removeExperience(String consultantId, String experienceId) {
		consultantRepositoryGateway.removeExperience(consultantId, experienceId);
	}

	@Override
	public ExperienceModel findExperience(String consultantId, String experienceId) {
		return consultantRepositoryGateway.findOneExperience(consultantId, experienceId);
	}

	@Override
	public List<ExperienceModel> findExperiences(String consultantId) {
		return consultantRepositoryGateway.findExperiences(consultantId);
	}

	@Override
	public List<ExperienceModel> findMinimalExperiences(String consultantId) {
		return consultantRepositoryGateway.findMinimalExperiences(consultantId);
	}

	@Override
	public boolean addLanguage(LanguageModel languageModel, String consultantId) {
		return consultantRepositoryGateway.addLanguage(languageModel, consultantId);
	}

	@Override
	public boolean addLanguages(LanguageModel[] languageModelArray, String consultantId) {
		return consultantRepositoryGateway.addLanguages(languageModelArray, consultantId);
	}

	@Override
	public boolean addSkills(String[] skills, String consultantId) {
		return consultantRepositoryGateway.addSkills(removeBlankContentFromArray(skills), consultantId);
	}

	private String[] removeBlankContentFromArray(String[] skillsToProcess) {
		List<String> listOfSkill = Lists.newArrayList(skillsToProcess);
		Iterables.removeIf(listOfSkill, new Predicate<String>() {
			@Override
			public boolean apply(String input) {
				return input == null || input.isEmpty();
			}
		});
		return Iterables.toArray(listOfSkill, String.class);
	}

}
