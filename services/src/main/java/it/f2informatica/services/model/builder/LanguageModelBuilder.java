package it.f2informatica.services.model.builder;

import it.f2informatica.datastore.constant.LanguageProficiency;
import it.f2informatica.services.model.LanguageModel;

public class LanguageModelBuilder {

	private LanguageModel lang = new LanguageModel();

	private LanguageModelBuilder(String language) {
		this.lang.setLanguage(language);
	}

	public static LanguageModelBuilder english() {
		return new LanguageModelBuilder("English");
	}

	public static LanguageModelBuilder italian() {
		return new LanguageModelBuilder("Italian");
	}

	public static LanguageModelBuilder spanish() {
		return new LanguageModelBuilder("Spanish");
	}

	public static LanguageModelBuilder french() {
		return new LanguageModelBuilder("French");
	}

	public static LanguageModelBuilder languageModel() {
		return new LanguageModelBuilder(null);
	}

	public static LanguageModelBuilder languageModel(String language) {
		return new LanguageModelBuilder(language);
	}

	public LanguageModelBuilder withProficiency(LanguageProficiency proficiency) {
		lang.setProficiency(proficiency);
		return this;
	}

	public LanguageModel build() {
		return lang;
	}

}
