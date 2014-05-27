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
package it.f2informatica.mysql.repositories;

import it.f2informatica.mysql.domain.Experience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;

public interface ExperienceRepository extends JpaRepository<Experience, Long> {

  @Modifying
  @Query("UPDATE Experience e SET " +
    "e.companyName = :companyName, " +
    "e.jobPosition = :jobPosition, " +
    "e.location = :location, " +
    "e.periodFrom = :periodFrom, " +
    "e.periodTo = :periodTo, " +
    "e.current = :current, " +
    "e.description = :description " +
    "WHERE e.id = :id")
  int updateExperience(@Param("id") Long id,
                       @Param("companyName") String companyName,
                       @Param("jobPosition") String jobPosition,
                       @Param("location") String location,
                       @Param("periodFrom") Date periodFrom,
                       @Param("periodTo") Date periodTo,
                       @Param("current") boolean current,
                       @Param("description") String description);

}
