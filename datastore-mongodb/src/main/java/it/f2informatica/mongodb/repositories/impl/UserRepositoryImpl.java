package it.f2informatica.mongodb.repositories.impl;

import com.google.common.collect.Lists;
import it.f2informatica.mongodb.domain.Role;
import it.f2informatica.mongodb.domain.User;
import it.f2informatica.mongodb.repositories.custom.CustomUserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.data.mongodb.core.query.Update.update;

@Repository
public class UserRepositoryImpl implements CustomUserRepository {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public Page<User> findAllExcludingUser(Pageable pageable, String username) {
		Query query = query(where("username").ne(username)).with(pageable);
		List<User> users = mongoTemplate.find(query, User.class);
		long count = users.size();
		return new PageImpl<>(users, pageable, count);
	}

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
		Query query = query(where("id").is(userId).and("notRemovable").is(false));
		mongoTemplate.remove(query, User.class);
	}

	@Override
	public boolean isCurrentPasswordValid(String userId, String currentPwd) {
		Query query = query(where("id").is(userId).and("password").is(currentPwd));
		User user = mongoTemplate.findOne(query, User.class);
		return user != null;
	}

	@Override
	public boolean updatePassword(String userId, String currentPwd, String newPwd, String confirmedPwd) {
		Query query = query(where("id").is(userId).and("password").is(currentPwd));
		return mongoTemplate.updateFirst(query, update("password", newPwd), User.class).getLastError().ok();
	}

}
