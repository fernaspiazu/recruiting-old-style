package it.f2informatica.services.gateway.mysql;

import com.google.common.base.Function;
import it.f2informatica.mysql.domain.*;
import it.f2informatica.mysql.domain.pk.LanguagePK;
import it.f2informatica.mysql.repositories.ConsultantRepository;
import it.f2informatica.mysql.repositories.ExperienceRepository;
import it.f2informatica.services.gateway.ConsultantRepositoryGateway;
import it.f2informatica.services.gateway.EntityToModelConverter;
import it.f2informatica.services.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static com.google.common.collect.Lists.transform;
import static com.google.common.collect.Sets.newHashSet;

@Service
public class ConsultantRepositoryGatewayMySQL implements ConsultantRepositoryGateway {

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

  @Override
  @Transactional(readOnly = true)
  public ConsultantModel findOneConsultant(String consultantId) {
    return mysqlConsultantToModelConverter.convert(consultantRepository.findOne(Long.parseLong(consultantId)));
  }

  @Override
  @Transactional(readOnly = true)
  public Page<ConsultantModel> findAllConsultants(Pageable pageable) {
    Page<Consultant> consultantPage = consultantRepository.findAll(pageable);
    return new PageImpl<>(mysqlConsultantToModelConverter.convertList(consultantPage.getContent()));
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
    mapAddressData(consultantModel.getResidence(), new Address());
    mapAddressData(consultantModel.getDomicile(), new Address());
    final Consultant savedEntity = consultantRepository.save(consultant);
    return mysqlConsultantToModelConverter.convert(savedEntity);
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
    mapAddressData(consultantModel.getResidence(), consultant.getResidence());
    mapAddressData(consultantModel.getDomicile(), consultant.getDomicile());
    return consultantRepository.saveAndFlush(consultant) != null;
  }

  private void mapAddressData(AddressModel addressModel, Address address) {
    if (addressModel != null) {
      if  (address == null) {
        address = new Address();
      }
      address.setStreet(addressModel.getStreet());
      address.setHouseNo(addressModel.getHouseNo());
      address.setZipCode(addressModel.getZipCode());
      address.setCity(addressModel.getCity());
      address.setProvince(addressModel.getProvince());
      address.setRegion(addressModel.getRegion());
      address.setCountry(addressModel.getCountry());
    }
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
  public boolean addLanguages(LanguageModel[] languageModelArray, final String consultantId) {
    final Consultant consultant = consultantRepository.findOne(Long.parseLong(consultantId));
    List<LanguageModel> languageModels = Arrays.asList(languageModelArray);
    Set<Language> languages = newHashSet(transform(languageModels, new Function<LanguageModel, Language>() {
      @Override
      public Language apply(LanguageModel input) {
        LanguagePK pk = new LanguagePK(input.getLanguage(), consultant);
        Language language = new Language();
        language.setId(pk);
        language.setProficiency(input.getProficiency());
        return language;
      }
    }));
    return consultant.getLanguages().addAll(languages);
  }

  @Override
  public boolean addSkills(String[] skills, String consultantId) {
    return false;
  }

  @Override
  public boolean updateEducation(EducationModel educationModel, String consultantId) {
    return false;
  }

  @Override
  public void removeEducation(String consultantId, String educationId) {

  }

  @Override
  public boolean addEducation(EducationModel educationModel, String consultantId) {
    return false;
  }

  @Override
  public EducationModel findOneEducation(String consultantId, String educationId) {
    return null;
  }

}
