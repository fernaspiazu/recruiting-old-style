package it.f2informatica.services.responses;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode
@ToString
public class UserResponse {

	private String userId;

	private String username;

	private boolean notRemovable;

	private String authorization;

}
