package it.f2informatica.services.requests.builders;

import it.f2informatica.services.requests.UpdatePasswordRequest;

public class UpdatePasswordRequestBuilder {

	private UpdatePasswordRequest request = new UpdatePasswordRequest();

	public static UpdatePasswordRequestBuilder updatePasswordRequest() {
		return new UpdatePasswordRequestBuilder();
	}

	public UpdatePasswordRequestBuilder userIdToFind(String userId) {
		request.setUserId(userId);
		return this;
	}

	public UpdatePasswordRequestBuilder withCurrentPassword(String currentPassword) {
		request.setCurrentPassword(currentPassword);
		return this;
	}

	public UpdatePasswordRequestBuilder withNewPassword(String newPassword) {
		request.setNewPassword(newPassword);
		return this;
	}

	public UpdatePasswordRequestBuilder withPasswordConfirmed(String passwordConfirmed) {
		request.setPasswordConfirmed(passwordConfirmed);
		return this;
	}

	public UpdatePasswordRequest build() {
		return request;
	}

}
