package it.f2informatica.services.gateway.mysql.converter;

import it.f2informatica.mysql.domain.Language;
import it.f2informatica.services.gateway.EntityToModelConverter;
import it.f2informatica.services.model.LanguageModel;
import org.springframework.stereotype.Component;

import static it.f2informatica.services.model.builder.LanguageModelBuilder.languageModel;

@Component("mysqlLanguageToModelConverter")
public class MySQLLanguageToModelConverter extends EntityToModelConverter<Language, LanguageModel> {

  @Override
  public LanguageModel convert(Language language) {
    return (language == null || language.getId() == null) ? null :
      languageModel(language.getId().getLang())
        .withProficiency(language.getProficiency())
        .build();
  }

}
