package it.f2informatica.services.domain.user;

import it.f2informatica.mongodb.domain.User;
import it.f2informatica.services.requests.UpdatePasswordRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.data.mongodb.core.query.Update.update;

@Service
public class PasswordUpdaterServiceImpl implements PasswordUpdaterService {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public boolean updatePassword(UpdatePasswordRequest request) {
		return arePasswordCompiledCorrectly(request)
				&& mongoTemplate.updateFirst(
				query(where("id").is(request.getUserId()).and("password").is(request.getCurrentPassword())),
				update("password", request.getNewPassword()), User.class
		).getLastError().ok();
	}

	private boolean arePasswordCompiledCorrectly(UpdatePasswordRequest request) {
		return StringUtils.hasText(request.getNewPassword())
				&& StringUtils.hasText(request.getPasswordConfirmed())
				&& request.getNewPassword().equals(request.getPasswordConfirmed());
	}

}
