package it.f2informatica.mongodb.repositories.impl;

import com.mongodb.WriteResult;
import it.f2informatica.mongodb.domain.Consultant;
import it.f2informatica.mongodb.domain.Experience;
import it.f2informatica.mongodb.domain.Language;
import it.f2informatica.mongodb.repositories.custom.CustomConsultantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.Fields;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.TypedAggregation.*;
import static org.springframework.data.mongodb.core.query.Criteria.where;

public class ConsultantRepositoryImpl implements CustomConsultantRepository {
	private static final String ID = "id";
	private static final String EXPERIENCES = "experiences";
	private static final String LANGUAGES = "languages";
	private static final String SKILLS = "skills";

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public boolean addExperience(Experience experience, String consultantId) {
		Query query = new Query(where(ID).is(consultantId));
		Update update = new Update().addToSet(EXPERIENCES, experience);
		return updateConsultant(query, update).getLastError().ok();
	}

	@Override
	public boolean updateExperience(Experience experience, String consultantId) {
		Query query = new Query(where(ID).is(consultantId)
			.and(EXPERIENCES + "." + Fields.UNDERSCORE_ID).is(experience.getId()));
		Update update = new Update().set(EXPERIENCES + ".$", experience);
		return updateConsultant(query, update).getLastError().ok();
	}

	@Override
	public boolean removeExperience(String consultantId, String experienceId) {
		Query query = new Query(where(ID).is(consultantId)
			.and(EXPERIENCES + "." + Fields.UNDERSCORE_ID).is(experienceId));
		Update update = new Update().pull(EXPERIENCES, findExperience(consultantId, experienceId));
		return updateConsultant(query, update).getLastError().ok();
	}

	@Override
	public boolean addLanguage(Language language, String consultantId) {
		Query query = new Query(where(ID).is(consultantId));
		Update update = new Update().addToSet(LANGUAGES, language);
		return updateConsultant(query, update).getLastError().ok();
	}

	@Override
	public boolean addLanguages(List<Language> languages, String consultantId) {
		Query query = new Query(where(ID).is(consultantId));
		Update update = new Update().set(LANGUAGES, languages);
		return updateConsultant(query, update).getLastError().ok();
	}

	@Override
	public boolean addSkills(String[] skills, String consultantId) {
		Query query = new Query(where(ID).is(consultantId));
		Update update = new Update().set(SKILLS, skills);
		return updateConsultant(query, update).getLastError().ok();
	}

	private WriteResult updateConsultant(Query query, Update update) {
		return mongoTemplate.updateFirst(query, update, Consultant.class);
	}

	@Override
	public Experience findExperience(String consultantId, String experienceId) {
		Aggregation aggregation = newAggregation(
				match(where("id").is(consultantId)),
				group("experiences"),
				unwind(previousOperation()),
				match(where(previousOperation() + "." + Fields.UNDERSCORE_ID).is(experienceId))
		);
		AggregationResults<Experience> aggregationResults = mongoTemplate.aggregate(aggregation, Consultant.class, Experience.class);
		return aggregationResults.getUniqueMappedResult();
	}

}
