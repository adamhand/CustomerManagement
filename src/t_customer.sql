drop database if exists customermanagement;
create database customermanagement default charset utf8;
use customermanagement;


drop table if exists t_customer;
create table t_customer
(
	id varchar(5) not null primary key,
	name varchar(16) not null,
	gender varchar(8) not null,
	phone varchar(16) null default "-",
	email varchar(16) null default "-",
	description varchar(255) null default "-"
);