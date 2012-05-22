

# --- !Ups

CREATE SEQUENCE member_type_id_seq;

create TABLE member_type (
       id integer PRIMARY KEY DEFAULT nextval('member_type_id_seq'),
       member_type varchar(64)
       );

insert into member_type (member_type) values ('MANAGER');

CREATE SEQUENCE member_id_seq;
CREATE TABLE member (
       id integer PRIMARY KEY DEFAULT nextval('member_id_seq'),
       name varchar(255),
       email varchar(255),
       member_type_id integer references member_type(id)
);

# --- !Downs


DROP TABLE member_type;
DROP SEQUENCE member_type_id_seq;

DROP TABLE member;
DROP SEQUENCE member_id_seq;