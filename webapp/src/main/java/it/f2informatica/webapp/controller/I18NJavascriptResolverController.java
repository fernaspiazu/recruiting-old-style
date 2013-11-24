package it.f2informatica.webapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;

@Controller
public class I18NJavascriptResolverController {

	@Autowired
	private LocaleResolver localeResolver;

	@Autowired
	private MessageSource messageSource;

	@RequestMapping(value = "/js/resolvei18nCode", method = RequestMethod.POST)
	@ResponseBody
	public String resolveMessage(@RequestParam("code") String code, HttpServletRequest request) {
		return messageSource.getMessage(code, emptyArray(), localeResolver.resolveLocale(request));
	}

	private Object[] emptyArray() {
		return new Object[0];
	}

}
