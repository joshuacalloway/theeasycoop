# item and item_order
# --- !Ups

CREATE SEQUENCE item_id_seq;

CREATE TABLE item (
       id integer PRIMARY KEY DEFAULT nextval('item_id_seq'),
       name varchar(64),
       description varchar(128),
       cost numeric(8,2),
       url varchar(128),
       created_by_id integer references member(id)
);

insert into item(id, name, description, cost, url,created_by_id) values (1, '100% Grass-Fed Black Angus Beef', '45-55 lbs at 7.95 each.  No Hormones, Antibiotics, Corn, or Grains', 440, 'http://www.barrington-natural-farms.com/our-products/', 0);
insert into item(id, name, description, cost, url,created_by_id) values (2, '50 lbs Freezer Pack-All Ground', '50 lbs at 5.95 per lb.  No Hormones, Antibiotics, Corn, or Grains', 300, 'http://www.barrington-natural-farms.com/our-products/', 0);

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

insert into itemorder(id, item_id, minimumbuyers, membercost, description, deadline_by, deliveryaddress, created_at, created_by_id, coop_id) values (1, 1, 11, 40, '5 lb each', '2012-09-01', '1515 N Wicker', now(), 0, 0);


# --- !Downs


drop table itemorder_member;
DROP TABLE itemorder;
DROP TABLE item;

DROP SEQUENCE itemorder_id_seq;
DROP SEQUENCE itemorder_member_id_seq;
DROP SEQUENCE item_id_seq;
