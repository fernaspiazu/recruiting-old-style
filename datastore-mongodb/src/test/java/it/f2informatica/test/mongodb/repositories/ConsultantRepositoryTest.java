//package it.f2informatica.test.mongodb.repositories;
//
//import com.mongodb.BasicDBObject;
//import com.mongodb.DBCursor;
//import com.mongodb.DBObject;
//import it.f2informatica.datastore.constant.LanguageProficiency;
//import it.f2informatica.mongodb.domain.Consultant;
//import it.f2informatica.mongodb.repositories.ConsultantRepository;
//import it.f2informatica.test.mongodb.DatastoreUtils;
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.mongodb.core.query.Query;
//
//import java.util.GregorianCalendar;
//
//import static it.f2informatica.test.mongodb.builders.ConsultantDataBuilder.consultant;
//import static it.f2informatica.test.mongodb.builders.ExperienceDataBuilder.experience;
//import static it.f2informatica.test.mongodb.builders.LanguageDataBuilder.italian;
//import static it.f2informatica.test.mongodb.builders.LanguageDataBuilder.spanish;
//import static it.f2informatica.test.mongodb.builders.ProfileDataBuilder.profile;
//import static org.springframework.data.mongodb.core.query.Criteria.where;
//
//public class ConsultantRepositoryTest extends DatastoreUtils {
//
//	@Autowired
//	private ConsultantRepository consultantRepository;
//
//	@Test
//	public void findLimitedExperiencesTest() {
//		Consultant consultant = consultant()
//				.withConsultantNo("12345678901234567890")
//				.withLastName("Aspiazu")
//				.withFirstName("Fernando")
//				.withFiscalCode("SPZFNN87D17Z605K")
//				.withProfile(profile()
//						.withExperienceIn(experience()
//								.fromPeriod(new GregorianCalendar(2011, 1, 1).getTime())
//								.toPeriod(new GregorianCalendar(2011, 11, 1).getTime())
//								.inCompany("F2 Informatica").inFunctionOf("Developer").locatedAt("Corsico"))
//						.withExperienceIn(experience()
//								.fromPeriod(new GregorianCalendar(2012, 1, 1).getTime())
//								.toPeriod(new GregorianCalendar(2012, 11, 1).getTime())
//								.inCompany("Athirat").inFunctionOf("Developer").locatedAt("Milano"))
//						.withExperienceIn(experience()
//								.fromPeriod(new GregorianCalendar(2013, 1, 1).getTime())
//								.toPeriod(new GregorianCalendar(2013, 11, 1).getTime())
//								.inCompany("Sopra Group").inFunctionOf("Developer").locatedAt("Assago"))
//						.withExperienceIn(experience()
//								.fromPeriod(new GregorianCalendar(2014, 1, 1).getTime())
//								.thisIsTheCurrentJob()
//								.inCompany("RedHat").inFunctionOf("Developer").locatedAt("Milano"))
//						.speaking(spanish().withProficiency(LanguageProficiency.NATIVE_OR_BILINGUAL))
//						.speaking(italian().withProficiency(LanguageProficiency.FULL_PROFESSIONAL))
//				).build();
//		consultantRepository.save(consultant);
//		DBObject queryObject = new Query(where("id").is("12345678901234567890")).limit(3).getQueryObject();
//		DBCursor cursor = mongoTemplateTest.getCollection("consultant")
//				.find(queryObject, new BasicDBObject("profile.experiences", true).append("_id", false));
//
//		try {
//			while (cursor.hasNext()) {
//				DBObject obj = cursor.next();
//				System.out.println(obj);
//			}
//		} finally {
//			cursor.close();
//		}
//	}
//
//}
