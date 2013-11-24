package it.f2informatica.webapp.test.validator;

import it.f2informatica.services.model.UserModel;
import it.f2informatica.webapp.validator.RegistrationUserValidator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import static org.fest.assertions.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class RegistrationUserValidatorTest {

	private RegistrationUserValidator registrationUserValidator;

	@Before
	public void initialize() {
		registrationUserValidator = new RegistrationUserValidator();
		registrationUserValidator.supports(UserModel.class);
	}

	@Test
	public void validationTest() {
		BindingResult bindingResult = new BeanPropertyBindingResult(new UserModel(), "userModel");
		registrationUserValidator.validate(new UserModel(), bindingResult);
		assertThat(bindingResult.hasErrors()).isTrue();
	}

}
