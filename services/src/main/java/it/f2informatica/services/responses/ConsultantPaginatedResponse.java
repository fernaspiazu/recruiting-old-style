package it.f2informatica.services.responses;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode
@ToString
public class ConsultantPaginatedResponse {

	private String consultantId;

	private String consultantNo;

	private String firstName;

	private String lastName;

	private int age;

	private String skills;

	private String photo; // TODO: GridFSFile

}
