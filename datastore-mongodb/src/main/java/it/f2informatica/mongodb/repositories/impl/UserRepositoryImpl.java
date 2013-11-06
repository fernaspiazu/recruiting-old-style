package it.f2informatica.mongodb.repositories.impl;

import com.google.common.collect.Lists;
import it.f2informatica.mongodb.domain.Role;
import it.f2informatica.mongodb.domain.User;
import it.f2informatica.mongodb.repositories.customrepositories.UserRepositoryCustom;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Repository
public class UserRepositoryImpl implements UserRepositoryCustom {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public Iterable<User> findByRoleName(String roleName) {
		Role role = mongoTemplate.findOne(query(where("name").is(roleName)), Role.class);
		if (role == null) {
			return Lists.newArrayList();
		}
		final ObjectId roleObjectId = new ObjectId(role.getId());
		return mongoTemplate.find(query(where("role.$id").is(roleObjectId)), User.class);
	}

	@Override
	public void deleteRemovableUser(String userId) {
		mongoTemplate.remove(
				query(where("id").is(userId)
						.and("removable").is(true)), User.class);
	}

}
