package it.f2informatica.webapp.test.config;

import com.google.common.collect.Iterables;
import it.f2informatica.webapp.WebApplicationConfig;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.BeanNameViewResolver;
import org.thymeleaf.dialect.IDialect;
import org.thymeleaf.spring3.SpringTemplateEngine;
import org.thymeleaf.spring3.dialect.SpringStandardDialect;
import org.thymeleaf.spring3.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class WebApplicationConfigTest {

	static WebApplicationConfig webApplicationConfig;

	@Mock
	ResourceHandlerRegistry handlerRegistry;

	@Mock
	ResourceHandlerRegistration resourceHandlerRegistration;

	@Mock
	List<HandlerMethodArgumentResolver> argumentResolvers;

	@Mock
	ResourceLoader resourceLoader;

	@Mock
	ViewControllerRegistry viewControllerRegistry;

	@Mock
	ViewControllerRegistration viewControllerRegistration;

	@Mock
	DefaultServletHandlerConfigurer servletHandlerConfigurer;

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
	public void addViewControllerMappingRootUrlToRedirectHomePage() {
		ArgumentCaptor<String> urlPathArgument = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<String> viewNameArgument = ArgumentCaptor.forClass(String.class);

		when(viewControllerRegistry.addViewController("/")).thenReturn(viewControllerRegistration);
		doNothing().when(viewControllerRegistration).setViewName("redirect:/home");

		webApplicationConfig.addViewControllers(viewControllerRegistry);

		verify(viewControllerRegistry).addViewController(urlPathArgument.capture());
		verify(viewControllerRegistration).setViewName(viewNameArgument.capture());

		assertThat("/").isEqualTo(urlPathArgument.getValue());
		assertThat("redirect:/home").isEqualTo(viewNameArgument.getValue());
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

}
