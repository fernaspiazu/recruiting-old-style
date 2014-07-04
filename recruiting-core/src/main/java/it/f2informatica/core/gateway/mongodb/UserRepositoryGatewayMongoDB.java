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
package it.f2informatica.core.gateway.mongodb;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import it.f2informatica.core.gateway.EntityToModelConverter;
import it.f2informatica.core.gateway.UserRepositoryGateway;
import it.f2informatica.core.model.AuthenticationModel;
import it.f2informatica.core.model.RoleModel;
import it.f2informatica.core.model.UpdatePasswordModel;
import it.f2informatica.core.model.UserModel;
import it.f2informatica.mongodb.MongoDB;
import it.f2informatica.mongodb.domain.Role;
import it.f2informatica.mongodb.domain.User;
import it.f2informatica.mongodb.repositories.RoleRepository;
import it.f2informatica.mongodb.repositories.UserRepository;
import it.f2informatica.pagination.repository.mongodb.MongoQueryPredicate;
import it.f2informatica.pagination.services.MongoDBPaginationService;
import it.f2informatica.pagination.services.QueryParameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import static it.f2informatica.mongodb.domain.builder.UserBuilder.user;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.data.mongodb.core.query.Update.update;

@MongoDB
@Service
public class UserRepositoryGatewayMongoDB implements UserRepositoryGateway {

  @Autowired
  private MongoTemplate mongoTemplate;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private RoleRepository roleRepository;

	@Autowired
	private MongoDBPaginationService mongoDBPaginationService;

  @Autowired
  @Qualifier("userToModelConverter")
  private EntityToModelConverter<User, UserModel> userToModelConverter;

  @Override
  public AuthenticationModel authenticationByUsername(String username) {
    User user = userRepository.findByUsername(username);
    AuthenticationModel authenticationModel = new AuthenticationModel();
    authenticationModel.setUsername(user.getUsername());
    authenticationModel.setPassword(user.getPassword());
    authenticationModel.setAuthorization(user.getRole().getName());
    return authenticationModel;
  }

  @Override
  public void updatePassword(UpdatePasswordModel request) {
    if (arePasswordCompiledCorrectly(request)) {
	    Query query = query(where("id").is(request.getUserId()).and("password").is(request.getCurrentPassword()));
	    mongoTemplate.updateFirst(query, update("password", request.getCurrentPassword()), User.class);
    }
  }

  private boolean arePasswordCompiledCorrectly(UpdatePasswordModel request) {
    return StringUtils.hasText(request.getNewPassword())
      && StringUtils.hasText(request.getPasswordConfirmed())
      && request.getNewPassword().equals(request.getPasswordConfirmed());
  }

  @Override
  public UserModel findUserById(String userId) {
    return userToModelConverter.convert(userRepository.findOne(userId));
  }

  @Override
  public UserModel findByUsername(String username) {
    return userToModelConverter.convert(userRepository.findByUsername(username));
  }

  @Override
  public UserModel findByUsernameAndPassword(String username, String password) {
    return userToModelConverter.convert(userRepository.findByUsernameAndPassword(username, password));
  }

  @Override
  public Page<UserModel> findAllExcludingCurrentUser(Pageable pageable, String usernameToExclude) {
    return new PageImpl<>(Lists.newArrayList(
      userToModelConverter.convertIterable(userRepository.findAllExcludingUser(usernameToExclude, pageable))
    ));
  }

  @Override
  public String getAllUsersPaginated(QueryParameters parameters, final String currentUsername) {
	  MongoQueryPredicate<User> queryPredicate = new MongoQueryPredicate<User>(User.class) {
		  @Override
		  public Query queryPredicate() {
			  return query(where("username").not().regex(currentUsername));
		  }
	  };
	  return mongoDBPaginationService.getPaginatedResultAsJson(parameters, queryPredicate);
  }

  @Override
  public UserModel saveUser(UserModel userModel) {
    User newUser = userRepository.save(user()
      .withUsername(userModel.getUsername())
      .withPassword(userModel.getPassword())
      .withRole(roleRepository.findOne(userModel.getRole().getRoleId()))
      .withLastName(userModel.getLastName())
      .withFirstName(userModel.getFirstName())
      .withEmail(userModel.getEmail())
      .thatIsRemovable()
      .build());
    return userToModelConverter.convert(newUser);
  }

  @Override
  public void updateUser(UserModel userModel) {
    Query query = query(where("id").is(userModel.getUserId()));
    Update update = new Update()
      .set("username", userModel.getUsername())
      .set("role", roleRepository.findOne(userModel.getRole().getRoleId()))
      .set("lastName", userModel.getLastName())
      .set("firstName", userModel.getFirstName())
      .set("email", userModel.getEmail());
    mongoTemplate.updateFirst(query, update, User.class).getLastError().ok();
  }

  @Override
  public void deleteUser(String userId) {
    userRepository.deleteUser(userId);
  }

  @Override
  public Iterable<RoleModel> loadRoles() {
    return Iterables.transform(roleRepository.findAll(), roleToRoleModel());
  }

  @Override
  public RoleModel findRoleByName(String roleName) {
    return roleToRoleModel().apply(roleRepository.findByName(roleName));
  }

  @Override
  public boolean isCurrentPasswordValid(String userId, String currentPwd) {
	  return mongoTemplate.count(query(where("id").is(userId).and("password").is(currentPwd)), User.class) != 0;
  }

  private Function<Role, RoleModel> roleToRoleModel() {
    return new Function<Role, RoleModel>() {
      @Override
      public RoleModel apply(Role role) {
        RoleModel model = new RoleModel();
        model.setRoleId(role.getId());
        model.setRoleName(role.getName());
        return model;
      }
    };
  }

}
