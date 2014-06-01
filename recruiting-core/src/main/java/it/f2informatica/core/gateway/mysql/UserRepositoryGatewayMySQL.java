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
package it.f2informatica.core.gateway.mysql;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.mysema.query.BooleanBuilder;
import com.mysema.query.jpa.impl.JPAQuery;
import it.f2informatica.core.gateway.EntityToModelConverter;
import it.f2informatica.core.gateway.UserRepositoryGateway;
import it.f2informatica.core.model.AuthenticationModel;
import it.f2informatica.core.model.RoleModel;
import it.f2informatica.core.model.UpdatePasswordModel;
import it.f2informatica.core.model.UserModel;
import it.f2informatica.mysql.MySQL;
import it.f2informatica.mysql.Persistence;
import it.f2informatica.mysql.domain.Role;
import it.f2informatica.mysql.domain.User;
import it.f2informatica.mysql.repositories.RoleRepository;
import it.f2informatica.mysql.repositories.UserRepository;
import it.f2informatica.pagination.services.PaginationService;
import it.f2informatica.pagination.services.QueryParameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static it.f2informatica.mysql.domain.QUser.*;

@MySQL
@Service
@Transactional
public class UserRepositoryGatewayMySQL implements UserRepositoryGateway {

  @PersistenceContext(name = Persistence.PERSISTENCE_UNIT_NAME)
  private EntityManager entityManager;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private RoleRepository roleRepository;

  @Autowired
  private PaginationService paginationService;

  @Autowired
  @Qualifier("mysqlUserToModelConverter")
  private EntityToModelConverter<User, UserModel> mysqlUserToModelConverter;

  @Override
  @Transactional(readOnly = true)
  public AuthenticationModel authenticationByUsername(String username) {
    User user = userRepository.findByUsername(username);
    AuthenticationModel authenticationModel = new AuthenticationModel();
    authenticationModel.setUsername(user.getUsername());
    authenticationModel.setPassword(user.getPassword());
    authenticationModel.setAuthorization(user.getRole().getName());
    return authenticationModel;
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
  public void updatePassword(UpdatePasswordModel request) {
    if (arePasswordCompiledCorrectly(request)) {
      userRepository.updatePassword(
        Long.parseLong(request.getUserId()),
        request.getCurrentPassword(),
        request.getNewPassword());
    }
  }

  private boolean arePasswordCompiledCorrectly(UpdatePasswordModel request) {
    return StringUtils.hasText(request.getNewPassword())
      && StringUtils.hasText(request.getPasswordConfirmed())
      && request.getNewPassword().equals(request.getPasswordConfirmed());
  }

  @Override
  public UserModel findUserById(String userId) {
    return mysqlUserToModelConverter.convert(userRepository.findOne(Long.parseLong(userId)));
  }

  @Override
  public UserModel findByUsername(String username) {
    return mysqlUserToModelConverter.convert(userRepository.findByUsername(username));
  }

  @Override
  public UserModel findByUsernameAndPassword(String username, String password) {
    return mysqlUserToModelConverter.convert(userRepository.findByUsernameAndPassword(username, password));
  }

  @Override
  public Page<UserModel> findAllExcludingCurrentUser(Pageable pageable, String usernameToExclude) {
    return new PageImpl<>(mysqlUserToModelConverter.convertList(userRepository.findAllExcludingCurrentUser(usernameToExclude)));
  }

  @Override
  public String getAllUsersPaginated(QueryParameters parameters, String currentUsername) {
    BooleanBuilder whereCondition = new BooleanBuilder(user.username.notEqualsIgnoreCase(currentUsername));
    if (StringUtils.hasText(parameters.getSearchCriteria())) {
      whereCondition.and(user.username.toLowerCase().like(contains(parameters.getSearchCriteria())));
    }
    JPAQuery jpaQuery = new JPAQuery(entityManager).from(user).where(whereCondition.getValue());
    return paginationService.getPaginatedResultAsJson(parameters, jpaQuery, user);
  }

  private static String contains(String value) {
    return "%" + value.toLowerCase() + "%";
  }

  @Override
  public Iterable<UserModel> findUsersByRoleName(String roleName) {
    return mysqlUserToModelConverter.convertIterable(userRepository.findByRoleName(roleName));
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
  public UserModel saveUser(UserModel userModel) {
    User user = new User();
    user.setUsername(userModel.getUsername());
    user.setPassword(userModel.getPassword());
    user.setEmail(userModel.getEmail());
    user.setFirstName(userModel.getFirstName());
    user.setLastName(userModel.getLastName());
    user.setRole(roleRepository.findOne(Long.parseLong(userModel.getRole().getRoleId())));
    User newUser = userRepository.save(user);
    return mysqlUserToModelConverter.convert(newUser);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
  public void updateUser(UserModel userModel) {
    userRepository.updateUser(
      Long.parseLong(userModel.getUserId()),
      userModel.getUsername(),
      userModel.getFirstName(),
      userModel.getLastName(),
      userModel.getEmail(),
      roleRepository.findOne(Long.parseLong(userModel.getRole().getRoleId())));
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
  public void deleteUser(String userId) {
    userRepository.delete(Long.parseLong(userId));
  }

  @Override
  public Iterable<RoleModel> loadRoles() {
    return Iterables.transform(roleRepository.findAll(), roleToRoleModel());
  }

  @Override
  public RoleModel findRoleByName(String roleName) {
    return roleToRoleModel().apply(roleRepository.findByName(roleName));
  }

  private Function<Role, RoleModel> roleToRoleModel() {
    return new Function<Role, RoleModel>() {
      @Override
      public RoleModel apply(Role role) {
        RoleModel model = new RoleModel();
        model.setRoleId(String.valueOf(role.getId()));
        model.setRoleName(role.getName());
        return model;
      }
    };
  }

  @Override
  public boolean isCurrentPasswordValid(String userId, String currentPwd) {
    return userRepository.findByIdAndPassword(Long.parseLong(userId), currentPwd) != null;
  }

}
