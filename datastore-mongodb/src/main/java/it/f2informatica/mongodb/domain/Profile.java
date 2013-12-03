package it.f2informatica.mongodb.domain;

import com.google.common.collect.Lists;
import it.f2informatica.datastore.domain.MongoDBDocument;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Data
@EqualsAndHashCode
@ToString
public class Profile implements MongoDBDocument {

	private List<Experience> experiences = Lists.newArrayList();

	private List<Education> educationList = Lists.newArrayList();

	private List<Language> languages = Lists.newArrayList();

	private List<String> skills = Lists.newArrayList();

	private String interests;

}
