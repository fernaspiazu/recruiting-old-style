package it.f2informatica.webapp.controller;

import com.google.common.collect.Lists;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/consultant")
public class ConsultantController {

	@RequestMapping(value = "/consultant-management", method = RequestMethod.GET)
	public String consultantManagementPage(ModelMap modelMap, Pageable pageable) {
		//modelMap.addAttribute("consultants", consultantServiceGateway.findAllConsultants(pageable));
		modelMap.addAttribute("consultants", Lists.newArrayList());
		return "consultant/consultants";
	}

}
