CREATE TABLE if not exists post (
   id SERIAL PRIMARY KEY,
   name TEXT,
   description TEXT,
   visible boolean,
   created timestamp,
   city_id int
);