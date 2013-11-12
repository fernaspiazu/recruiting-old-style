package it.f2informatica.mongodb.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Data
@EqualsAndHashCode
@ToString
public class Profile {

	private List<Experience> experiences;

	private List<Training> trainingList;

	private List<String> skills;

	private List<Language> languages;

	private String interests;

}
