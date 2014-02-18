package it.f2informatica.webapp.test.controllers;

import it.f2informatica.webapp.controller.I18NJavascriptResolverController;
import it.f2informatica.webapp.utils.CurrentHttpServletRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Locale;

import static junit.framework.Assert.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@RunWith(MockitoJUnitRunner.class)
public class I18NJavascriptResolverTest {

	private MessageSource messageSource = messageSource();

	@Mock
	private CurrentHttpServletRequest currentHttpRequest;

	@InjectMocks
	private I18NJavascriptResolverController i18NJavascriptResolverController;

	private MockMvc mockMvc;

	@Before
	public void setUp() {
		ReflectionTestUtils.setField(i18NJavascriptResolverController, "messageSource", messageSource);
		mockMvc = MockMvcBuilders
				.standaloneSetup(i18NJavascriptResolverController)
				.build();
	}

	@Test
	public void resolveMessageRequestTest() throws Exception {
		if (isLocaleEnglish()) {
			assertResolveMessageRequest("Message in English");
		} else if (isLocaleItalian()) {
			assertResolveMessageRequest("Messaggio in Italiano");
		} else {
			fail("No language available");
		}
	}

	private void assertResolveMessageRequest(String message) throws Exception {
		mockMvc.perform(post("/js/resolvei18nCode").param("code", "message.test"))
				.andExpect(content().string(message));
	}

	private boolean isLocaleEnglish() {
		System.out.println(Locale.getDefault());
		return Locale.getDefault().equals(Locale.US) || Locale.getDefault().equals(Locale.UK);
	}

	private boolean isLocaleItalian() {
		return Locale.getDefault().equals(Locale.ITALY);
	}

	public ReloadableResourceBundleMessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasenames("/WEB-INF/i18n/global");
		messageSource.setDefaultEncoding("UTF-8");
		messageSource.setCacheSeconds(1);
		messageSource.setFallbackToSystemLocale(false);
		return messageSource;
	}

}
