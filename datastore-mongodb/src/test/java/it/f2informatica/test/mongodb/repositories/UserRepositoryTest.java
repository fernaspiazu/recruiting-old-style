package it.f2informatica.test.mongodb.repositories;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import it.f2informatica.mongodb.domain.Role;
import it.f2informatica.mongodb.domain.User;
import it.f2informatica.mongodb.repositories.RoleRepository;
import it.f2informatica.mongodb.repositories.UserRepository;
import it.f2informatica.test.mongodb.DatastoreUtils;
import it.f2informatica.test.mongodb.constants.RoleConstants;
import it.f2informatica.test.mongodb.constants.UserConstants;
import org.apache.log4j.Logger;
import org.bson.types.ObjectId;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.data.mongodb.core.index.IndexField;
import org.springframework.data.mongodb.core.index.IndexInfo;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

public class UserRepositoryTest extends DatastoreUtils
		implements UserConstants, RoleConstants {
	private static final Logger log = Logger.getLogger(UserRepositoryTest.class);

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	static User savedUser= null;

	@BeforeClass
	public static void setUp() {
		UserRepository _userRepository = getBean(UserRepository.class);
		MongoTemplate _mongoTemplateTest = getBean("mongoTemplateTest", MongoTemplate.class);
		savedUser = createUser(USER_1_USERNAME, USER_1_PASSWORD);
		savedUser.setRole(getBean(RoleRepository.class).save(createRole(ROLE_1_NAME)));
		_userRepository.save(savedUser);
		_mongoTemplateTest.indexOps(User.class).ensureIndex(new Index().on("username", Sort.Direction.ASC));
	}

	@Test
	public void hasUserBeenSaved() {
		assertThat(savedUser).isNotNull();
		log.info("User saved >>> " + savedUser);
	}

	@Test
	public void findByUsername() {
		User userFoundByUsername = userRepository.findByUsername(USER_1_USERNAME);
		assertThat(userFoundByUsername).isNotNull();
		assertThat(userFoundByUsername.getUsername()).isEqualTo(USER_1_USERNAME);
		log.info("findByUsername(...) result >>> " + userFoundByUsername);
	}

	@Test
	public void findByUsernameAndPassword() {
		User user = userRepository.findByUsernameAndPassword(USER_1_USERNAME, USER_1_PASSWORD);
		assertThat(user).isNotNull();
		assertThat(user.getPassword()).isEqualTo(USER_1_PASSWORD);
	}

	@Test
	public void findByRoleName() {
		Iterable<User> administratorUsers = userRepository.findByRoleName(ROLE_1_NAME);
		log.info("Users retrieved >>> " + administratorUsers);
		User firstUserFound = Iterables.getFirst(administratorUsers, new User());
		assert firstUserFound != null;
		assertThat(firstUserFound.getRole().getName()).isEqualTo(ROLE_1_NAME);
	}

	@Test
	public void findByRoleNameReturningEmptyResult() {
		Iterable<User> administratorUsers = userRepository.findByRoleName("Unknown");
		assertThat(administratorUsers).isEmpty();
	}

	@Test
	public void findByRole() {
		Role role = roleRepository.findByName("Administrator");
		Iterable<User> administratorUsers = userRepository.findByRole(role);
		assertThat(administratorUsers).isNotEmpty();
	}

	@Test
	public void deleteRemovableUser() {
		User removableUser = createUser(USER_2_USERNAME, USER_2_PASSWORD);
		assertThat(removableUser.isRemovable()).isTrue();
		Role removableUserRole = createRole(ROLE_2_NAME);
		removableUser.setRole(roleRepository.save(removableUserRole));
		User removableUserSaved = userRepository.save(removableUser);
		userRepository.deleteRemovableUser(parseObjectIdToString(removableUserSaved.getId()));
		assertThat(userRepository.findOne(removableUserSaved.getId())).isNull();
	}

	@Test
	public void deleteNotRemovableUser() {
		User notRemovableUser = createUser(USER_2_USERNAME, USER_2_PASSWORD);
		assertThat(notRemovableUser.isRemovable()).isTrue();
		Role someRole = createRole(ROLE_2_NAME);
		notRemovableUser.setRole(roleRepository.save(someRole));
		notRemovableUser.setRemovable(false);
		User notRemovableUserSaved = userRepository.save(notRemovableUser);
		userRepository.deleteRemovableUser(parseObjectIdToString(notRemovableUserSaved.getId()));
		User notRemovableRetrieved = userRepository.findOne(notRemovableUserSaved.getId());
		assertThat(notRemovableRetrieved.getUsername()).isEqualTo(USER_2_USERNAME);
	}

	private String parseObjectIdToString(Object id) {
		return id == null ? null : id instanceof ObjectId ? ((ObjectId) id).toString() : id.toString();
	}

	@Test
	public void isIndexExistentInDocument() {
		List<IndexInfo> indexInfoList = mongoTemplateTest.indexOps(User.class).getIndexInfo();
		Optional<IndexInfo> usernameKeyIndex = findIndexOnUsernameField(indexInfoList);
		assertThat(usernameKeyIndex).isNotEqualTo(Optional.absent());
	}

	private Optional<IndexInfo> findIndexOnUsernameField(List<IndexInfo> indexInfoList) {
		return Iterables.tryFind(indexInfoList, new Predicate<IndexInfo>() {
			@Override
			public boolean apply(IndexInfo indexInfo) {
				IndexField usernameIndexKey = findKeyInsideIndexInformations(indexInfo);
				return !Optional.absent().equals(Optional.fromNullable(usernameIndexKey));
			}
		});
	}

	private IndexField findKeyInsideIndexInformations(IndexInfo indexInfo) {
		return Iterables.find(indexInfo.getIndexFields(), new Predicate<IndexField>() {
			@Override
			public boolean apply(IndexField indexField) {
				return "username".equals(indexField.getKey());
			}
		}, null);
	}

	private static User createUser(String username, String password) {
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		return user;
	}

	private static Role createRole(String roleName) {
		Role role = new Role();
		role.setName(roleName);
		return role;
	}

}
