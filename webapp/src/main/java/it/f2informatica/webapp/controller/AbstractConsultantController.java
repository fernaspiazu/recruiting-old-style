package it.f2informatica.webapp.controller;

import com.google.common.collect.Lists;
import it.f2informatica.datastore.constant.LanguageProficiency;
import it.f2informatica.services.consultant.ConsultantService;
import it.f2informatica.services.model.ExperienceModel;
import it.f2informatica.services.model.LanguageModel;
import it.f2informatica.webapp.utils.MonthHelper;
import it.f2informatica.webapp.utils.PeriodResolver;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static it.f2informatica.services.model.builder.LanguageModelBuilder.languageModel;

public abstract class AbstractConsultantController {
	protected static final String CONSULTANT_ID_SESSION_ATTR = "consultantId";

	@Autowired
	protected MonthHelper monthHelper;

	@Autowired
	protected PeriodResolver periodResolver;

	@Autowired
	protected ConsultantService consultantService;

	protected void formatExperiencePeriods(ExperienceModel experience) {
		experience.setPeriodFromStr(periodResolver.periodToString(experience.getPeriodFrom()));
		experience.setPeriodToStr(periodResolver.periodToString(experience.getPeriodTo()));
	}

	protected List<ExperienceModel> findExperiences(String consultantId) {
		List<ExperienceModel> experiences = consultantService.findExperiences(consultantId);
		for (ExperienceModel experience : experiences) {
			formatExperiencePeriods(experience);
		}
		return experiences;
	}

	protected List<ExperienceModel> findMinimalExperiences(String consultantId) {
		List<ExperienceModel> experiences = consultantService.findMinimalExperiences(consultantId);
		for (ExperienceModel experience : experiences) {
			reduceExperienceDescriptionToHundredCharacters(experience);
			formatExperiencePeriods(experience);
		}
		return experiences;
	}

	private void reduceExperienceDescriptionToHundredCharacters(ExperienceModel experience) {
		String description = experience.getDescription();
		if (description.length() >= 100) {
			experience.setDescription(description.substring(0, 96).concat("..."));
		}
	}

	protected LanguageModel[] getLanguageModels(String[] languages, String[] proficiencies) {
		List<LanguageModel> languagesModel = Lists.newArrayList();
		for (int i = 0; i < languages.length; i++) {
			if (StringUtils.isEmpty(languages[i].trim())) {
				continue;
			}
			languagesModel.add(languageModel(languages[i]).withProficiency(resolveProficiency(proficiencies[i])).build());
		}
		return languagesModel.toArray(new LanguageModel[languagesModel.size()]);
	}

	private LanguageProficiency resolveProficiency(String proficiency) {
		for (LanguageProficiency languageProficiency : LanguageProficiency.values()) {
			if (proficiency.equals(languageProficiency.toString())) {
				return languageProficiency;
			}
		}
		return LanguageProficiency.ELEMENTARY;
	}

}
