package it.f2informatica.webapp.test.validator;

import it.f2informatica.services.model.UserModel;
import it.f2informatica.webapp.controller.helper.CurrentHttpServletRequest;
import it.f2informatica.webapp.validator.RegistrationUserValidator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;

import static it.f2informatica.services.model.builder.RoleModelBuilder.roleModel;
import static it.f2informatica.services.model.builder.UserModelBuilder.userModel;
import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RegistrationUserValidatorTest {

	@Mock
	private CurrentHttpServletRequest currentHttpServletRequest;

	@InjectMocks
	private RegistrationUserValidator registrationUserValidator = new RegistrationUserValidator();

	@Before
	public void initialize() {
		registrationUserValidator.supports(UserModel.class);
	}

	@Test
	public void validationTest() {
		HttpServletRequest request = mock(HttpServletRequest.class);
		when(currentHttpServletRequest.currentHttpServletRequest()).thenReturn(request);
		when(request.getRequestURI()).thenReturn("/user/save");

		UserModel userModel = userModel()
			.withUsername(null).withPassword("pass")
			.withRole(roleModel().withId("12345").withAuthorization("Administrator"))
			.build();
		BindingResult bindingResult = new BeanPropertyBindingResult(userModel, "userModel");
		registrationUserValidator.validate(userModel, bindingResult);
		assertThat(bindingResult.hasErrors()).isTrue();
	}

}
