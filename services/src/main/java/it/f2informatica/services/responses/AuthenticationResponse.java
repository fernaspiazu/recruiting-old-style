package it.f2informatica.services.responses;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode
@ToString
public class AuthenticationResponse {

	private String username;

	private String password;

	private String authorization;

}
