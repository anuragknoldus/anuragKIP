# --- Created by Slick DDL
# To stop Slick DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table "USER" ("id" BIGSERIAL NOT NULL PRIMARY KEY,"name" VARCHAR(254) NOT NULL,"address" VARCHAR(254) NOT NULL,"email" VARCHAR(254) NOT NULL,"password" VARCHAR(254) NOT NULL,"phone" BIGINT NOT NULL,"usertype" BIGINT DEFAULT 2,"created" DATE,"updated" DATE);
create unique index "IDX_EMAIL" on "USER" ("email");

# --- !Downs

drop table "USER";

