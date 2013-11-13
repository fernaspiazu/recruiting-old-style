package it.f2informatica.webapp.test.gateway;

import com.google.common.collect.Lists;
import it.f2informatica.services.domain.consultant.ConsultantService;
import it.f2informatica.services.responses.ConsultantPaginatedResponse;
import it.f2informatica.webapp.gateway.ConsultantServiceGateway;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static it.f2informatica.services.responses.builders.ConsultantPaginatedResponseBuilder.consultantPaginatedResponse;
import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ConsultantServiceGatewayTest {

	@Mock
	private ConsultantService consultantService;

	@InjectMocks
	private ConsultantServiceGateway consultantServiceGateway = new ConsultantServiceGateway();

	@Test
	public void findAllConsultantsTest() {
		when(consultantService.findAll(any(Pageable.class))).thenReturn(getConsultantResponseList(5));
		assertThat(consultantServiceGateway.findAllConsultants(new PageRequest(0, 10))).hasSize(5);
	}

	private PageImpl<ConsultantPaginatedResponse> getConsultantResponseList(int size) {
		List<ConsultantPaginatedResponse> responses = Lists.newArrayList();
		for (int i = 0; i < size; i++) {
			responses.add(consultantPaginatedResponse()
					.withFirstName("consultant_" + (i + 1))
					.build());
		}
		return new PageImpl<>(responses);
	}

}
