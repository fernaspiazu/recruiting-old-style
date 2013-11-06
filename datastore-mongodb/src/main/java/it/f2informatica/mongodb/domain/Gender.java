package it.f2informatica.mongodb.domain;

public enum Gender {

	MALE(1, "Male", "M"),
	FEMALE(2, "Female", "F");

	private int id;
	private String name;
	private String abbreviation;

	private Gender(int id, String name, String abbreviation) {
		this.id = id;
		this.name = name;
		this.abbreviation = abbreviation;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getAbbreviation() {
		return abbreviation;
	}

}
