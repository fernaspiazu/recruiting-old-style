package it.f2informatica.services.requests.builders;

import it.f2informatica.mongodb.domain.constants.LanguageProficiency;
import it.f2informatica.services.requests.LanguageRequest;

public class LanguageRequestBuilder {

	private LanguageRequest lang= new LanguageRequest();

	private LanguageRequestBuilder(String language) {
		this.lang.setLanguage(language);
	}

	public static LanguageRequestBuilder english() {
		return new LanguageRequestBuilder("English");
	}

	public static LanguageRequestBuilder italian() {
		return new LanguageRequestBuilder("Italian");
	}

	public static LanguageRequestBuilder spanish() {
		return new LanguageRequestBuilder("Spanish");
	}

	public static LanguageRequestBuilder french() {
		return new LanguageRequestBuilder("French");
	}

	public LanguageRequestBuilder language(String language) {
		return new LanguageRequestBuilder(language);
	}

	public LanguageRequestBuilder withProficiency(LanguageProficiency proficiency) {
		lang.setProficiency(proficiency);
		return this;
	}

	public LanguageRequest build() {
		return lang;
	}

}
