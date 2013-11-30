package it.f2informatica.webapp.test.gateway;

import com.google.common.collect.Lists;
import it.f2informatica.services.domain.consultant.ConsultantService;
import it.f2informatica.services.model.ConsultantModel;
import it.f2informatica.webapp.gateway.ConsultantServiceGateway;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static it.f2informatica.services.model.builder.ConsultantModelBuilder.consultantModel;
import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ConsultantServiceGatewayTest {

	@Mock
	private ConsultantService consultantService;

	@InjectMocks
	private ConsultantServiceGateway consultantServiceGateway = new  ConsultantServiceGateway();

	@Test
	public void showAllConsultant() {
		Page<ConsultantModel> consultantPage = new PageImpl<>(Lists.newArrayList(new ConsultantModel(), new ConsultantModel()));
		when(consultantService.showAllConsultants(any(Pageable.class))).thenReturn(consultantPage);
		Page<ConsultantModel> consultantPageResult = consultantServiceGateway.showAllConsultants(new PageRequest(1, 10));
		assertThat(consultantPageResult).hasSize(2);
	}

	@Test
	public void findConsultantByIdTest() {
		ConsultantModel consMock = consultantModel()
				.withId("5298766a39ef39c7c280b7e5")
				.withFirstName("Mario")
				.build();
		when(consultantService.findConsultantById(consMock.getId())).thenReturn(consMock);
		ConsultantModel consultant = consultantServiceGateway.findConsultantById(consMock.getId());
		assertThat(consultant.getFirstName()).isEqualTo(consMock.getFirstName());
	}

}
