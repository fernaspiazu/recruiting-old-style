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
package it.f2informatica.webapp.test.context;

import com.google.common.collect.Iterables;
import it.f2informatica.webapp.WebApplicationConfig;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.view.BeanNameViewResolver;
import org.thymeleaf.dialect.IDialect;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.dialect.SpringStandardDialect;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import java.util.List;
import java.util.Locale;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class WebApplicationContextTest {

	@Mock
	private InterceptorRegistry interceptorRegistry;

	@Mock
	private ResourceHandlerRegistry handlerRegistry;

	@Mock
	private List<HandlerMethodArgumentResolver> argumentResolvers;

	@Mock
	private ResourceHandlerRegistration resourceHandlerRegistration;

	@Mock
	private DefaultServletHandlerConfigurer servletHandlerConfigurer;

	private static WebApplicationConfig webApplicationConfig;

	@BeforeClass
	public static void beforeClass() {
		webApplicationConfig = new WebApplicationConfig();
	}

	@Test
	public void resourceHandlers() {
		when(handlerRegistry.addResourceHandler("/static/**")).thenReturn(resourceHandlerRegistration);
		when(resourceHandlerRegistration.addResourceLocations("/static/")).thenReturn(resourceHandlerRegistration);
		webApplicationConfig.addResourceHandlers(handlerRegistry);
		verify(handlerRegistry).addResourceHandler("/static/**");
		verify(resourceHandlerRegistration).addResourceLocations("/static/");
	}

	@Test
	public void argumentResolvers() {
		ArgumentCaptor<PageableHandlerMethodArgumentResolver> argument = pageableArgumentCaptor();
		webApplicationConfig.addArgumentResolvers(argumentResolvers);
		verify(argumentResolvers).add(argument.capture());
		assertThat(argument.getValue()).isInstanceOf(PageableHandlerMethodArgumentResolver.class);
	}

	private ArgumentCaptor<PageableHandlerMethodArgumentResolver> pageableArgumentCaptor() {
		return ArgumentCaptor.forClass(PageableHandlerMethodArgumentResolver.class);
	}

	@Test
	public void configureDefaultServletHandling() {
		doNothing().when(servletHandlerConfigurer).enable();
		webApplicationConfig.configureDefaultServletHandling(servletHandlerConfigurer);
		verify(servletHandlerConfigurer).enable();
	}

	@Test
	public void beanNameViewResolver() {
		BeanNameViewResolver beanNameViewResolver = webApplicationConfig.beanNameViewResolver();
		assertThat(beanNameViewResolver.getOrder()).isEqualTo(1);
	}

	@Test
	public void thymeleafViewResolver() {
		ThymeleafViewResolver thymeleafViewResolver = webApplicationConfig.thymeleafViewResolver();
		assertThat(thymeleafViewResolver.getOrder()).isEqualTo(2);
	}

	@Test
	public void thymeleafTemplateEngine() {
		SpringTemplateEngine thymeleafTemplateEngine = webApplicationConfig.thymeleafTemplateEngine();
		IDialect springDialiect = Iterables.getFirst(thymeleafTemplateEngine.getDialects(), null);
		assertThat(springDialiect).isInstanceOf(SpringStandardDialect.class);
	}

	@Test
	public void thymeleafTemplateResolver() {
		ServletContextTemplateResolver templateResolver = webApplicationConfig.thymeleafTemplateResolver();
		templateResolver.initialize();
		assertThat(templateResolver.getTemplateMode()).isEqualTo("HTML5");
	}

	@Test
	public void messageSource() {
		ReloadableResourceBundleMessageSource messageSource = webApplicationConfig.messageSource();
		String englishMessage = messageSource.getMessage("message.test", new Object[]{}, Locale.ENGLISH);
		assertThat(englishMessage).isEqualTo("Message in English");
		String italianMessage = messageSource.getMessage("message.test", new Object[]{}, Locale.ITALIAN);
		assertThat(italianMessage).isEqualTo("Messaggio in Italiano");
	}

	@Test
	public void addInterceptors() {
		ArgumentCaptor<LocaleChangeInterceptor> argument = ArgumentCaptor.forClass(LocaleChangeInterceptor.class);
		webApplicationConfig.addInterceptors(interceptorRegistry);
		verify(interceptorRegistry).addInterceptor(argument.capture());
		assertThat(argument.getValue().getParamName()).isEqualTo("siteLanguage");
	}

	@Test
	public void localeChangeInterceptor() {
		LocaleChangeInterceptor localeChangeInterceptor = webApplicationConfig.localeChangeInterceptor();
		assertThat(localeChangeInterceptor.getParamName()).isEqualTo("siteLanguage");
	}

	@Test
	public void localeResolver() {
		CookieLocaleResolver cookieLocaleResolver = webApplicationConfig.localeResolver();
		assertThat(cookieLocaleResolver.getCookieName()).isEqualTo("CURRENT_LOCALE");
	}

}
