package it.f2informatica.test.mongodb.repositories;

import it.f2informatica.datastore.constant.LanguageProficiency;
import it.f2informatica.mongodb.domain.Consultant;
import it.f2informatica.mongodb.domain.Experience;
import it.f2informatica.mongodb.repositories.ConsultantRepository;
import it.f2informatica.test.mongodb.DatastoreUtils;
import org.fest.assertions.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.GregorianCalendar;

import static it.f2informatica.test.mongodb.builders.ConsultantDataBuilder.consultant;
import static it.f2informatica.test.mongodb.builders.ExperienceDataBuilder.experience;
import static it.f2informatica.test.mongodb.builders.LanguageDataBuilder.italian;
import static it.f2informatica.test.mongodb.builders.LanguageDataBuilder.spanish;
import static it.f2informatica.test.mongodb.builders.ProfileDataBuilder.profile;
import static org.fest.assertions.Assertions.*;

public class ConsultantRepositoryTest extends DatastoreUtils {

	@Autowired
	private ConsultantRepository consultantRepository;

	@Before
	public void setup() {
		Consultant consultant = consultant()
				.withId("12345678901234567890")
				.withConsultantNo("12345678901234567890")
				.withLastName("Aspiazu")
				.withFirstName("Fernando")
				.withFiscalCode("SPZFNN87D17Z605K")
				.withProfile(profile()
						.withExperienceIn(experience()
								.withId("11111")
								.fromPeriod(new GregorianCalendar(2011, 1, 1).getTime())
								.toPeriod(new GregorianCalendar(2011, 11, 1).getTime())
								.inCompany("F2 Informatica").inFunctionOf("Developer").locatedAt("Corsico"))
						.withExperienceIn(experience()
								.withId("22222")
								.fromPeriod(new GregorianCalendar(2012, 1, 1).getTime())
								.toPeriod(new GregorianCalendar(2012, 11, 1).getTime())
								.inCompany("Athirat").inFunctionOf("Developer").locatedAt("Milano"))
						.withExperienceIn(experience()
								.withId("33333")
								.fromPeriod(new GregorianCalendar(2013, 1, 1).getTime())
								.toPeriod(new GregorianCalendar(2013, 11, 1).getTime())
								.inCompany("Sopra Group").inFunctionOf("Developer").locatedAt("Assago"))
						.withExperienceIn(experience()
								.withId("44444")
								.fromPeriod(new GregorianCalendar(2014, 1, 1).getTime())
								.thisIsTheCurrentJob()
								.inCompany("RedHat").inFunctionOf("Developer").locatedAt("Milano")
								.withDescription("Web/Enterprise Application development as Sales Solution " +
										"and Order management. I had to develop particularly back-end components " +
										"and domain model using Hibernate, JPA and some Spring modules as support. " +
										"I've also participated in the Business layer development and Front-end " +
										"layer building. Besides, has been adopted SCRUM approach as Agile methodology " +
										"and Continuous Integration in order to release a stable version per sprint."))
						.speaking(spanish().withProficiency(LanguageProficiency.NATIVE_OR_BILINGUAL))
						.speaking(italian().withProficiency(LanguageProficiency.FULL_PROFESSIONAL))
				).build();
		consultantRepository.save(consultant);
	}

	@Test
	public void findExperienceTest() {
		Experience experience = consultantRepository.findExperience("12345678901234567890", "44444");
		assertThat(experience.getCompanyName()).isEqualTo("RedHat");
	}

}
