package it.f2informatica.mysql.repositories;

import it.f2informatica.mysql.domain.Role;
import it.f2informatica.mysql.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

  User findByUsername(String username);

  User findByUsernameAndPassword(String username, String password);

  Iterable<User> findByRoleName(String roleName);

  @Query("SELECT u FROM User u WHERE u.username NOT IN (:usernameToExclude, 'admin')")
  List<User> findAllExcludingCurrentUser(@Param("usernameToExclude") String usernameToExclude);

  @Query("SELECT u FROM User u WHERE u.id = :userId AND u.password = :password")
  User findByIdAndPassword(@Param("userId") Long userId, @Param("password") String password);

  @Modifying
  @Query("UPDATE User u SET u.password = :newPwd WHERE u.id = :userId AND u.password = :currentPwd")
  int updatePassword(@Param("userId") Long userId,
                     @Param("currentPwd") String currentPwd,
                     @Param("newPwd") String newPwd);

  @Modifying
  @Query("UPDATE User u SET " +
    "u.username = :username, " +
    "u.firstName = :firstName, " +
    "u.lastName = :lastName, " +
    "u.email = :email, " +
    "u.role = :role " +
    "WHERE u.id = :userId")
  int updateUser(@Param("userId") Long userId,
                 @Param("username") String username,
                 @Param("firstName") String firstName,
                 @Param("lastName") String lastName,
                 @Param("email") String email,
                 @Param("role") Role role);

}
