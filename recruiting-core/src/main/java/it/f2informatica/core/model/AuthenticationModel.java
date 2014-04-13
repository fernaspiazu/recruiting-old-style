package it.f2informatica.core.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

@Data
@EqualsAndHashCode
@ToString
public class AuthenticationModel implements Serializable {
  private static final long serialVersionUID = -6498031138520487799L;

  private String username;

  private String password;

  private String authorization;

}
