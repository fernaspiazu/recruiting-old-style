package it.f2informatica.services.gateway.converter;

import it.f2informatica.mongodb.domain.Language;
import it.f2informatica.services.gateway.EntityToModelConverter;
import it.f2informatica.services.model.LanguageModel;

import static it.f2informatica.services.model.builder.LanguageModelBuilder.languageModel;

public class MongoDBLanguageToLanguageModelConverter
		extends EntityToModelConverter<Language, LanguageModel> {

	@Override
	public LanguageModel convert(Language language) {
		return (language == null) ? null :
			languageModel(language.getLanguage())
				.withProficiency(language.getProficiency())
				.build();
	}

}
