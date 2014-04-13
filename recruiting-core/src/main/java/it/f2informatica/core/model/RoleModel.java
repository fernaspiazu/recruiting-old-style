package it.f2informatica.core.model;

import lombok.*;

import java.io.Serializable;

@Data
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RoleModel implements Serializable {
	private static final long serialVersionUID = 637344076604577987L;

	private String roleId;

	private String roleName;

}
