

# --- !Ups
CREATE SEQUENCE coop_type_id_seq;
CREATE TABLE coop_type (
       id integer PRIMARY KEY DEFAULT nextval('coop_type_id_seq'),
       coop_type varchar(255)
);

CREATE SEQUENCE coop_id_seq;
CREATE TABLE coop (
       id integer PRIMARY KEY DEFAULT nextval('coop_id_seq'),
       name varchar(255),
       description varchar(1024),
       coop_type_id integer references coop_type(id),
       manager_id integer references member(id)
);

CREATE SEQUENCE coop_member_id_seq;

CREATE TABLE coop_member (
       id integer PRIMARY KEY DEFAULT nextval('coop_member_id_seq'),
       coop_id integer references coop(id),
       member_id integer references member(id)
);

# --- !Downs
DROP SEQUENCE coop_type_id_seq;
DROP TABLE coop_type;
DROP SEQUENCE coop_id_seq;
DROP SEQUENCE coop_member_id_seq;
DROP TABLE coop;
DROP TABLE coop_member;