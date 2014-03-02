package it.f2informatica.services.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

@Data
@EqualsAndHashCode
@ToString
public class UpdatePasswordModel implements Serializable {

	private String userId;

	private String currentPassword;

	private String newPassword;

	private String passwordConfirmed;

}
