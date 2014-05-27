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
package it.f2informatica.core.model.builder;

import it.f2informatica.core.model.LanguageModel;

public class LanguageModelBuilder {

  private LanguageModel lang = new LanguageModel();

  private LanguageModelBuilder(String language) {
    this.lang.setLanguage(language);
  }

  public static LanguageModelBuilder english() {
    return new LanguageModelBuilder("English");
  }

  public static LanguageModelBuilder italian() {
    return new LanguageModelBuilder("Italian");
  }

  public static LanguageModelBuilder spanish() {
    return new LanguageModelBuilder("Spanish");
  }

  public static LanguageModelBuilder french() {
    return new LanguageModelBuilder("French");
  }

  public static LanguageModelBuilder languageModel() {
    return new LanguageModelBuilder(null);
  }

  public static LanguageModelBuilder languageModel(String language) {
    return new LanguageModelBuilder(language);
  }

  public LanguageModelBuilder withProficiency(String proficiency) {
    lang.setProficiency(proficiency);
    return this;
  }

  public LanguageModel build() {
    return lang;
  }

}
