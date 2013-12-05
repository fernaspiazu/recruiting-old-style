package it.f2informatica.acceptance.usecase;

import it.f2informatica.acceptance.page.consultant.ConsultantManagementPage;
import it.f2informatica.acceptance.page.consultant.ConsultantRegistrationPage;
import it.f2informatica.acceptance.page.consultant.ProfileRegistrationPage;
import it.f2informatica.mongodb.domain.Consultant;
import org.junit.After;
import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static org.fest.assertions.Assertions.assertThat;

public class UC_03_ConsultantRegistration extends UseCaseTest {

	@After
	public void logout() {
		navigator.logOut();
	}

	private void login() {
		navigator.goToLoginPage().login("admin", "admin");
	}

	@Test
	public void registerNewConsultant() {
		login();
		ConsultantManagementPage consultantManagementPage = navigator.goToConsultantManagementPage();
		ConsultantRegistrationPage registrationFormPage = consultantManagementPage.consultantRegistrationForm();
		ProfileRegistrationPage profilePage = consultantMasterDataRegistration(registrationFormPage);
		consultantProfileDataRegistration(profilePage);
	}

	private void consultantProfileDataRegistration(ProfileRegistrationPage profilePage) {
		assertThat(profilePage.isExperienceSectionPresent()).isTrue();
		profilePage.addNewExperience();
		assertThat(profilePage.isNewExperienceFormPresent()).isTrue();
		assertThat(profilePage.isAddNewExperienceButtonDisabled()).isTrue();
		profilePage.typeCompanyName("IBM");
		profilePage.typeFunction("Business Analyst");
		profilePage.typeLocation("London");
		profilePage.selectPeriodFromApril();
		profilePage.typePeriofFromYear("2010");
		profilePage.selectPeriodToDecember();
		profilePage.typePeriodToYear("2012");
		profilePage.typeDescription("This is a fake description");
		profilePage.saveExperience();
		assertThat(profilePage.experiencesNumber()).isEqualTo(1);
	}

	private ProfileRegistrationPage consultantMasterDataRegistration(ConsultantRegistrationPage registrationFormPage) {
		assertThatRegistrationDateIsToday(registrationFormPage.registrationDate());
		assertThatConsultantNumberIsCorrect(registrationFormPage.consultantNumber());
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
		Consultant consultantRegistered = consultantRepository.findByFiscalCode("RSSMRA78H05A089N");
		ProfileRegistrationPage profilePage = loadProfileRegistrationPage(consultantRegistered);
		assertThat("Rossi Mario").isEqualTo(profilePage.consultantWichWillBeAddedProfile());
		return profilePage;
	}

	private void assertThatRegistrationDateIsToday(String registrationDate) {
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		String today = dateFormat.format(Calendar.getInstance().getTime());
		assertThat(registrationDate).isEqualTo(today);
	}

	private void assertThatConsultantNumberIsCorrect(String consultantNo) {
		assertThat(consultantNo).hasSize(22);
		assertThat(consultantNo.split("-")).hasSize(2);
	}

	private ProfileRegistrationPage loadProfileRegistrationPage(Consultant consultantRegistered) {
		return new ProfileRegistrationPage(driver, navigator.getBaseUrl(), consultantRegistered.getId());
	}

}
