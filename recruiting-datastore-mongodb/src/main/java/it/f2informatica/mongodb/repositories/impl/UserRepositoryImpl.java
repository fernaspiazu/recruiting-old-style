/*
 * =============================================================================
 *
 *   Copyright (c) 2014, Fernando Aspiazu
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 * =============================================================================
 */
package it.f2informatica.mongodb.repositories.impl;

import com.google.common.collect.Lists;
import it.f2informatica.mongodb.domain.Role;
import it.f2informatica.mongodb.domain.User;
import it.f2informatica.mongodb.repositories.custom.UserRepositoryCustom;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.data.mongodb.core.query.Update.update;

@Component
public class UserRepositoryImpl implements UserRepositoryCustom {

  @Autowired
  private MongoTemplate mongoTemplate;

  @Override
  public Page<User> findAllExcludingUser(Pageable pageable, String username) {
    Query query = query(where("username").nin(username, "admin")).with(pageable);
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
    return mongoTemplate.updateFirst(query, update("password", newPwd), User.class).isUpdateOfExisting();
  }

}
