package it.f2informatica.services.model;

import it.f2informatica.mongodb.domain.constants.LanguageProficiency;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

@Data
@EqualsAndHashCode
@ToString
public class LanguageModel implements Serializable {

	private String language;

	private LanguageProficiency proficiency;

}
