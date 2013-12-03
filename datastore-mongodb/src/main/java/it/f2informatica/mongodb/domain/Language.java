package it.f2informatica.mongodb.domain;

import it.f2informatica.datastore.constant.LanguageProficiency;
import it.f2informatica.datastore.domain.MongoDBDocument;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode
@ToString
public class Language implements MongoDBDocument {

	private String language;

	private LanguageProficiency proficiency;

}
