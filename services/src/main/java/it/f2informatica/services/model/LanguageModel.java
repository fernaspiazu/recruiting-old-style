package it.f2informatica.services.model;

import it.f2informatica.datastore.constant.LanguageProficiency;
import it.f2informatica.datastore.model.DataModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode
@ToString
public class LanguageModel implements DataModel {
	private static final long serialVersionUID = 5650873740412761162L;

	private String language;

	private LanguageProficiency proficiency;

	private String knowledge;

}
