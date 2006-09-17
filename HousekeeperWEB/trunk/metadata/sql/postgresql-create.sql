-- drop the existing database
drop database housekeeper;

-- create the test user
create user test password 'test';

-- create the database
create database housekeeper owner test;
