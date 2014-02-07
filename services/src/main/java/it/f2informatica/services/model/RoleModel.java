package it.f2informatica.services.model;

import it.f2informatica.datastore.model.DataModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode
@ToString
public class RoleModel implements DataModel {
	private static final long serialVersionUID = 637344076604577987L;

	private String roleId;

	private String roleName;

}
