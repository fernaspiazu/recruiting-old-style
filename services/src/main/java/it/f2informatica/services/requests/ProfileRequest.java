package it.f2informatica.services.requests;

import com.google.common.collect.Lists;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Data
@EqualsAndHashCode
@ToString
public class ProfileRequest {

	private List<ExperienceRequest> experiences = Lists.newArrayList();

	private List<EducationRequest> educationList = Lists.newArrayList();

	private List<LanguageRequest> languages = Lists.newArrayList();

	private List<String> skills = Lists.newArrayList();

	private String interests;

}
