package it.f2informatica.acceptance.usecase;

import it.f2informatica.acceptance.page.consultant.ConsultantManagementPage;
import it.f2informatica.acceptance.page.consultant.ConsultantRegistrationPage;
import it.f2informatica.acceptance.page.consultant.ProfileRegistrationPage;
import it.f2informatica.mongodb.domain.Consultant;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static org.fest.assertions.Assertions.assertThat;

public class UC_04_ConsultantRegistration extends UseCaseTest {

	@Before
	public void init() {
		login();
	}

	@After
	public void teardown() {
		logout();
	}

	@Test
	public void registerNewConsultant() {
		ConsultantManagementPage consultantManagementPage = navigator.goToConsultantManagementPage();
		ConsultantRegistrationPage registrationFormPage = consultantManagementPage.consultantRegistrationForm();
		ProfileRegistrationPage profilePage = consultantMasterDataRegistration(registrationFormPage);
	}

	private ProfileRegistrationPage consultantMasterDataRegistration(ConsultantRegistrationPage registrationFormPage) {
		final String consultantNo = registrationFormPage.consultantNumber();
		isConsultantNumberCorrect(consultantNo);
		isRegistrationDateToday(registrationFormPage.registrationDate());
		registrationFormPage.typeFirstName("Mario");
		registrationFormPage.typeLastName("Rossi");
		registrationFormPage.selectMaleGender();
		registrationFormPage.typeEmail("mario.rossi@tiscali.it");
		registrationFormPage.typeFiscalCode("RSSMRA78H05A089N");
		registrationFormPage.typeBirthDate("05-06-1978");
		registrationFormPage.typeBirthCity("Agrigento");
		registrationFormPage.typeBirthCountry("Italy");
		registrationFormPage.typePhoneNumber("0289223344");
		registrationFormPage.typeMobileNumber("3401246559");
		registrationFormPage.clickOnSaveAndContinueRegisteringProfile();
		ProfileRegistrationPage profilePage = loadProfileRegistrationPage(consultantNo);
		checkThatConsultantHasBeenRegistered(profilePage);
		return profilePage;
	}

	private void isRegistrationDateToday(String registrationDate) {
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		String today = dateFormat.format(Calendar.getInstance().getTime());
		assertThat(registrationDate).isEqualTo(today);
	}

	private void isConsultantNumberCorrect(String consultantNo) {
		assertThat(consultantNo).hasSize(22);
		assertThat(consultantNo.split("-")).hasSize(2);
	}

	private ProfileRegistrationPage loadProfileRegistrationPage(String consultantNo) {
		Consultant consultant = consultantRepository.findByConsultantNo(consultantNo);
		return new ProfileRegistrationPage(driver, navigator.getBaseUrl(), consultant.getId());
	}

	private void checkThatConsultantHasBeenRegistered(ProfileRegistrationPage profilePage) {
		assertThat(profilePage.consultantWichWillBeAddedProfile()).contains("Rossi Mario");
	}

}
