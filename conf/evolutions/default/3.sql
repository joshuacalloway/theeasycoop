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

insert into item(id, name, description, cost, url,created_by_id) values (1, 'Fresh Salmon Sushi Grade', '10 lbs the very best', 300, 'salmon.com', 0);
insert into item(id, name, description, cost, url,created_by_id) values (2, 'NAPA valley Wine', '10 cases of red wine', 500, 'napawine.com', 0);

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

insert into itemorder(id, item_id, minimumbuyers, membercost, description, deadline_by, deliveryaddress, created_at, created_by_id, coop_id) values (1, 1, 10, 30.10, '1 lb of salmon each', '2012-09-01', '1515 N Wicker', now(), 0, 0);


# --- !Downs


drop table itemorder_member;
DROP TABLE itemorder;
DROP TABLE item;

DROP SEQUENCE itemorder_id_seq;
DROP SEQUENCE itemorder_member_id_seq;
DROP SEQUENCE item_id_seq;
