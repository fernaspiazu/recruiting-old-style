package it.f2informatica.services.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

@Data
@EqualsAndHashCode
@ToString
public class LanguageModel implements Serializable {
	private static final long serialVersionUID = 5650873740412761162L;

	private String language;

	private String proficiency;

}
