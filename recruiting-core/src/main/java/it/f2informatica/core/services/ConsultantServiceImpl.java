package it.f2informatica.core.services;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import it.f2informatica.core.gateway.ConsultantRepositoryGateway;
import it.f2informatica.core.model.ConsultantModel;
import it.f2informatica.core.model.EducationModel;
import it.f2informatica.core.model.ExperienceModel;
import it.f2informatica.core.model.LanguageModel;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import static it.f2informatica.core.model.builder.ConsultantModelBuilder.consultantModel;
import static org.apache.commons.lang3.StringUtils.isBlank;

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
  public ConsultantModel savePersonalDetails(ConsultantModel consultantModel) {
    return consultantRepositoryGateway.savePersonalDetails(consultantModel);
  }

  @Override
  public void updatePersonalDetails(ConsultantModel consultantModel, String consultantId) {
    consultantRepositoryGateway.updatePersonalDetails(consultantModel, consultantId);
  }

  @Override
  public ConsultantModel findConsultantById(String consultantId) {
    return consultantRepositoryGateway.findOneConsultant(consultantId);
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
  public boolean addConsultantExperience(ExperienceModel experienceModel, String consultantId) {
    return consultantRepositoryGateway.addExperience(experienceModel, consultantId);
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
  public ExperienceModel findExperience(String consultantId, String experienceId) {
    return consultantRepositoryGateway.findOneExperience(consultantId, experienceId);
  }

  @Override
  public boolean addLanguages(LanguageModel[] languageModelArray, String consultantId) {
    return consultantRepositoryGateway.addLanguages(removeFurtherEmptyLanguages(languageModelArray), consultantId);
  }

  private LanguageModel[] removeFurtherEmptyLanguages(LanguageModel[] languageModels) {
    List<LanguageModel> languages = Lists.newArrayList(languageModels);
    Iterables.removeIf(languages, DOES_NOT_HAVE_ITEM_TO_ADD);
    return Iterables.toArray(languages, LanguageModel.class);
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
        return isBlank(input);
      }
    });
    return Iterables.toArray(listOfSkill, String.class);
  }

  @Override
  public EducationModel findEducation(String consultantId, String educationId) {
    return consultantRepositoryGateway.findOneEducation(consultantId, educationId);
  }

  @Override
  public boolean addConsultantEducation(EducationModel educationModel, String consultantId) {
    return consultantRepositoryGateway.addEducation(educationModel, consultantId);
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
