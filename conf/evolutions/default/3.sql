# item and item_order
# --- !Ups

create SEQUENCE vendor_id_seq;

CREATE TABLE vendor (
       id integer PRIMARY KEY DEFAULT nextval('vendor_id_seq'),
       name varchar(128),
       address varchar(256),
       state_id integer references state(id),
       zip_code varchar(16),
       url varchar (64),
       created_by_id integer references member(id)
);

CREATE SEQUENCE item_type_id_seq;

CREATE TABLE item_type (
       id integer PRIMARY KEY DEFAULT nextval('item_type_id_seq'),
       item_type varchar(128)
);

CREATE SEQUENCE item_id_seq;

CREATE TABLE item (
       id integer PRIMARY KEY DEFAULT nextval('item_id_seq'),
       name varchar(64),
       description varchar(128),
       item_type_id integer references item_type(id),
       cost numeric(8,2),
       url varchar(128),
       vendor_id integer references vendor(id),
       created_by_id integer references member(id)
);

insert into item_type(item_type) values ('BEEF');

insert into vendor(name, address, zip_code, url, created_by_id,state_id) values ('barrington natural farms', '7 Crawling Stone Rd, Barrington Hills, IL 60010', '60010', 'http://www.barrington-natural-farms.com/our-products/',1,1);
  
insert into item(name, vendor_id, description, item_type_id, cost, url,created_by_id) values ('100% Grass-Fed Black Angus Beef', 1, '45-55 lbs at 7.95 each.  No Hormones, Antibiotics, Corn, or Grains', 1, 440, 'http://www.barrington-natural-farms.com/our-products/', 1);
insert into item(name, vendor_id, description, item_type_id, cost, url,created_by_id) values ('50 lbs Freezer Pack-All Ground', 1, '50 lbs at 5.95 per lb.  No Hormones, Antibiotics, Corn, or Grains', 1, 300, 'http://www.barrington-natural-farms.com/our-products/', 1);

CREATE SEQUENCE itemorder_id_seq;
CREATE TABLE itemorder (
       id integer PRIMARY KEY DEFAULT nextval('itemorder_id_seq'),
       item_id integer references item(id),
       minimumbuyers integer,
       membercost numeric(8,2),
       description varchar(128),
       deadline_by timestamp,
       deliveryaddress varchar(128),
       created_at timestamp default now(),
       created_by_id integer references member(id),
       coop_id integer references coop(id)
);  
CREATE SEQUENCE itemorder_member_id_seq;
create table itemorder_member (
       id integer PRIMARY KEY DEFAULT nextval('itemorder_member_id_seq'),
       member_id integer references member(id),
       itemorder_id integer references itemorder(id)
       );

insert into itemorder(item_id, minimumbuyers, membercost, description, deadline_by, deliveryaddress, created_at, created_by_id, coop_id) values (1, 11, 40, '5 lb each', '2012-09-01', '1515 N Wicker', now(), 1, 1);


# --- !Downs


drop table itemorder_member;
DROP TABLE itemorder;
DROP TABLE item;
DROP TABLE item_type;
DROP TABLE vendor;
DROP SEQUENCE itemorder_id_seq;
DROP SEQUENCE itemorder_member_id_seq;
DROP SEQUENCE item_id_seq;
DROP SEQUENCE item_type_id_seq;
DROP SEQUENCE vendor_id_seq;
