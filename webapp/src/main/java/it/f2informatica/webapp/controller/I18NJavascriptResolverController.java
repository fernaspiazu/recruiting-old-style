package it.f2informatica.webapp.controller;

import it.f2informatica.webapp.utils.CurrentHttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/js/resolvei18nCode")
public class I18NJavascriptResolverController {

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private CurrentHttpServletRequest currentHttpRequest;

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public String resolveMessage(@RequestParam("code") String code) {
		return messageSource.getMessage(code, new Object[0], currentHttpRequest.getRequestLocale());
	}

}
