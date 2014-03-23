package it.f2informatica.mongodb.domain.builder;

import it.f2informatica.mongodb.domain.Language;

public class LanguageBuilder {

	private Language lang = new Language();

	private LanguageBuilder(String language) {
		this.lang.setLanguage(language);
	}

	public static LanguageBuilder english() {
		return new LanguageBuilder("English");
	}

	public static LanguageBuilder italian() {
		return new LanguageBuilder("Italian");
	}

	public static LanguageBuilder spanish() {
		return new LanguageBuilder("Spanish");
	}

	public static LanguageBuilder french() {
		return new LanguageBuilder("French");
	}

	public static LanguageBuilder language(String language) {
		return new LanguageBuilder(language);
	}

	public LanguageBuilder withProficiency(String proficiency) {
		lang.setProficiency(proficiency);
		return this;
	}

	public Language build() {
		return lang;
	}

}