package it.f2informatica.pagination;

import com.google.gson.*;
import org.joda.time.DateTime;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Type;
import java.util.Date;

@Configuration
public class PaginationConfig {

  @Bean
  public Gson gson() {
    return new GsonBuilder()
      .serializeNulls()
      .setPrettyPrinting()
      .setDateFormat(DatePatterns.GLOBAL_DATE_FORMAT)
      .serializeSpecialFloatingPointValues()
      .registerTypeAdapter(DateTime.class, new DateTimeTypeAdapter())
      .create();
  }

  private static class DateTimeTypeAdapter implements JsonSerializer<DateTime>, JsonDeserializer<DateTime> {

    @Override
    public JsonElement serialize(DateTime src, Type typeOfSrc, JsonSerializationContext context) {
      return new JsonPrimitive(src.toString(DatePatterns.GLOBAL_DATE_FORMAT));
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
