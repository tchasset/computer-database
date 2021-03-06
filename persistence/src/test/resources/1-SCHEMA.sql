drop schema if exists `computer-database-db`;
create schema if not exists `computer-database-db`;
use `computer-database-db`;

drop table if exists computer;
drop table if exists company;
drop table if exists users;
drop table if exists authorities;

create table users (
	username varchar(32) not null,
    	password varchar(255) not null,
    	enabled TINYINT NOT NULL DEFAULT 1,
    	constraint pk_users primary key (username))
;
  
CREATE TABLE authorities (
	username VARCHAR(50) NOT NULL,
	authority VARCHAR(50) NOT NULL)
;

create table company (
    	id                        bigint not null auto_increment,
   	name                      varchar(255),
    	constraint pk_company primary key (id))
;

create table computer (
   	id                        bigint not null auto_increment,
   	name                      varchar(255),
   	introduced                timestamp NULL,
    	discontinued              timestamp NULL,
    	company_id                bigint default NULL,
    	constraint pk_computer primary key (id))
;


CREATE UNIQUE INDEX ix_auth_username  on authorities (username,authority);
alter table authorities add constraint fk_users_authorities_1 foreign key (username) references users (username) on delete restrict on update restrict;
alter table computer add constraint fk_computer_company_1 foreign key (company_id) references company (id) on delete restrict on update restrict;
create index ix_computer_company_1 on computer (company_id);
