package it.f2informatica.test.mongodb.builders;

import it.f2informatica.mongodb.domain.Language;

public class LanguageDataBuilder {

	private String language;
	private String proficiency = "professional_working";

	private LanguageDataBuilder(String language) {
		this.language = language;
	}

	public static LanguageDataBuilder english() {
		return new LanguageDataBuilder("English");
	}

	public static LanguageDataBuilder italian() {
		return new LanguageDataBuilder("Italian");
	}

	public static LanguageDataBuilder spanish() {
		return new LanguageDataBuilder("Spanish");
	}

	public LanguageDataBuilder withProficiency(String proficiency) {
		this.proficiency = proficiency;
		return this;
	}

	public Language build() {
		Language lang = new Language();
		lang.setLanguage(language);
		lang.setProficiency(proficiency);
		return lang;
	}

}
