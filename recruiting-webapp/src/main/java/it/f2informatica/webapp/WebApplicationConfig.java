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
package it.f2informatica.webapp;

import it.f2informatica.pagination.DatePatterns;
import it.f2informatica.webapp.handler.Pages;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.format.datetime.DateFormatterRegistrar;
import org.springframework.format.datetime.DateTimeFormatAnnotationFormatterFactory;
import org.springframework.format.number.NumberFormatAnnotationFormatterFactory;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.view.BeanNameViewResolver;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import java.util.List;

@Configuration
@EnableWebMvc
@EnableSpringDataWebSupport
@ComponentScan(basePackages = {"it.f2informatica.webapp"})
public class WebApplicationConfig extends WebMvcConfigurerAdapter {
  public static final String GLOBAL_DATE_FORMAT = DatePatterns.GLOBAL_DATE_FORMAT;
  public static final String CURRENT_LOCALE_COOKIE = "CURRENT_LOCALE";
  public static final String LANGUAGE = "siteLanguage";

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/static/**").addResourceLocations("/static/");
  }

  @Override
  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
    argumentResolvers.add(new PageableHandlerMethodArgumentResolver());
  }

  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
    registry.addViewController("/").setViewName("redirect:/home");
    registry.addViewController("/404").setViewName(Pages.PAGE_NOT_FOUND);
    registry.addViewController("/500").setViewName(Pages.SERVER_ERROR);
  }

  @Override
  public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
    configurer.enable();
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(localeChangeInterceptor());
  }

  @Bean
  public LocaleChangeInterceptor localeChangeInterceptor() {
    LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
    localeChangeInterceptor.setParamName(LANGUAGE);
    return localeChangeInterceptor;
  }

  @Bean
  public CookieLocaleResolver localeResolver() {
    CookieLocaleResolver cookieLocaleResolver = new CookieLocaleResolver();
    cookieLocaleResolver.setCookieName(CURRENT_LOCALE_COOKIE);
    return cookieLocaleResolver;
  }

  @Bean
  public StandardServletMultipartResolver multipartResolver() {
    return new StandardServletMultipartResolver();
  }

  @Bean
  public BeanNameViewResolver beanNameViewResolver() {
    BeanNameViewResolver viewResolver = new BeanNameViewResolver();
    viewResolver.setOrder(1);
    return viewResolver;
  }

  @Bean
  public ThymeleafViewResolver thymeleafViewResolver() {
    ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
    viewResolver.setTemplateEngine(thymeleafTemplateEngine());
    viewResolver.setCharacterEncoding("UTF-8");
    viewResolver.setOrder(2);
    return viewResolver;
  }

  @Bean
  public SpringTemplateEngine thymeleafTemplateEngine() {
    SpringTemplateEngine templateEngine = new SpringTemplateEngine();
    templateEngine.setTemplateResolver(thymeleafTemplateResolver());
    return templateEngine;
  }

  @Bean
  public ServletContextTemplateResolver thymeleafTemplateResolver() {
    ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver();
    templateResolver.setPrefix("/WEB-INF/views/");
    templateResolver.setSuffix(".html");
    templateResolver.setTemplateMode("HTML5");
    templateResolver.setCharacterEncoding("UTF-8");
    // Uncomment these lines in order to use
    // cache when resolving templates.
    templateResolver.setCacheable(false);
    templateResolver.setCacheTTLMs(0L);
    // -------------------------------------
    return templateResolver;
  }

  @Bean
  public ReloadableResourceBundleMessageSource messageSource() {
    ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
    messageSource.setBasenames(basenames());
    messageSource.setDefaultEncoding("UTF-8");
    messageSource.setCacheSeconds(1);
    messageSource.setFallbackToSystemLocale(false);
    return messageSource;
  }

  private String[] basenames() {
    return new String[]{
      "/WEB-INF/i18n/global",
      "/WEB-INF/i18n/user",
      "/WEB-INF/i18n/consultant",
      "/WEB-INF/i18n/months",
      "/WEB-INF/i18n/language"
    };
  }

  @Bean
  public FormattingConversionService mvcConversionService() {
    DefaultFormattingConversionService conversionService = new DefaultFormattingConversionService(false);
    addFormattersForFieldAnnotation(conversionService);
    DateFormatterRegistrar registrar = new DateFormatterRegistrar();
    registrar.setFormatter(new DateFormatter(GLOBAL_DATE_FORMAT));
    registrar.registerFormatters(conversionService);
    return conversionService;
  }

  private void addFormattersForFieldAnnotation(DefaultFormattingConversionService conversionService) {
    conversionService.addFormatterForFieldAnnotation(new NumberFormatAnnotationFormatterFactory());
    conversionService.addFormatterForFieldAnnotation(new DateTimeFormatAnnotationFormatterFactory());
  }

}
