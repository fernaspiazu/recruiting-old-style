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
package it.f2informatica.webapp.utils;

import it.f2informatica.webapp.WebApplicationConfig;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.google.common.base.Preconditions.checkNotNull;

@Component
public class ShortDateFormatter {

	private SimpleDateFormat dateFormat = new SimpleDateFormat(WebApplicationConfig.GLOBAL_DATE_FORMAT);

	public Date parse(String value) throws ParseException {
		return dateFormat.parse(value);
	}

	public Date parse(String value, String pattern) throws ParseException {
		dateFormat.applyPattern(checkNotNull(pattern));
		return dateFormat.parse(value);
	}

}
