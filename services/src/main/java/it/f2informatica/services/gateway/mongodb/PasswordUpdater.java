package it.f2informatica.services.gateway.mongodb;

import it.f2informatica.mongodb.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.data.mongodb.core.query.Update.update;

@Component
class PasswordUpdater {

	@Autowired
	private MongoTemplate mongoTemplate;

	public boolean updatePassword(String userId, String currentPwd, String newPwd, String confirmedPwd) {
		Query query = query(where("id").is(userId).and("password").is(currentPwd));
		return arePasswordCompiledCorrectly(newPwd, confirmedPwd)
			&& mongoTemplate.updateFirst(query, update("password", newPwd), User.class)
				.getLastError().ok();
	}

	private boolean arePasswordCompiledCorrectly(String newPwd, String confirmedPwd) {
		return StringUtils.hasText(newPwd)
				&& StringUtils.hasText(confirmedPwd)
				&& newPwd.equals(confirmedPwd);
	}

}
