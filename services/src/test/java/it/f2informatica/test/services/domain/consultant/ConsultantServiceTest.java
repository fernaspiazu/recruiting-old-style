package it.f2informatica.test.services.domain.consultant;

import it.f2informatica.services.domain.consultant.ConsultantService;
import it.f2informatica.services.domain.consultant.ConsultantServiceImpl;
import it.f2informatica.services.gateway.ConsultantRepositoryGateway;
import it.f2informatica.services.model.ConsultantModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;

import static it.f2informatica.services.model.builder.ConsultantModelBuilder.consultantModel;
import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ConsultantServiceTest {

	@Mock
	private ConsultantRepositoryGateway consultantRepositoryGateway;

	@InjectMocks
	private ConsultantService consultantService = new ConsultantServiceImpl();

	@Test
	public void assertThatShowAllConsultantsMethodWorks() {
		when(consultantRepositoryGateway.findAllConsultants(any(Pageable.class))).thenReturn(consultants());
		Page<ConsultantModel> paginatedResult = consultantService.showAllConsultants(new PageRequest(1, 10));
		assertThat(paginatedResult).isNotEmpty().hasSize(2);
	}

	private Page<ConsultantModel> consultants() {
		return new PageImpl<>(Arrays.asList(
				consultantModel().withFirstName("consultant_1").withLastName("consultant_1").build(),
				consultantModel().withFirstName("consultant_2").withLastName("consultant_2").build())
		);
	}

	@Test
	public void assertFirstStepOnSavingConsultant() {
		when(consultantRepositoryGateway.saveMasterData(any(ConsultantModel.class)))
				.thenReturn(registeredConsultant());
		ConsultantModel registeredConsultant = consultantService.registerConsultantMasterData(
				consultantModel()
						.withFirstName("Mario")
						.withLastName("Rossi").build());
		assertThat(registeredConsultant.getConsultantNo()).isEqualTo("20131152820f6f34bdf55624303fc4");
	}

	private ConsultantModel registeredConsultant() {
		return consultantModel()
				.withId("52820f6f34bdf55624303fc2")
				.withConsultantNo("20131152820f6f34bdf55624303fc4")
				.withFirstName("Mario")
				.withLastName("Rossi")
				.build();
	}

	@Test
	public void findConsultantByIdTest() {
		ConsultantModel consMock = consultantModel()
				.withId("5298766a39ef39c7c280b7e5")
				.withFirstName("Mario")
				.withLastName("Rossi")
				.build();
		when(consultantRepositoryGateway.findConsultantById(consMock.getId())).thenReturn(consMock);
		ConsultantModel result = consultantService.findConsultantById(consMock.getId());
		assertThat(result.getFirstName()).isEqualTo(consMock.getFirstName());
	}

	public void verifyThatTwoNumbersAreNotEqualEachOtherAfterTenRounds() {
		for (int i=0; i<10; i++) {
			String firstCodeGenerated = consultantService.generateConsultantNumber();
			String secondCodeGenerated = consultantService.generateConsultantNumber();
			assertThat(firstCodeGenerated).isNotEqualTo(secondCodeGenerated);
		}
	}

	@Test
	public void testConsultantNumberFormat() {
		String consultantNumber = consultantService.generateConsultantNumber();
		assertThat(consultantNumber).hasSize(22);
		assertThat(consultantNumber.split("-")).hasSize(2);
	}

}
