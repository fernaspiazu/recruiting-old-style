/*
 * =============================================================================
 *
 *   Copyright (c) 2014, Fernando Aspiazu
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 * =============================================================================
 */
package it.f2informatica.pagination;

import com.google.gson.*;
import org.joda.time.DateTime;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Type;
import java.util.Date;

@Configuration
@ComponentScan(basePackages = "it.f2informatica.pagination")
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
