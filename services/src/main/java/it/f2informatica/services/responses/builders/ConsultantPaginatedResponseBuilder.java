package it.f2informatica.services.responses.builders;

import it.f2informatica.services.responses.ConsultantPaginatedResponse;

public class ConsultantPaginatedResponseBuilder {

	private ConsultantPaginatedResponse response = new ConsultantPaginatedResponse();

	public static ConsultantPaginatedResponseBuilder consultantPaginatedResponse() {
		return new ConsultantPaginatedResponseBuilder();
	}

	public ConsultantPaginatedResponseBuilder withConsultantId(String consultantId) {
		response.setConsultantId(consultantId);
		return this;
	}

	public ConsultantPaginatedResponseBuilder withConsultantNo(String consultantNo) {
		response.setConsultantNo(consultantNo);
		return this;
	}

	public ConsultantPaginatedResponseBuilder withFirstName(String firstName) {
		response.setFirstName(firstName);
		return this;
	}

	public ConsultantPaginatedResponseBuilder withLastName(String lastName) {
		response.setLastName(lastName);
		return this;
	}

	public ConsultantPaginatedResponseBuilder withAge(int age) {
		response.setAge(age);
		return this;
	}

	public ConsultantPaginatedResponseBuilder withSkills(String skills) {
		response.setSkills(skills);
		return this;
	}

	public ConsultantPaginatedResponse build() {
		return response;
	}

}
