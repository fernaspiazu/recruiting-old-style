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
import java.util.Date;

@Data
@EqualsAndHashCode
@ToString(exclude = "consultant")
@Entity
@Table(name = "experience")
public class Experience implements Serializable {
	private static final long serialVersionUID = -3499814695049172754L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "company")
	private String companyName;

	@Column(name = "job_position")
	private String jobPosition;

	@Column(name = "location")
	private String location;

	@Temporal(TemporalType.DATE)
	@Column(name = "period_from")
	private Date periodFrom;

	@Temporal(TemporalType.DATE)
	@Column(name = "period_to")
	private Date periodTo;

	@Column(name = "is_current")
	private boolean current;

	@Column(name = "description")
	private String description;

	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Consultant.class)
	@JoinColumn(name = "consultant_id")
	private Consultant consultant;
}
