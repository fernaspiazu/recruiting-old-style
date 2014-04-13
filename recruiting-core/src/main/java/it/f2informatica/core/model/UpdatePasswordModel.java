package it.f2informatica.core.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

@Data
@EqualsAndHashCode
@ToString
public class UpdatePasswordModel implements Serializable {
  private static final long serialVersionUID = 8031360673716450792L;

  private String userId;

  private String currentPassword;

  private String newPassword;

  private String passwordConfirmed;

}
