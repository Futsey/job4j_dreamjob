CREATE TABLE if not exists users (
  id SERIAL PRIMARY KEY,
  email varchar UNIQUE,
  password varchar,
  created timestamp
);