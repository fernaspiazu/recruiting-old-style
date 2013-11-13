package it.f2informatica.test.services.domain.consultant;

import com.google.common.collect.Lists;
import it.f2informatica.mongodb.domain.Consultant;
import it.f2informatica.mongodb.repositories.ConsultantRepository;
import it.f2informatica.services.domain.consultant.ConsultantService;
import it.f2informatica.services.domain.consultant.impl.ConsultantServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static it.f2informatica.mongodb.domain.builders.ConsultantBuilder.consultant;
import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ConsultantServiceTest {

	@Mock
	private ConsultantRepository consultantRepository;

	@InjectMocks
	private ConsultantService consultantService = new ConsultantServiceImpl();

	@Test
	public void findAllConsultants() {
		when(consultantRepository.findAll(any(Pageable.class)))
				.thenReturn(new PageImpl<>(getConsultantList(5)));
		assertThat(consultantService.findAll(new PageRequest(0, 10))).hasSize(5);
	}

	private List<Consultant> getConsultantList(int size) {
		List<Consultant> consultants = Lists.newArrayList();
		for (int i = 0; i < size; i++) {
			consultants.add(consultant().withFirstName("consultant_" + (i+1)).build());
		}
		return consultants;
	}

}
