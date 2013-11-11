package it.f2informatica.services.requests;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode
@ToString
public class UserRequest {

	private String userId;

	private String username;

	private String password;

	private boolean notRemovable;

	private String roleId;

}
