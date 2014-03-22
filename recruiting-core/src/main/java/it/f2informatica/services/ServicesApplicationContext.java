package it.f2informatica.services;

import com.google.common.collect.Sets;
import com.google.gson.*;

import it.f2informatica.mongodb.domain.Address;
import it.f2informatica.mongodb.domain.Consultant;
import it.f2informatica.mongodb.domain.Education;
import it.f2informatica.mongodb.domain.Experience;
import it.f2informatica.mongodb.domain.Language;
import it.f2informatica.mongodb.domain.User;
import it.f2informatica.services.gateway.EntityToModelConverter;
import it.f2informatica.services.gateway.converter.*;
import it.f2informatica.services.model.AddressModel;
import it.f2informatica.services.model.ConsultantModel;
import it.f2informatica.services.model.EducationModel;
import it.f2informatica.services.model.ExperienceModel;
import it.f2informatica.services.model.LanguageModel;
import it.f2informatica.services.model.UserModel;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ConversionServiceFactoryBean;

import java.lang.reflect.Type;
import java.util.Date;

@Configuration
@ComponentScan(basePackages = {
	"it.f2informatica.services"
})
public class ServicesApplicationContext {
	public static final String GLOBAL_DATE_FORMAT = "dd-MM-yyyy";

	@Bean @SuppressWarnings("unchecked")
	public ConversionServiceFactoryBean mongodbConversionService() {
		ConversionServiceFactoryBean conversionServiceFactoryBean = new ConversionServiceFactoryBean();
		conversionServiceFactoryBean.setConverters(Sets.newHashSet(
			mongoDBUserToModelConverter(),
			mongoDBConsultantToModelConverter(),
			mongoDBAddressToModelConverter(),
			mongoDBExperienceToModelConverter(),
			mongoDBLanguageToModelConverter(),
			mongoDBEducationToModelConverter()
		));
		return conversionServiceFactoryBean;
	}

	@Bean(name = "userToModelConverter", autowire = Autowire.BY_NAME)
	public EntityToModelConverter<User, UserModel> mongoDBUserToModelConverter() {
		return new MongoDBUserToUserModelConverter();
	}

	@Bean(name = "consultantToModelConverter", autowire = Autowire.BY_NAME)
	public EntityToModelConverter<Consultant, ConsultantModel> mongoDBConsultantToModelConverter() {
		return new MongoDBConsultantToConsultantModelConverter();
	}

	@Bean(name = "addressToModelConverter", autowire = Autowire.BY_NAME)
	public EntityToModelConverter<Address, AddressModel> mongoDBAddressToModelConverter() {
		return new MongoDBAddressToAddressModelConverter();
	}

	@Bean(name = "experienceToModelConverter", autowire = Autowire.BY_NAME)
	public EntityToModelConverter<Experience, ExperienceModel> mongoDBExperienceToModelConverter() {
		return new MongoDBExperienceToExperienceModel();
	}

	@Bean(name = "educationToModelConverter", autowire = Autowire.BY_NAME)
	public EntityToModelConverter<Education, EducationModel> mongoDBEducationToModelConverter() {
		return new MongoDBEducationToEducationModelConverter();
	}

	@Bean(name = "languageToModelConverter", autowire = Autowire.BY_NAME)
	public EntityToModelConverter<Language, LanguageModel> mongoDBLanguageToModelConverter() {
		return new MongoDBLanguageToLanguageModelConverter();
	}

	@Bean
	public Gson gson() {
		return new GsonBuilder()
			.setPrettyPrinting()
			.setDateFormat(GLOBAL_DATE_FORMAT)
			.serializeNulls()
			.serializeSpecialFloatingPointValues()
			.registerTypeAdapter(DateTime.class, new DateTimeTypeAdapter())
			.create();
	}

	private static class DateTimeTypeAdapter implements JsonSerializer<DateTime>, JsonDeserializer<DateTime> {
		@Override
		public JsonElement serialize(DateTime src, Type typeOfSrc, JsonSerializationContext context) {
			return new JsonPrimitive(src.toString(GLOBAL_DATE_FORMAT));
		}

		@Override
		public DateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
			try {
				return new DateTime(json.getAsString());
			} catch (IllegalArgumentException e) {
				Date date = context.deserialize(json, Date.class);
				return new DateTime(date);
			}
		}
	}

}
