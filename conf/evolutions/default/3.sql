
# --- !Ups

CREATE SEQUENCE bulkitem_id_seq;

CREATE TABLE bulkitem (
       id integer PRIMARY KEY DEFAULT nextval('bulkitem_id_seq'),
       name varchar(64),
       description varchar(128),
       cost numeric(8,2),
       url varchar(128)
);

CREATE SEQUENCE bulkitemorder_id_seq;
CREATE TABLE bulkitemorder (
       id integer PRIMARY KEY DEFAULT nextval('bulkitemorder_id_seq'),
       bulkitem_id integer references bulkitem(id),
       minimumbuyers integer,
       deadline_by timestamp,
       deliveryaddress varchar(128),
       created_at timestamp default now(),
       created_by_id integer references member(id),
       coop_id integer references coop(id)
);  
CREATE SEQUENCE member_bulkitemorder_id_seq;
create table member_bulkitemorder (
       id integer PRIMARY KEY DEFAULT nextval('member_bulkitemorder_id_seq'),
       member_id integer references member(id),
       bulkitemorder_id integer references bulkitemorder(id)
);

# --- !Downs

drop table member_bulkitemorder;
DROP TABLE bulkitemorder;
DROP TABLE bulkitem;

DROP SEQUENCE bulkitemorder_id_seq;
DROP SEQUENCE member_bulkitemorder_id_seq;
DROP SEQUENCE bulkitem_id_seq;
