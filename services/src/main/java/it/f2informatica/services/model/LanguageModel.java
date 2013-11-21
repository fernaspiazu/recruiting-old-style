package it.f2informatica.services.model;

import it.f2informatica.mongodb.domain.constants.LanguageProficiency;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode
@ToString
public class LanguageModel {

	private String language;

	private LanguageProficiency proficiency;

}
