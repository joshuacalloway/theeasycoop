

# --- !Ups

CREATE SEQUENCE member_type_id_seq;

create TABLE member_type (
       id integer PRIMARY KEY DEFAULT nextval('member_type_id_seq'),
       member_type varchar(64)
       );

create SEQUENCE member_status_id_seq;
CREATE TABLE member_status (
       id integer PRIMARY KEY DEFAULT nextval('member_status_id_seq'),
       member_status varchar(255)
);

CREATE SEQUENCE member_id_seq;
CREATE TABLE member (
       id integer PRIMARY KEY DEFAULT nextval('member_id_seq'),
       name varchar(64) not null,
       email varchar(64) not null,
       password varchar(64),
       cell varchar(16),
       address varchar(255)
);


insert into member_type (member_type) values ('MANAGER');
insert into member_type (member_type) values ('REGULAR_MEMBER');
insert into member_status (member_status) values ('ACTIVE');
insert into member_status (member_status) values ('SUSPENDED');
insert into member(id, name, email, password, address) values (0, 'Supreme', 'supreme@aol.com', 'password', 'heaven'); 
insert into member(id, name, email, password, address) values (1, 'joshua', 'joshua@gmail.com', 'password', '1515 N Wicker'); 

# --- !Downs


DROP TABLE member_type;
DROP SEQUENCE member_type_id_seq;

DROP TABLE member_status;
DROP SEQUENCE member_status_id_seq;
DROP TABLE member;
DROP SEQUENCE member_id_seq;