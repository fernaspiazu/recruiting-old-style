package it.f2informatica.services.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

@Data
@EqualsAndHashCode
@ToString
public class UserModel implements Serializable {
	private static final long serialVersionUID = -7604082585104660083L;

	private String userId;

	private String username;

	private String password;

	private String firstName;

	private String lastName;

	private String email;

	private boolean notRemovable;

	private RoleModel role;

	private String submitEvent;

}
