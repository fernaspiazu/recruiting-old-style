package it.f2informatica.webapp.utils;

import com.google.common.collect.Lists;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LanguageHelper {

	public List<String> getLanguages() {
		return Lists.newArrayList(
			"english",
			"italian",
			"spanish",
			"german",
			"dutch"
		);
	}

	public List<String> getProficiencies() {
		return Lists.newArrayList(
			"elementary",
			"limited_working",
			"professional_working",
			"full_professional",
			"native_or_bilingual"
		);
	}

}
