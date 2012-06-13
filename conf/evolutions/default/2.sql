

# --- !Ups
CREATE SEQUENCE state_id_seq;
CREATE TABLE state (
       id integer PRIMARY KEY DEFAULT nextval('state_id_seq'),
       name varchar(22),
       code varchar(2)
);
 
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
       state_id integer references state(id),
       zip_code varchar(12),
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

insert into coop_type(coop_type) values ('PUBLIC');
insert into coop_type(coop_type) values ('PRIVATE');

insert into state(name, code) values('Alabama', 'AL');
insert into state(name, code) values('Alaska', 'AK');
insert into state(name, code) values('Arizona', 'AZ');
insert into state(name, code) values('Arkansas', 'AR');
insert into state(name, code) values('California', 'CA');
insert into state(name, code) values('Colorado', 'CO');
insert into state(name, code) values('Connecticut', 'CT');
insert into state(name, code) values('Delaware', 'DE');
insert into state(name, code) values('District of Columbia', 'DC');
insert into state(name, code) values('Florida', 'FL');
insert into state(name, code) values('Georgia', 'GA');
insert into state(name, code) values('Hawaii', 'HI');
insert into state(name, code) values('Idaho', 'ID');
insert into state(name, code) values('Illinois', 'IL');
insert into state(name, code) values('Indiana', 'IN');
insert into state(name, code) values('Iowa', 'IA');
insert into state(name, code) values('Kansas', 'KS');
insert into state(name, code) values('Kentucky', 'KY');
insert into state(name, code) values('Louisiana', 'LA');
insert into state(name, code) values('Maine', 'ME');
insert into state(name, code) values('Maryland', 'MD');
insert into state(name, code) values('Massachusetts', 'MA');
insert into state(name, code) values('Michigan', 'MI');
insert into state(name, code) values('Minnesota', 'MN');
insert into state(name, code) values('Mississippi', 'MS');
insert into state(name, code) values('Missouri', 'MO');
insert into state(name, code) values('Montana', 'MT');
insert into state(name, code) values('Nebraska', 'NE');
insert into state(name, code) values('Nevada', 'NV');
insert into state(name, code) values('New Hampshire', 'NH');
insert into state(name, code) values('New Jersey', 'NJ');
insert into state(name, code) values('New Mexico', 'NM');
insert into state(name, code) values('New York', 'NY');
insert into state(name, code) values('North Carolina', 'NC');
insert into state(name, code) values('North Dakota', 'ND');
insert into state(name, code) values('Ohio', 'OH');
insert into state(name, code) values('Oklahoma', 'OK');
insert into state(name, code) values('Oregon', 'OR');
insert into state(name, code) values('Pennsylvania', 'PA');
insert into state(name, code) values('Rhode Island', 'RI');
insert into state(name, code) values('South Carolina', 'SC');
insert into state(name, code) values('South Dakota', 'SD');
insert into state(name, code) values('Tennessee', 'TN');
insert into state(name, code) values('Texas', 'TX');
insert into state(name, code) values('Utah', 'UT');
insert into state(name, code) values('Vermont', 'VT');
insert into state(name, code) values('Virginia', 'XX');
insert into state(name, code) values('Washington', 'WA');
insert into state(name, code) values('West Virginia', 'WV');
insert into state(name, code) values('Wisconsin', 'WI');
insert into state(name, code) values('Wyoming', 'WY');


insert into coop(name, description, coop_type_id, manager_id,state_id,zip_code) values ('Global Coop', 'Global Coop Open to Anyone', 1, 1,1,'60622');

insert into coop_member(coop_id, joined_at, member_id, member_type_id, member_status_id) values (1, '2012-01-01', 1, 1, 1);


# --- !Downs
DROP TABLE coop;
DROP SEQUENCE coop_id_seq;
DROP TABLE coop_type;
DROP SEQUENCE coop_type_id_seq;

DROP TABLE state;
DROP SEQUENCE state_id_seq;

DROP TABLE coop_member;
DROP SEQUENCE coop_member_id_seq;

