package it.f2informatica.test.services.builder;

import it.f2informatica.services.requests.UpdatePasswordRequest;

public class UpdatePasswordRequestBuilder {

	private String userId = "52820f6f34bdf55624303fc1";
	private String currentPassword = "currentPassword";
	private String newPassword = "newPassword";
	private String passwordConfirmed = "newPassword";

	public static UpdatePasswordRequestBuilder updatePasswordRequest() {
		return new UpdatePasswordRequestBuilder();
	}

	public UpdatePasswordRequestBuilder withUserId(String userId) {
		this.userId = userId;
		return this;
	}

	public UpdatePasswordRequestBuilder withCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
		return this;
	}

	public UpdatePasswordRequestBuilder withoutNewPassword() {
		return withNewPassword(null);
	}

	public UpdatePasswordRequestBuilder withNewPassword(String newPassword) {
		this.newPassword = newPassword;
		return this;
	}

	public UpdatePasswordRequestBuilder withoutConfirmedPassword() {
		return withConfirmedPassword(null);
	}

	public UpdatePasswordRequestBuilder withConfirmedPassword(String confirmedPassword) {
		this.passwordConfirmed = confirmedPassword;
		return this;
	}

	public UpdatePasswordRequest build() {
		UpdatePasswordRequest request = new UpdatePasswordRequest();
		request.setUserId(userId);
		request.setCurrentPassword(currentPassword);
		request.setNewPassword(newPassword);
		request.setPasswordConfirmed(passwordConfirmed);
		return request;
	}

}
