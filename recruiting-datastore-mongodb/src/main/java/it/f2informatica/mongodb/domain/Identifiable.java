package it.f2informatica.mongodb.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

@Data
@EqualsAndHashCode
public abstract class Identifiable<ID extends Serializable> implements Serializable {

	@Id
	protected ID id;

}
