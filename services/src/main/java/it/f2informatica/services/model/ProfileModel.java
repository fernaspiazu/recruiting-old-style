package it.f2informatica.services.model;

import com.google.common.collect.Lists;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Data
@EqualsAndHashCode
@ToString
public class ProfileModel {

	private List<ExperienceModel> experiences = Lists.newArrayList();

	private List<EducationModel> educationList = Lists.newArrayList();

	private List<LanguageModel> languages = Lists.newArrayList();

	private List<String> skills = Lists.newArrayList();

	private String interests;

}
