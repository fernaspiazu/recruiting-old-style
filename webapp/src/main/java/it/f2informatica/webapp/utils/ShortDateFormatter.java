package it.f2informatica.webapp.utils;

import it.f2informatica.webapp.context.WebAppContext;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.google.common.base.Preconditions.checkNotNull;

@Component
public class ShortDateFormatter {

	private SimpleDateFormat dateFormat = new SimpleDateFormat(WebAppContext.GLOBAL_DATE_FORMAT);

	public Date parse(String value) throws ParseException {
		return dateFormat.parse(value);
	}

	public Date parse(String value, String pattern) throws ParseException {
		dateFormat.applyPattern(checkNotNull(pattern));
		return dateFormat.parse(value);
	}

}
