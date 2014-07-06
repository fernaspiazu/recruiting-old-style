/*
 * =============================================================================
 *
 *   Copyright (c) 2014, Fernando Aspiazu
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 * =============================================================================
 */
package it.f2informatica.test.services.gateway.mongodb;

import com.google.common.collect.Lists;
import it.f2informatica.core.gateway.ConsultantRepositoryGateway;
import it.f2informatica.core.gateway.EntityToModelConverter;
import it.f2informatica.core.gateway.mongodb.ConsultantRepositoryGatewayMongoDB;
import it.f2informatica.core.model.ConsultantModel;
import it.f2informatica.mongodb.domain.Consultant;
import it.f2informatica.mongodb.repositories.ConsultantRepository;
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
import java.util.List;

import static it.f2informatica.core.model.builder.ConsultantModelBuilder.consultantModel;
import static it.f2informatica.mongodb.domain.builder.ConsultantBuilder.consultant;
import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ConsultantRepositoryGatewayMongoDBTest {

	@Mock
	private ConsultantRepository consultantRepository;

	@Mock
	private EntityToModelConverter<Consultant, ConsultantModel> consultantToModelConverter;

	@InjectMocks
	private ConsultantRepositoryGateway consultantRepositoryGateway = new ConsultantRepositoryGatewayMongoDB();

	@Test
	public void findAllConsultantsTest() {
		when(consultantRepository.findAll(any(Pageable.class))).thenReturn(consultants());
		when(consultantToModelConverter.convertList(anyListOf(Consultant.class))).thenReturn(consultantModels());
		Page<ConsultantModel> paginated = consultantRepositoryGateway.findAllConsultants(new PageRequest(1, 10));
		assertThat(paginated).isNotEmpty().hasSize(2);
	}

	private Page<Consultant> consultants() {
		return new PageImpl<>(Arrays.asList(
			consultant().withFirstName("consultant_1").withLastName("consultant_1").build(),
			consultant().withFirstName("consultant_2").withLastName("consultant_2").build()
		));
	}

	private List<ConsultantModel> consultantModels() {
		return Lists.newArrayList(
			consultantModel().withFirstName("consultant_1").withLastName("consultant_1").build(),
			consultantModel().withFirstName("consultant_2").withLastName("consultant_2").build()
		);
	}

	@Test
	public void assertThatNoCrashWhenSavingNewConsultant() {
		ConsultantModel registeredConsultant = consultantModel()
			.withId("52820f6f34bdf55624303fc2").withConsultantNo("20131152820f6f34bdf55624303fc4")
			.build();
		when(consultantRepository.save(any(Consultant.class))).thenReturn(new Consultant());
		when(consultantToModelConverter.convert(any(Consultant.class))).thenReturn(registeredConsultant);
		ConsultantModel consultantModel = consultantRepositoryGateway.savePersonalDetails(new ConsultantModel());
		assertThat(consultantModel.getConsultantNo()).isEqualTo("20131152820f6f34bdf55624303fc4");
	}

	@Test
	public void findConsultantByIdTest() {
		Consultant consultant = consultant().withId("5298766a39ef39c7c280b7e5").withFirstName("Mario").build();
		ConsultantModel model = consultantModel().withId(consultant.getId()).withFirstName(consultant.getFirstName()).build();
		when(consultantRepository.findOne(consultant.getId())).thenReturn(consultant);
		when(consultantToModelConverter.convert(consultant)).thenReturn(model);
		ConsultantModel consultantModel = consultantRepositoryGateway.findOneConsultant(consultant.getId());
		assertThat(consultantModel.getFirstName()).isEqualTo(model.getFirstName());
	}

}
