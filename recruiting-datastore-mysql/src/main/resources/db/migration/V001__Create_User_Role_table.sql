
create table role (
  id          bigint not null auto_increment,
  name        varchar(20) not null,
  constraint pk_role primary key(id)
);

create table user (
  id              bigint not null auto_increment,
  username        varchar(20) not null,
  password        varchar(100) not null,
  firstname       varchar(50),
  lastname        varchar(50),
  email           varchar(50),
  role_id         bigint not null,
  constraint pk_user primary key (id),
  constraint fk_role foreign key (role_id) references role(id),
  index username_idx(username asc)
);

create table consultant (
  id              bigint not null auto_increment,
  registr_date    timestamp not null default now(),
  fiscal_code     varchar(16),
  email           varchar(50),
  first_name      varchar(50),
  last_name       varchar(50),
  gender          varchar(2),
  phone_number    varchar(20),
  mobile_number   varchar(20),
  birth_date      date,
  birth_city      varchar(20),
  birth_country   varchar(20),
  identity_card   varchar(20),
  interests       varchar(100),
  curriculum      longblob,
  constraint pk_consultant primary key (id),
  check (gender in ('M', 'F')),
  index cons_regdate_idx(registr_date desc),
  index cons_firstname_idx(first_name asc),
  index cons_lastname_idx(last_name asc),
  index cons_fullname_idx(first_name asc, last_name asc)
);

create table experience (
  id              bigint not null auto_increment,
  company         varchar(100) not null,
  job_position    varchar(100) not null,
  location        varchar(100),
  period_from     date not null,
  period_to       date,
  is_current      tinyint unsigned default 0,
  description     varchar(5000),
  consultant_id   bigint not null,
  constraint pk_experience primary key (id),
  constraint fk_prof_consul foreign key (consultant_id) references consultant(id)
);

create table education (
  id              bigint not null auto_increment,
  school          varchar(100) not null,
  start_year      integer not null,
  end_year        integer,
  is_current      tinyint unsigned default 0,
  school_degree   varchar(100),
  field_study     varchar(100),
  school_grade    varchar(100),
  activities      varchar(1000),
  description     varchar(5000),
  consultant_id   bigint not null,
  constraint pk_education primary key (id),
  constraint fk_educa_consul foreign key (consultant_id) references consultant(id)
);

create table languages (
  consultant_id   bigint not null,
  lang            varchar(20) not null,
  proficiency     varchar(30),
  constraint pk_skill primary key (consultant_id, lang),
  constraint fk_lang_consul foreign key (consultant_id) references consultant(id)
);

create table skills (
  consultant_id   bigint not null,
  skill           varchar(50) not null,
  constraint pk_skill primary key (consultant_id, skill),
  constraint fk_skill_consul foreign key (consultant_id) references consultant(id)
);

create table address (
  id              bigint not null auto_increment,
  street          varchar(100),
  house_no        varchar(10),
  zip_code        varchar(10),
  city            varchar(50),
  province        varchar(50),
  region          varchar(50),
  country         varchar(50),
  consultant_id   bigint not null,
  constraint pk_address primary key (id),
  constraint fk_address_consul foreign key (consultant_id) references consultant(id)
);

insert into role (name) values ('Administrator');
insert into role (name) values ('User');
insert into user (username, password, firstname, lastname, email, role_id)
  values ('admin', 'admin', null, null, 'admin@f2informatica.it', 1);