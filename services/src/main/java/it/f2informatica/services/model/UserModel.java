package it.f2informatica.services.model;

import it.f2informatica.datastore.model.DataModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode
@ToString
public class UserModel implements DataModel {

	private String userId;

	private String username;

	private String password;

	private String firstName;

	private String lastName;

	private String email;

	private boolean notRemovable;

	private RoleModel role;

}
