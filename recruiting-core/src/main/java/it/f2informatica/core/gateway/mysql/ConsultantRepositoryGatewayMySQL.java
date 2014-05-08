package it.f2informatica.core.gateway.mysql;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import it.f2informatica.core.gateway.ConsultantRepositoryGateway;
import it.f2informatica.core.gateway.EntityToModelConverter;
import it.f2informatica.core.model.*;
import it.f2informatica.mysql.MySQL;
import it.f2informatica.mysql.Persistence;
import it.f2informatica.mysql.domain.*;
import it.f2informatica.mysql.domain.pk.LanguagePK;
import it.f2informatica.mysql.domain.pk.SkillPK;
import it.f2informatica.mysql.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Set;

@MySQL
@Service
public class ConsultantRepositoryGatewayMySQL implements ConsultantRepositoryGateway {

  @PersistenceContext(unitName = Persistence.PERSISTENCE_UNIT_NAME)
  private EntityManager entityManager;

  @Autowired
  private SkillRepository skillRepository;

  @Autowired
  private LanguageRepository languageRepository;

  @Autowired
  private EducationRepository educationRepository;

  @Autowired
  private ConsultantRepository consultantRepository;

  @Autowired
  private ExperienceRepository experienceRepository;

  @Autowired
  @Qualifier("mysqlConsultantToModelConverter")
  private EntityToModelConverter<Consultant, ConsultantModel> mysqlConsultantToModelConverter;

  @Autowired
  @Qualifier("mysqlExperienceToModelConverter")
  private EntityToModelConverter<Experience, ExperienceModel> mysqlExperienceToModelConverter;

  @Autowired
  @Qualifier("mysqlEducationToModelConverter")
  private EntityToModelConverter<Education, EducationModel> mysqlEducationToModelConverter;

  @Autowired
  @Qualifier("mysqlLanguageToModelConverter")
  private EntityToModelConverter<Language, LanguageModel> mysqlLanguageToModelConverter;

  @Override
  @Transactional(readOnly = true)
  public ConsultantModel findOneConsultant(String consultantId) {
    return mysqlConsultantToModelConverter.convert(consultantRepository.findOne(Long.parseLong(consultantId)));
  }

  @Override
  @Transactional(readOnly = true)
  public Page<ConsultantModel> findAllConsultants(Pageable pageable) {
    Page<Consultant> consultantPage = consultantRepository.findAll(pageable);
    return new PageImpl<>(mysqlConsultantToModelConverter.convertList(consultantPage.getContent()), pageable, consultantPage.getTotalElements());
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
  public ConsultantModel savePersonalDetails(ConsultantModel consultantModel) {
    Consultant consultant = new Consultant();
    consultant.setConsultantNo(consultantModel.getConsultantNo());
    consultant.setRegistrationDate(consultantModel.getRegistrationDate());
    consultant.setFiscalCode(consultantModel.getFiscalCode());
    consultant.setEmail(consultantModel.getEmail());
    consultant.setFirstName(consultantModel.getFirstName());
    consultant.setLastName(consultantModel.getLastName());
    consultant.setGender(consultantModel.getGender());
    consultant.setPhoneNumber(consultantModel.getPhoneNumber());
    consultant.setMobileNumber(consultantModel.getMobileNumber());
    consultant.setBirthDate(consultantModel.getBirthDate());
    consultant.setBirthCity(consultantModel.getBirthCity());
    consultant.setBirthCountry(consultantModel.getBirthCountry());
    consultant.setIdentityCard(consultantModel.getIdentityCardNo());
    consultant.setInterests(consultantModel.getInterests());
    mapResidenceData(consultantModel, consultant);
    mapDomicileData(consultantModel, consultant);
    consultantRepository.save(consultant);
    entityManager.refresh(consultant);
    return mysqlConsultantToModelConverter.convert(consultant);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
  public boolean updatePersonalDetails(ConsultantModel consultantModel, String consultantId) {
    Consultant consultant = consultantRepository.findOne(Long.parseLong(consultantId));
    consultant.setFiscalCode(consultantModel.getFiscalCode());
    consultant.setEmail(consultantModel.getEmail());
    consultant.setFirstName(consultantModel.getFirstName());
    consultant.setLastName(consultantModel.getLastName());
    consultant.setGender(consultantModel.getGender());
    consultant.setPhoneNumber(consultantModel.getPhoneNumber());
    consultant.setMobileNumber(consultantModel.getMobileNumber());
    consultant.setBirthDate(consultantModel.getBirthDate());
    consultant.setBirthCity(consultantModel.getBirthCity());
    consultant.setBirthCountry(consultantModel.getBirthCountry());
    consultant.setIdentityCard(consultantModel.getIdentityCardNo());
    consultant.setInterests(consultantModel.getInterests());
    mapResidenceData(consultantModel, consultant);
    mapDomicileData(consultantModel, consultant);
    return true;
  }

  private void mapResidenceData(ConsultantModel consultantModel, Consultant consultant) {
    if (consultant.getResidence() == null) {
      Address address = new Address();
      address.setConsultantResidence(consultant);
      consultant.setResidence(address);
    }
    mapAddressData(consultantModel.getResidence(), consultant.getResidence());
  }

  private void mapDomicileData(ConsultantModel consultantModel, Consultant consultant) {
    if (consultant.getDomicile() == null) {
      Address address = new Address();
      address.setConsultantDomicile(consultant);
      consultant.setDomicile(address);
    }
    mapAddressData(consultantModel.getDomicile(), consultant.getDomicile());
  }

  private void mapAddressData(AddressModel addressModel, Address address) {
    address.setStreet(addressModel.getStreet());
    address.setHouseNo(addressModel.getHouseNo());
    address.setZipCode(addressModel.getZipCode());
    address.setCity(addressModel.getCity());
    address.setProvince(addressModel.getProvince());
    address.setRegion(addressModel.getRegion());
    address.setCountry(addressModel.getCountry());
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
  public boolean addExperience(ExperienceModel experienceModel, String consultantId) {
    Experience experience = new Experience();
    experience.setCompanyName(experienceModel.getCompanyName());
    experience.setJobPosition(experienceModel.getPosition());
    experience.setLocation(experienceModel.getLocality());
    experience.setPeriodFrom(experienceModel.getPeriodFrom());
    experience.setPeriodTo(experienceModel.getPeriodTo());
    experience.setCurrent(experienceModel.isCurrent());
    experience.setDescription(experienceModel.getDescription());
    Consultant consultant = consultantRepository.findOne(Long.parseLong(consultantId));
    experience.setConsultant(consultant);
    return consultant.getExperiences().add(experience);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
  public boolean updateExperience(ExperienceModel experienceModel, String consultantId) {
    return experienceRepository.updateExperience(
      Long.parseLong(experienceModel.getId()),
      experienceModel.getCompanyName(),
      experienceModel.getPosition(),
      experienceModel.getLocality(),
      experienceModel.getPeriodFrom(),
      experienceModel.getPeriodTo(),
      experienceModel.isCurrent(),
      experienceModel.getDescription()) != 0;
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
  public void removeExperience(String consultantId, String experienceId) {
    experienceRepository.delete(Long.parseLong(experienceId));
  }

  @Override
  @Transactional(readOnly = true)
  public ExperienceModel findOneExperience(String consultantId, String experienceId) {
    return mysqlExperienceToModelConverter.convert(experienceRepository.findOne(Long.parseLong(experienceId)));
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
  public boolean addLanguages(LanguageModel[] languageModelArray, final String consultantId) {
    final Consultant consultant = consultantRepository.findOne(Long.parseLong(consultantId));
    final List<LanguageModel> languageModels = Lists.newArrayList(languageModelArray);
    removeLanguagesThatMustNotBeInDBAnymore(consultant, languageModels);
    removeLanguagesThatAlreadyExistInDB(consultantId, languageModels);
    Set<Language> languages = Sets.newHashSet(Iterables.transform(languageModels,
      new Function<LanguageModel, Language>() {
        @Override
        public Language apply(LanguageModel input) {
          LanguagePK pk = new LanguagePK(input.getLanguage(), consultant);
          Language language = new Language();
          language.setId(pk);
          language.setProficiency(input.getProficiency());
          return language;
        }
      }
    ));
    consultant.getLanguages().addAll(languages);
    return true;
  }

  private void removeLanguagesThatMustNotBeInDBAnymore(Consultant consultant, final List<LanguageModel> languageModels) {
    Iterables.removeIf(consultant.getLanguages(), new Predicate<Language>() {
      @Override
      public boolean apply(Language language) {
        LanguageModel languageModel = mysqlLanguageToModelConverter.convert(language);
        return !languageModels.contains(languageModel);
      }
    });
  }

  private void removeLanguagesThatAlreadyExistInDB(final String consultantId, List<LanguageModel> languageModels) {
    Iterables.removeIf(languageModels, new Predicate<LanguageModel>() {
      @Override
      public boolean apply(LanguageModel languageModel) {
        List<LanguageModel> languages = mysqlLanguageToModelConverter.convertList(languageRepository.findByConsultantId(Long.parseLong(consultantId)));
        return languages.contains(languageModel);
      }
    });
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
  public boolean addSkills(String[] skillArray, String consultantId) {
    final Consultant consultant = consultantRepository.findOne(Long.parseLong(consultantId));
    Set<String> skillSet = Sets.newHashSet(skillArray);
    removeSkillsThatMustNotBeInDBAnymore(consultant, skillSet);
    removeSkillsThatAlreadyExistInDB(skillSet);
    Set<Skill> skills = Sets.newHashSet(Iterables.transform(skillSet, new Function<String, Skill>() {
      @Override
      public Skill apply(String input) {
        return new Skill(new SkillPK(input, consultant));
      }
    }));
    return consultant.getSkills().addAll(skills);
  }

  private void removeSkillsThatMustNotBeInDBAnymore(Consultant consultant, final Set<String> skills) {
    Iterables.removeIf(consultant.getSkills(), new Predicate<Skill>() {
      @Override
      public boolean apply(Skill skill) {
        return !skills.contains(skill.getId().getSkill());
      }
    });
  }

  private void removeSkillsThatAlreadyExistInDB(final Set<String> skills) {
    Iterables.removeIf(skills, new Predicate<String>() {
      @Override
      public boolean apply(String skill) {
        return skillRepository.findByIdSkill(skill) != null;
      }
    });
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
  public void removeEducation(String consultantId, String educationId) {
    educationRepository.delete(Long.parseLong(educationId));
  }

  @Override
  @Transactional(readOnly = true)
  public EducationModel findOneEducation(String consultantId, String educationId) {
    return mysqlEducationToModelConverter.convert(educationRepository.findOne(Long.parseLong(educationId)));
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
  public boolean addEducation(EducationModel educationModel, String consultantId) {
    Consultant consultant = consultantRepository.findOne(Long.parseLong(consultantId));
    Education education = new Education();
    education.setSchoolName(educationModel.getSchool());
    education.setStartYear(educationModel.getStartYear());
    education.setEndYear(educationModel.getEndYear());
    education.setCurrent(educationModel.isCurrent());
    education.setSchoolDegree(educationModel.getSchoolDegree());
    education.setFieldsOfStudy(educationModel.getSchoolFieldOfStudy());
    education.setGrade(educationModel.getSchoolGrade());
    education.setActivities(educationModel.getSchoolActivities());
    education.setDescription(educationModel.getDescription());
    education.setConsultant(consultant);
    return consultant.getEducations().add(education);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
  public boolean updateEducation(EducationModel educationModel, String consultantId) {
    Education education = educationRepository.findOne(Long.parseLong(educationModel.getId()));
    education.setSchoolName(educationModel.getSchool());
    education.setStartYear(educationModel.getStartYear());
    education.setEndYear(educationModel.getEndYear());
    education.setCurrent(educationModel.isCurrent());
    education.setSchoolDegree(educationModel.getSchoolDegree());
    education.setFieldsOfStudy(educationModel.getSchoolFieldOfStudy());
    education.setGrade(educationModel.getSchoolGrade());
    education.setActivities(educationModel.getSchoolActivities());
    education.setDescription(educationModel.getDescription());
    return true;
  }

}
