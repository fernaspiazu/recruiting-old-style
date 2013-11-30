package it.f2informatica.webapp.converter;

import it.f2informatica.mongodb.domain.constants.Gender;
import org.springframework.core.convert.converter.Converter;

public class StringToGenderConverter implements Converter<String, Gender> {

	@Override
	public Gender convert(String source) {
		return Gender.getByAbbreviation(source);
	}

}
