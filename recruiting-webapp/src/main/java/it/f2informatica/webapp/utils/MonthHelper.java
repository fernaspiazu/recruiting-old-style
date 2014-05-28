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
package it.f2informatica.webapp.utils;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MonthHelper {

  @Autowired
  private MessageSource messageSource;

  @Autowired
  private CurrentHttpRequest httpRequest;

  public List<Month> getMonths() {
    return Lists.newArrayList(
      new Month("0", getMessage("month.january")),
      new Month("1", getMessage("month.february")),
      new Month("2", getMessage("month.march")),
      new Month("3", getMessage("month.april")),
      new Month("4", getMessage("month.may")),
      new Month("5", getMessage("month.june")),
      new Month("6", getMessage("month.july")),
      new Month("7", getMessage("month.august")),
      new Month("8", getMessage("month.september")),
      new Month("9", getMessage("month.october")),
      new Month("10", getMessage("month.november")),
      new Month("11", getMessage("month.december"))
    );
  }

  private String getMessage(String label) {
    return messageSource.getMessage(label, null, httpRequest.getLocale());
  }

}
