package it.f2informatica.services;

import com.google.gson.*;
import org.joda.time.DateTime;
import org.springframework.context.annotation.*;

import java.lang.reflect.Type;
import java.util.Date;

@Configuration
@ComponentScan(
  basePackages = {"it.f2informatica.services"},
  excludeFilters = {
    @ComponentScan.Filter(type = FilterType.REGEX ,pattern = "it.f2informatica.services.gateway.mongodb.*"),
    @ComponentScan.Filter(type = FilterType.REGEX ,pattern = "it.f2informatica.services.gateway.mysql.*")
  }
)
@Import({MongoDBConversionServicesContext.class, MySQLConversionServicesContext.class})
public class ServicesApplicationContext {
	public static final String GLOBAL_DATE_FORMAT = "dd-MM-yyyy";

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
