package it.f2informatica.services.model;

import lombok.*;

import java.io.Serializable;

@Data
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class LanguageModel implements Serializable {
	private static final long serialVersionUID = 5650873740412761162L;

	private String language;

	private String proficiency;

}
