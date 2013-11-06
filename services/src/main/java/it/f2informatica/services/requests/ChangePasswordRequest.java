package it.f2informatica.services.requests;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode
@ToString
public class ChangePasswordRequest {

	private String userId;

	private String currentPassword;

	private String newPassword;

	private String passwordConfirmed;

}
