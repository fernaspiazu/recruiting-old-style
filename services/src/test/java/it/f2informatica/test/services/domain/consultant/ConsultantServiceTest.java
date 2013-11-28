package it.f2informatica.test.services.domain.consultant;

import it.f2informatica.services.domain.consultant.ConsultantService;
import it.f2informatica.services.domain.consultant.ConsultantServiceImpl;
import it.f2informatica.services.gateway.ConsultantRepositoryGateway;
import it.f2informatica.services.model.ConsultantModel;
import org.fest.assertions.Assertions;
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
		Assertions.assertThat(paginatedResult).isNotEmpty().hasSize(2);
	}

	private Page<ConsultantModel> consultants() {
		return new PageImpl<>(Arrays.asList(
				consultantModel().withFirstName("consultant_1").withLastName("consultant_1").build(),
				consultantModel().withFirstName("consultant_2").withLastName("consultant_2").build())
		);
	}

}
