package it.f2informatica.mongodb.repositories.impl;

import it.f2informatica.mongodb.domain.Consultant;
import it.f2informatica.mongodb.domain.Experience;
import it.f2informatica.mongodb.domain.Profile;
import it.f2informatica.mongodb.repositories.customrepositories.AdditionalConsultantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

public class ConsultantRepositoryImpl implements AdditionalConsultantRepository {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public Profile findProfileByConsultantId(String consultantId) {
		Query query = Query.query(Criteria.where("id").is(consultantId));
		query.fields().include("profile");
		Consultant consultant = mongoTemplate.findOne(query, Consultant.class);
		return (consultant != null) ? consultant.getProfile() : null;
	}

	@Override
	public boolean addExperience(Experience experience, String consultantId) {
		Query query = Query.query(Criteria.where("id").is(consultantId));
		Update update = new Update().addToSet("profile.experiences", experience);
		return mongoTemplate.updateFirst(query, update, Consultant.class).getLastError().ok();
	}

}
