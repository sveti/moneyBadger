create table expense (id bigint not null auto_increment, name varchar(255), timeframe integer, value double precision, primary key (id)) engine=InnoDB;
create table income (id bigint not null auto_increment, name varchar(255), timeframe integer, value double precision, primary key (id)) engine=InnoDB;
create table users (id bigint not null auto_increment, currency integer, email varchar(255), last_name varchar(255), name varchar(255), password varchar(255), username varchar(255), primary key (id)) engine=InnoDB;
alter table users add constraint UK_6dotkott2kjsp8vw4d0m25fb7 unique (email);
alter table users add constraint UK_r43af9ap4edm43mmtq01oddj6 unique (username);
