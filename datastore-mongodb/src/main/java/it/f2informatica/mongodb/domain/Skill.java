package it.f2informatica.mongodb.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@Document
public class Skill {

	private String skillName;

	private int level;

}
