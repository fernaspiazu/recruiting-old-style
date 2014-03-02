package it.f2informatica.test.mongodb.repositories;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import it.f2informatica.mongodb.domain.Role;
import it.f2informatica.mongodb.domain.User;
import it.f2informatica.mongodb.repositories.RoleRepository;
import it.f2informatica.mongodb.repositories.UserRepository;
import it.f2informatica.test.mongodb.DatastoreUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.data.mongodb.core.index.IndexField;
import org.springframework.data.mongodb.core.index.IndexInfo;

import java.util.List;

import static it.f2informatica.test.mongodb.builders.RoleDataBuilder.role;
import static it.f2informatica.test.mongodb.builders.UserDataBuilder.user;
import static org.fest.assertions.Assertions.assertThat;

public class UserRepositoryTest extends DatastoreUtils {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Test
	public void crudTest() {
		Role role = saveRoleUser();
		User user = saveSimpleUser(role);
		User hughJackmanUser = saveHughJackmanUser(role);
		Role roleAdmin = saveRoleAdmin();
		User savedNotRemovableUser = saveNotRemovableUser(roleAdmin);
		mongoTemplateTest.indexOps(User.class).ensureIndex(new Index().on("username", Sort.Direction.ASC));

		assertThatUserHasBeenSaved(user);
		findByUsername();
		findByUsernameAndPassword();
		findAllExceptCurrentUser(hughJackmanUser);
		findByRoleName();
		findByRole();
		findByRoleNameReturningEmptyResult();
		isIndexExistentInDocument();
		deleteRemovableUser(user);
		deleteNotRemovableUser(savedNotRemovableUser);
	}

	private User saveSimpleUser(Role role) {
		return userRepository.save(user().withRole(role).build());
	}

	private Role saveRoleUser() {
		return roleRepository.save(role().withId("52820f4634bdf55624303fbf").withAuthorization("USER").build());
	}

	private Role saveRoleAdmin() {
		return roleRepository.save(role().thatIsAdministrator());
	}

	private User saveNotRemovableUser(Role role) {
		return userRepository.save(
			user()
				.withId("52820f5b34bdf55624303fc0")
				.withUsername("super_user")
				.withPassword("super_user")
				.withFirstName("Barack")
				.withLastName("Obama")
				.withEmail("king_of_war@hell.com")
				.withRole(role)
				.thatIsNotRemovable()
				.build()
		);
	}

	private User saveHughJackmanUser(Role role) {
		return userRepository.save(
			user()
				.withId("52820f5b34bdf55624303fc1")
				.withUsername("hughjackman")
				.withPassword("wolverine")
				.withFirstName("Hugh")
				.withLastName("Jackman")
				.withEmail("hughjackman@marvel.com")
				.withRole(role)
				.thatIsRemovable()
				.build()
		);
	}

	private void assertThatUserHasBeenSaved(User savedUser) {
		assertThat(savedUser).isNotNull();
	}

	private void findByUsername() {
		String username = user().build().getUsername();
		User userFoundByUsername = userRepository.findByUsername(username);
		assertThat(userFoundByUsername.getUsername()).isEqualTo(username);
	}

	private void findByUsernameAndPassword() {
		User user = userRepository.findByUsernameAndPassword("jhon_kent77", "okisteralio");
		assertThat(user.getPassword()).isEqualTo("okisteralio");
	}

	private void findAllExceptCurrentUser(User hughJackmanUser) {
		Page<User> users = userRepository.findAllExcludingUser(new PageRequest(0, 10), "super_user");
		assertThat(users).hasSize(2).contains(hughJackmanUser);
	}

	@SuppressWarnings("ConstantConditions")
	private void findByRoleName() {
		User firstUserFound = Iterables.getFirst(userRepository.findByRoleName("USER"), new User());
		assertThat(firstUserFound.getRole().getName()).isEqualTo("USER");
	}

	private void findByRole() {
		Iterable<User> administratorUsers = userRepository.findByRole(roleRepository.findByName("USER"));
		assertThat(administratorUsers).isNotEmpty();
	}

	private void findByRoleNameReturningEmptyResult() {
		assertThat(userRepository.findByRoleName("Unknown")).isEmpty();
	}

	private void isIndexExistentInDocument() {
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

	private void deleteRemovableUser(User user) {
		String userId = String.valueOf(user.getId());
		userRepository.deleteRemovableUser(userId);
		assertThat(userRepository.findOne(userId)).isNull();
	}

	private void deleteNotRemovableUser(User notRemovableUser) {
		String userId = String.valueOf(notRemovableUser.getId());
		userRepository.deleteRemovableUser(userId);
		assertThat(userRepository.findOne(userId)).isNotNull();
	}

}
