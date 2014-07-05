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
package it.f2informatica.core.model;

import com.google.common.collect.Lists;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@EqualsAndHashCode
@ToString
public class ConsultantModel implements Serializable {
	private static final long serialVersionUID = 4877550339644654489L;

	private String id;

	private String consultantNo;

	private Date registrationDate;

	private String fiscalCode;

	private String email;

	private String firstName;

	private String lastName;

	private String gender;

	private String phoneNumber;

	private String mobileNumber;

	private Date birthDate;

	private int age;

	private String birthCity;

	private String birthCountry;

	private String nationality;

	private String identityCardNo;

	private String passportNo;

	private List<ExperienceModel> experiences = Lists.newArrayList();

	private List<EducationModel> educationList = Lists.newArrayList();

	private List<LanguageModel> languages = Lists.newArrayList();

	private List<String> skills = Lists.newArrayList();

	private String interests;

	private AddressModel residence;

	private AddressModel domicile;

	private String curriculum; // TODO: GridFSFile

	private String photo; // TODO: GridFSFile

	private String submitEvent;

	public String getConsultantFullName() {
		return lastName + " " + firstName;
	}

}
