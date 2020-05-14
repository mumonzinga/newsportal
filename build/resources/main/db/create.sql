CREATE DATABASE newsportal;
\c newsportal;



CREATE TABLE departments (
  id INTEGER,
  name VARCHAR,
  description VARCHAR
);


CREATE TABLE users (
  id SERIAL PRIMARY KEY ,
  name VARCHAR,
  address VARCHAR,
  zipcode VARCHAR,
  phone VARCHAR,
  email VARCHAR,
  position VARCHAR,
  role VARCHAR,
  department VARCHAR
);


CREATE TABLE news (
  id INTEGER,
  content VARCHAR,
  writtenby VARCHAR,
  rating VARCHAR,
  department VARCHAR,
  departmentid INTEGER,
  createdat BIGINT
);

CREATE TABLE departments_users (
  id INTEGER,
  usersid INTEGER,
  departmentid INTEGER
);
CREATE DATABASE newsportal_test WITH TEMPLATE newsportal;