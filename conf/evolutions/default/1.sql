

# --- !Ups
CREATE SEQUENCE state_id_seq;
CREATE TABLE state (
       id integer PRIMARY KEY DEFAULT nextval('state_id_seq'),
       name varchar(22),
       code varchar(2)
);

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
       address varchar(255),
       state_id integer references state(id),
       zip_code varchar(12)
);


insert into member_type (member_type) values ('MANAGER');
insert into member_type (member_type) values ('REGULAR_MEMBER');
insert into member_status (member_status) values ('ACTIVE');
insert into member_status (member_status) values ('SUSPENDED');
insert into member(name, email, password, address, state_id, zip_code) values ('Supreme', 'supreme@aol.com', 'password', 'heaven',2,'60622'); 
insert into member(name, email, password, address,state_id,zip_code) values ('joshua', 'joshua@gmail.com', 'password', '1515 N Wicker',3,'60647'); 

# --- !Downs


DROP TABLE member_type;
DROP SEQUENCE member_type_id_seq;

DROP TABLE member_status;
DROP SEQUENCE member_status_id_seq;
DROP TABLE member;
DROP SEQUENCE member_id_seq;
DROP TABLE state;
DROP SEQUENCE state_id_seq;
