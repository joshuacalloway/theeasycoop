
# --- !Ups

CREATE SEQUENCE bulkitem_id_seq;

CREATE TABLE bulkitem (
       id integer PRIMARY KEY DEFAULT nextval('bulkitem_id_seq'),
       name varchar(64),
       description varchar(128),
       cost numeric(8,2),
       url varchar(128)
);

insert into bulkitem(id, name, description, cost, url) values (1, 'Fresh Salmon Sushi Grade', '10 lbs the very best', 300.99, 'salmon.com');

CREATE SEQUENCE bulkitemorder_id_seq;
CREATE TABLE bulkitemorder (
       id integer PRIMARY KEY DEFAULT nextval('bulkitemorder_id_seq'),
       bulkitem_id integer references bulkitem(id),
       minimumbuyers integer,
       itemcost numeric(8,2),
       itemdescription varchar(128),
       deadline_by timestamp,
       deliveryaddress varchar(128),
       created_at timestamp default now(),
       created_by_id integer references member(id),
       coop_id integer references coop(id)
);  
CREATE SEQUENCE bulkitemorder_member_id_seq;
create table bulkitemorder_member (
       id integer PRIMARY KEY DEFAULT nextval('bulkitemorder_member_id_seq'),
       member_id integer references member(id),
       bulkitemorder_id integer references bulkitemorder(id)
       );

insert into bulkitemorder(id, bulkitem_id, minimumbuyers, itemcost, itemdescription, deadline_by, deliveryaddress, created_at, created_by_id, coop_id) values (1, 1, 10, 30.10, '1 lb of salmon each', '2012-09-01', '1515 N Wicker', now(), 0, 0);


# --- !Downs


drop table bulkitemorder_member;
DROP TABLE bulkitemorder;
DROP TABLE bulkitem;

DROP SEQUENCE bulkitemorder_id_seq;
DROP SEQUENCE bulkitemorder_member_id_seq;
DROP SEQUENCE bulkitem_id_seq;
