package it.f2informatica.services.requests.builders;

import it.f2informatica.services.requests.UserRequest;

public class UserRequestBuilder {

	private UserRequest userRequest = new UserRequest();

	private UserRequestBuilder() {
		userRequest.setNotRemovable(false);
	}

	public static UserRequestBuilder userRequest() {
		return new UserRequestBuilder();
	}

	public UserRequestBuilder withUserId(String userId) {
		userRequest.setUserId(userId);
		return this;
	}

	public UserRequestBuilder withUsername(String username) {
		userRequest.setUsername(username);
		return this;
	}

	public UserRequestBuilder withPassword(String password) {
		userRequest.setPassword(password);
		return this;
	}

	public UserRequestBuilder withRoleId(String roleId) {
		userRequest.setRoleId(roleId);
		return this;
	}

	public UserRequestBuilder thatIsNotRemovable() {
		userRequest.setNotRemovable(true);
		return this;
	}

	public UserRequest build() {
		return userRequest;
	}

}
