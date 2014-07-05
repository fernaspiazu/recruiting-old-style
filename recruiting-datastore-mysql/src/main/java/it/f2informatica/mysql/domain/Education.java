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
package it.f2informatica.mysql.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Data
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "education")
public class Education implements Serializable {
	private static final long serialVersionUID = 5816729224700502683L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "school")
	private String schoolName;

	@Column(name = "start_year")
	private int startYear;

	@Column(name = "end_year")
	private int endYear;

	@Column(name = "is_current")
	private boolean current;

	@Column(name = "school_degree")
	private String schoolDegree;

	@Column(name = "field_study")
	private String fieldsOfStudy;

	@Column(name = "school_grade")
	private String grade;

	@Column(name = "activities")
	private String activities;

	@Column(name = "description")
	private String description;

	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Consultant.class)
	@JoinColumn(name = "consultant_id")
	private Consultant consultant;
}
