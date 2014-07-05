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
package it.f2informatica.mongodb.domain;

import com.google.common.collect.Lists;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Document
public class Consultant extends Identifiable<String> {
	private static final long serialVersionUID = 6643483509995605407L;

	@Indexed(unique = true)
	private String consultantNo;

	@Indexed(direction = IndexDirection.DESCENDING)
	private Date registrationDate;

	private String fiscalCode;

	private String email;

	@Indexed(direction = IndexDirection.ASCENDING)
	private String firstName;

	@Indexed(direction = IndexDirection.ASCENDING)
	private String lastName;

	private String gender;

	private String phoneNumber;

	private String mobileNumber;

	private Date birthDate;

	private String birthCity;

	private String birthCountry;

	private String identityCardNo;

	private String passportNo;

	private String maritalStatus;

	private List<Experience> experiences = Lists.newArrayList();

	private List<Education> educationList = Lists.newArrayList();

	private List<Language> languages = Lists.newArrayList();

	private List<String> skills = Lists.newArrayList();

	private String interests;

	private Address residence;

	private Address domicile;

	private String curriculum; //TODO: GridFSFile

	private String photo; //TODO: GridFSFile

}
