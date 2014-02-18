package it.f2informatica.services.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

@Data
@EqualsAndHashCode
@ToString
public class RoleModel implements Serializable {
	private static final long serialVersionUID = 637344076604577987L;

	private String roleId;

	private String roleName;

}
