package it.f2informatica.test.services.domain.consultant;

import com.google.common.base.Optional;
import it.f2informatica.core.gateway.ConsultantRepositoryGateway;
import it.f2informatica.core.model.ConsultantModel;
import it.f2informatica.core.services.ConsultantService;
import it.f2informatica.core.services.ConsultantServiceImpl;
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

import static it.f2informatica.core.model.builder.ConsultantModelBuilder.consultantModel;
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
    Page<ConsultantModel> paginatedResult = consultantService.paginateConsultants(new PageRequest(1, 10));
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
    when(consultantRepositoryGateway.savePersonalDetails(any(ConsultantModel.class)))
      .thenReturn(registeredConsultant());
    ConsultantModel registeredConsultant = consultantService.savePersonalDetails(
      consultantModel()
        .withFirstName("Mario")
        .withLastName("Rossi").build()
    );
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
    when(consultantRepositoryGateway.findOneConsultant(consMock.getId())).thenReturn(consMock);
    Optional<ConsultantModel> consultant = consultantService.findConsultantById(consMock.getId());
    assertThat(consultant.isPresent()).isTrue();
    assertThat(consultant.get().getFirstName()).isEqualTo(consMock.getFirstName());
  }

  @Test
  public void verifyThatTwoNumbersAreNotEqualEachOtherAfterTenRounds() {
    for (int i = 0; i < 10; i++) {
      String firstCodeGenerated = consultantService.generateConsultantNumber();
      String secondCodeGenerated = consultantService.generateConsultantNumber();
      assertThat(firstCodeGenerated).isNotEqualTo(secondCodeGenerated);
    }
  }

  @Test
  public void testConsultantNumberFormat() {
    String consultantNumber = consultantService.generateConsultantNumber();
    assertThat(consultantNumber).hasSize(21);
  }

}
