

# --- !Ups

CREATE SEQUENCE member_id_seq;
CREATE TABLE member (
       id integer PRIMARY KEY DEFAULT nextval('member_id_seq'),
       name varchar(255)
);

# --- !Downs

DROP TABLE member;
DROP SEQUENCE member_id_seq;