

# --- !Ups
CREATE SEQUENCE coop_type_id_seq;
CREATE TABLE coop_type (
       id integer PRIMARY KEY DEFAULT nextval('coop_type_id_seq'),
       coop_type varchar(128)
);

CREATE SEQUENCE coop_id_seq;
CREATE TABLE coop (
       id integer PRIMARY KEY DEFAULT nextval('coop_id_seq'),
       name varchar(128),
       description varchar(1024),
       created_at timestamp default now(),
       coop_type_id integer references coop_type(id),
       manager_id integer references member(id)
);

CREATE SEQUENCE coop_member_id_seq;

CREATE TABLE coop_member (
       id integer PRIMARY KEY DEFAULT nextval('coop_member_id_seq'),
       coop_id integer references coop(id),
       joined_at timestamp default now(),
       member_id integer references member(id),
       member_type_id integer references member_type(id),
       member_status_id integer references member_status(id)
);

insert into coop_type(id, coop_type) values (0, 'PUBLIC');
insert into coop_type(id, coop_type) values (1, 'PRIVATE');

insert into coop(id, name, description, coop_type_id, manager_id) values (0, 'Global Coop', 'Global Coop Open to Anyone', 0, 0);


# --- !Downs
DROP TABLE coop;
DROP SEQUENCE coop_id_seq;
DROP TABLE coop_type;
DROP SEQUENCE coop_type_id_seq;
DROP TABLE coop_member;
DROP SEQUENCE coop_member_id_seq;

