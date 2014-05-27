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
package it.f2informatica.mongodb.domain.builder;

import it.f2informatica.mongodb.domain.Language;

public class LanguageBuilder {

  private Language lang = new Language();

  private LanguageBuilder(String language) {
    this.lang.setLanguage(language);
  }

  public static LanguageBuilder english() {
    return new LanguageBuilder("English");
  }

  public static LanguageBuilder italian() {
    return new LanguageBuilder("Italian");
  }

  public static LanguageBuilder spanish() {
    return new LanguageBuilder("Spanish");
  }

  public static LanguageBuilder french() {
    return new LanguageBuilder("French");
  }

  public static LanguageBuilder language(String language) {
    return new LanguageBuilder(language);
  }

  public LanguageBuilder withProficiency(String proficiency) {
    lang.setProficiency(proficiency);
    return this;
  }

  public Language build() {
    return lang;
  }

}
