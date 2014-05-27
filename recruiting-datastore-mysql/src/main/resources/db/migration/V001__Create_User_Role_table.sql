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
CREATE TABLE role (
  id   BIGINT      NOT NULL AUTO_INCREMENT,
  name VARCHAR(20) NOT NULL,
  CONSTRAINT pk_role PRIMARY KEY (id)
);

CREATE TABLE user (
  id        BIGINT       NOT NULL AUTO_INCREMENT,
  username  VARCHAR(20)  NOT NULL,
  password  VARCHAR(100) NOT NULL,
  firstname VARCHAR(50),
  lastname  VARCHAR(50),
  email     VARCHAR(50),
  role_id   BIGINT       NOT NULL,
  CONSTRAINT pk_user PRIMARY KEY (id),
  CONSTRAINT fk_role FOREIGN KEY (role_id) REFERENCES role (id),
  INDEX username_idx(username ASC)
);

CREATE TABLE address (
  id       BIGINT NOT NULL AUTO_INCREMENT,
  street   VARCHAR(100),
  house_no VARCHAR(10),
  zip_code VARCHAR(10),
  city     VARCHAR(50),
  province VARCHAR(50),
  region   VARCHAR(50),
  country  VARCHAR(50),
  CONSTRAINT pk_address PRIMARY KEY (id)
);

CREATE TABLE consultant (
  id            BIGINT      NOT NULL AUTO_INCREMENT,
  consultant_no VARCHAR(50) NOT NULL UNIQUE,
  registr_date  TIMESTAMP   NOT NULL DEFAULT now(),
  fiscal_code   VARCHAR(16),
  email         VARCHAR(50),
  first_name    VARCHAR(50),
  last_name     VARCHAR(50),
  gender        VARCHAR(10),
  phone_number  VARCHAR(20),
  mobile_number VARCHAR(20),
  birth_date    DATE,
  birth_city    VARCHAR(20),
  birth_country VARCHAR(20),
  identity_card VARCHAR(20),
  interests     VARCHAR(100),
  residence     BIGINT,
  domicile      BIGINT,
  curriculum    LONGBLOB,
  CONSTRAINT pk_consultant PRIMARY KEY (id),
  CONSTRAINT fk_cons_resid FOREIGN KEY (residence) REFERENCES address (id),
  CONSTRAINT fk_cons_domic FOREIGN KEY (domicile) REFERENCES address (id),
  CHECK (gender IN ('M', 'F')),
  INDEX cons_regdate_idx(registr_date DESC),
  INDEX cons_firstname_idx(first_name ASC),
  INDEX cons_lastname_idx(last_name ASC),
  INDEX cons_fullname_idx(first_name ASC, last_name ASC)
);

CREATE TABLE experience (
  id            BIGINT       NOT NULL AUTO_INCREMENT,
  company       VARCHAR(100) NOT NULL,
  job_position  VARCHAR(100) NOT NULL,
  location      VARCHAR(100),
  period_from   DATE         NOT NULL,
  period_to     DATE,
  is_current    TINYINT UNSIGNED DEFAULT 0,
  description   VARCHAR(5000),
  consultant_id BIGINT       NOT NULL,
  CONSTRAINT pk_experience PRIMARY KEY (id),
  CONSTRAINT fk_prof_consul FOREIGN KEY (consultant_id) REFERENCES consultant (id)
);

CREATE TABLE education (
  id            BIGINT       NOT NULL AUTO_INCREMENT,
  school        VARCHAR(100) NOT NULL,
  start_year    INTEGER      NOT NULL,
  end_year      INTEGER,
  is_current    TINYINT UNSIGNED DEFAULT 0,
  school_degree VARCHAR(100),
  field_study   VARCHAR(100),
  school_grade  VARCHAR(100),
  activities    VARCHAR(1000),
  description   VARCHAR(5000),
  consultant_id BIGINT       NOT NULL,
  CONSTRAINT pk_education PRIMARY KEY (id),
  CONSTRAINT fk_educa_consul FOREIGN KEY (consultant_id) REFERENCES consultant (id)
);

CREATE TABLE languages (
  consultant_id BIGINT      NOT NULL,
  lang          VARCHAR(20) NOT NULL,
  proficiency   VARCHAR(30),
  CONSTRAINT pk_skill PRIMARY KEY (consultant_id, lang),
  CONSTRAINT fk_lang_consul FOREIGN KEY (consultant_id) REFERENCES consultant (id)
);

CREATE TABLE skills (
  consultant_id BIGINT      NOT NULL,
  skill         VARCHAR(50) NOT NULL,
  CONSTRAINT pk_skill PRIMARY KEY (consultant_id, skill),
  CONSTRAINT fk_skill_consul FOREIGN KEY (consultant_id) REFERENCES consultant (id)
);

INSERT INTO role (name) VALUES ('Administrator');
INSERT INTO role (name) VALUES ('User');
INSERT INTO user (username, password, firstname, lastname, email, role_id)
VALUES ('admin', 'admin', NULL, NULL, 'admin@f2informatica.it', 1);