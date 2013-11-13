package it.f2informatica.test.mongodb.domain;

import it.f2informatica.mongodb.domain.Consultant;
import org.junit.Test;

import static it.f2informatica.test.mongodb.builders.ConsultantDataBuilder.consultant;
import static it.f2informatica.test.mongodb.builders.LanguageDataBuilder.english;
import static it.f2informatica.test.mongodb.builders.ProfileDataBuilder.profile;
import static it.f2informatica.test.mongodb.builders.TrainingDataBuilder.training;
import static org.fest.assertions.Assertions.assertThat;

public class ConsultantTest {

	private Consultant consultant = consultant().build();;

	@Test
	public void assertConsultantBuilding() {
		assertThat(consultant).isNotNull();
	}

	@Test
	public void assertThatConsultantSpeaksTwoLanguages() {
		assertThat(consultant.getProfile().getLanguages())
				.hasSize(2)
				.contains(english().build());
	}

	@Test
	public void assertInequality() {
		Consultant differentConsultant = consultant()
				.withConsultantNo("1234567890")
				.withProfile(profile()
						.withTrainingIn(training()
								.startedInYear(1997)
								.finishedInYear(1998)))
				.build();
		assertThat(consultant).isNotEqualTo(differentConsultant);
	}

}