package it.f2informatica.datastore.constant;

public enum Gender {

	MALE(1, "Male", "M"),
	FEMALE(2, "Female", "F");

	public static Gender getByAbbreviation(String abbreviation) {
		for (Gender gender : Gender.values()) {
			if (gender.getAbbreviation().equals(abbreviation)) {
				return gender;
			}
		}
		throw new GenderNotFoundException(abbreviation + " is not found as Gender");
	}

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

	public static class GenderNotFoundException extends IllegalArgumentException {
		public GenderNotFoundException(String message) {
			super(message);
		}
	}

}
