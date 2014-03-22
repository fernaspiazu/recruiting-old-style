package it.f2informatica.services;

public enum LanguageProficiency {

	ELEMENTARY(1, "language.elementary"),
	LIMITED_WORKING(2, "language.limited_working"),
	PROFESSIONAL_WORKING(3, "language.professional_working"),
	FULL_PROFESSIONAL(4, "language.full_professional"),
	NATIVE_OR_BILINGUAL(5, "language.native");

	private int value;
	private String i18nLabel;

	private LanguageProficiency(int value, String i18nLabel) {
		this.value = value;
		this.i18nLabel = i18nLabel;
	}

	public int getValue() {
		return value;
	}

	public String getI18nLabel() {
		return i18nLabel;
	}
}
