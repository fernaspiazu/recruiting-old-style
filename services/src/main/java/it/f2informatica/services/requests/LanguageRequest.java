package it.f2informatica.services.requests;

import it.f2informatica.mongodb.domain.constants.LanguageProficiency;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode
@ToString
public class LanguageRequest {

	private String language;

	private LanguageProficiency proficiency;

}
