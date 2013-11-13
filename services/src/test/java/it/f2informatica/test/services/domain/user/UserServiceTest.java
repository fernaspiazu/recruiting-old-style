package it.f2informatica.test.services.domain.user;

import com.google.common.collect.Lists;
import com.mongodb.CommandResult;
import com.mongodb.WriteResult;
import it.f2informatica.mongodb.domain.Role;
import it.f2informatica.mongodb.domain.User;
import it.f2informatica.mongodb.domain.builders.RoleBuilder;
import it.f2informatica.mongodb.domain.constants.Authority;
import it.f2informatica.mongodb.repositories.RoleRepository;
import it.f2informatica.mongodb.repositories.UserRepository;
import it.f2informatica.services.domain.user.UserService;
import it.f2informatica.services.domain.user.impl.UserServiceImpl;
import it.f2informatica.services.requests.UserRequest;
import it.f2informatica.services.responses.RoleResponse;
import it.f2informatica.services.responses.UserResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;

import static it.f2informatica.mongodb.domain.builders.RoleBuilder.role;
import static it.f2informatica.mongodb.domain.builders.UserBuilder.user;
import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

	@Mock
	private MongoTemplate mongoTemplate;

	@Mock
	private UserRepository userRepository;

	@Mock
	private RoleRepository roleRepository;

	@InjectMocks
	private UserService userService = new UserServiceImpl();

	@Test
	public void findUserById() {
		when(userRepository.findOne(anyString())).thenReturn(getUser());
		UserResponse userResponse = userService.findUserById("1234567890");
		assertThat(userResponse.getUsername()).isEqualTo("jhon");
	}

	@Test
	public void findByUsername() {
		when(userRepository.findByUsername(anyString())).thenReturn(getUser());
		UserResponse userResponse = userService.findByUsername("jhon");
		assertThat(userResponse.getUsername()).isEqualTo("jhon");
	}

	@Test
	public void findByUsernameAndPassword() {
		when(userRepository.findByUsernameAndPassword(anyString(), anyString())).thenReturn(getUser());
		UserResponse userResponse = userService.findByUsernameAndPassword("jhon", "jhon78*");
		assertThat(userResponse.getUsername()).isEqualTo("jhon");
	}

	@Test
	public void findAllWithPageable() {
		PageImpl<User> paginatedResult = new PageImpl<>(getUserList().subList(0, 10));
		when(userRepository.findAll(any(Pageable.class))).thenReturn(paginatedResult);
		Page<UserResponse> users = userService.findAll(new PageRequest(1, 10));
		assertThat(users.getContent()).hasSize(10);
	}

	@Test
	@SuppressWarnings("ConstantConditions")
	public void findUserByRoleName() {
		String roleAdmin = Authority.ROLE_ADMIN.toString();
		when(userRepository.findByRoleName(roleAdmin)).thenReturn(getUserList().subList(0, 2));
		assertThat(userService.findUsersByRoleName(roleAdmin)).hasSize(2);
	}

	private List<User> getUserList() {
		String roleUser = Authority.ROLE_USER.toString();
		Role adminRole = role().thatIsAdministrator();
		RoleBuilder userRole = role().withAuthorization(roleUser);
		return Lists.newArrayList(
				user().withUsername("user1").withPassword("password1").withRole(adminRole).build(),
				user().withUsername("user2").withPassword("password2").withRole(adminRole).build(),
				user().withUsername("user3").withPassword("password3").withRole(userRole).build(),
				user().withUsername("user4").withPassword("password4").withRole(userRole).build(),
				user().withUsername("user5").withPassword("password5").withRole(userRole).build(),
				user().withUsername("user6").withPassword("password6").withRole(userRole).build(),
				user().withUsername("user7").withPassword("password7").withRole(userRole).build(),
				user().withUsername("user8").withPassword("password8").withRole(userRole).build(),
				user().withUsername("user9").withPassword("password9").withRole(userRole).build(),
				user().withUsername("user10").withPassword("password10").withRole(userRole).build(),
				user().withUsername("user11").withPassword("password11").withRole(userRole).build(),
				user().withUsername("user12").withPassword("password12").withRole(userRole).build(),
				user().withUsername("user13").withPassword("password13").withRole(userRole).build(),
				user().withUsername("user14").withPassword("password14").withRole(userRole).build(),
				user().withUsername("user15").withPassword("password15").withRole(userRole).build()
		);
	}

	@Test
	public void saveUser() {
		when(userRepository.save(any(User.class))).thenReturn(getUser());
		assertThat(userService.saveUser(new UserRequest()).getUsername()).isEqualTo("jhon");
	}

	private User getUser() {
		return user()
				.withId("1234567890")
				.withUsername("jhon")
				.withPassword("jhon78*")
				.withRole(role().thatIsAdministrator())
				.build();
	}

	@Test
	public void loadRoles() {
		String userAuthority = Authority.ROLE_USER.toString();
		RoleResponse response = new RoleResponse();
		response.setRoleName(userAuthority);
		List<Role> roles = Lists.newArrayList(
				role().thatIsAdministrator(),
				role().withAuthorization(userAuthority).build()
		);
		when(roleRepository.findAll()).thenReturn(roles);
		assertThat(userService.loadRoles()).hasSize(2).contains(response);
	}

	@Test
	public void findRoleByName() {
		String roleAdmin = Authority.ROLE_ADMIN.toString();
		when(roleRepository.findByName(roleAdmin)).thenReturn(role().thatIsAdministrator());
		RoleResponse response = userService.findRoleByName(roleAdmin);
		assertThat(response.getRoleName()).isEqualTo("ROLE_ADMIN");
	}

	@Test
	public void deleteUserByUserId() {
		ArgumentCaptor<String> argument = ArgumentCaptor.forClass(String.class);
		userService.deleteUser("1234567890");
		verify(userRepository).deleteRemovableUser(argument.capture());
		assertThat("1234567890").isEqualTo(argument.getValue());
	}

	@Test
	public void updateUser() {
		stubUpdateSuccess();
		boolean recordUpdated = userService.updateUser(new UserRequest());
		assertThat(recordUpdated).isTrue();
	}

	private void stubUpdateSuccess() {
		WriteResult writeResultMock = mock(WriteResult.class);
		CommandResult commandResult = mock(CommandResult.class);
		when(writeResultMock.getLastError()).thenReturn(commandResult);
		when(commandResult.ok()).thenReturn(true);
		when(mongoTemplate.updateFirst(
				any(Query.class),
				any(Update.class),
				any(Class.class))
		).thenReturn(writeResultMock);
	}

}
