package it.f2informatica.mongodb.repositories.impl;

import com.mongodb.WriteResult;
import it.f2informatica.mongodb.domain.Consultant;
import it.f2informatica.mongodb.domain.Experience;
import it.f2informatica.mongodb.domain.Language;
import it.f2informatica.mongodb.domain.Profile;
import it.f2informatica.mongodb.repositories.custom.AdditionalConsultantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import static org.springframework.data.mongodb.core.query.Criteria.where;

public class ConsultantRepositoryImpl implements AdditionalConsultantRepository {
	private static final String ID = "id";
	private static final String PROFILE = "profile";
	private static final String EXPERIENCES = "profile.experiences";
	private static final String LANGUAGES = "profile.languages";
	private static final String SKILLS = "profile.skills";

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public Profile findProfileByConsultantId(String consultantId) {
		Query query = new Query(where(ID).is(consultantId));
		query.fields().include(PROFILE);
		Consultant consultant = mongoTemplate.findOne(query, Consultant.class);
		return (consultant != null) ? consultant.getProfile() : null;
	}

	@Override
	public boolean addExperience(Experience experience, String consultantId) {
		Query query = new Query(where(ID).is(consultantId));
		Update update = new Update().addToSet(EXPERIENCES, experience);
		return updateConsultant(query, update).getLastError().ok();
	}

	@Override
	public boolean addLanguage(Language language, String consultantId) {
		Query query = new Query(where(ID).is(consultantId));
		Update update = new Update().addToSet(LANGUAGES, language);
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

}
