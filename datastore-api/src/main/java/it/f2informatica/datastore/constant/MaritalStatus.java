package it.f2informatica.datastore.constant;

public enum MaritalStatus {

	SINGLE(1, "Single"),
	ENGAGED(2, "Engaged"),
	MARRIED(3, "Married"),
	DIVORCED(4, "Divorced"),
	WIDOWED(5, "Widowed");

	private int id;
	private String name;

	private MaritalStatus(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

}
