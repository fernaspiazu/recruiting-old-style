package it.f2informatica.services.gateway.mongodb.converter;

import it.f2informatica.mongodb.domain.Language;
import it.f2informatica.services.gateway.EntityToModelConverter;
import it.f2informatica.services.model.LanguageModel;
import org.springframework.stereotype.Component;

import static it.f2informatica.services.model.builder.LanguageModelBuilder.languageModel;

@Component("languageToModelConverter")
public class MongoDBLanguageToModelConverter
		extends EntityToModelConverter<Language, LanguageModel> {

	@Override
	public LanguageModel convert(Language language) {
		return (language == null) ? null :
			languageModel(language.getLanguage())
				.withProficiency(language.getProficiency())
				.build();
	}

}
