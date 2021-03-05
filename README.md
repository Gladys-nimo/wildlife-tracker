# Wildlife-tracker
# Author
Gladys Kanyora

# Description
This is an App that allows you to recruit a well-balanced team of superheros.
# Demo
This application allows rangers to input and record details of animals.
# Technology used
Java.
Gradle(for unit testing).
Bootstrap.
Spark.
Material Design Bootstrap.
# Setup instructions
CREATE DATABASE wildlife_tracker;
\c wildlife_tracker

CREATE TABLE  animals (id SERIAL PRIMARY KEY, name VARCHAR, health VARCHAR, type VARCHAR, age VARCHAR);
CREATE TABLE endangeredAnimals (name VARCHAR, type VARCHAR, health VARCHAR, age VARCHAR);
CREATE TABLE locations (id SERIAL PRIMARY KEY, name VARCHAR);
CREATE TABLE rangers (id SERIAL PRIMARY KEY, name VARCHAR, badge_number VARCHAR, phone_number VARCHAR);
CREATE TABLE sightings (id SERIAL PRIMARY KEY, location_id INTEGER, ranger_id INTEGER, animal_id INTEGER, time TIMESTAMP);
CREATE TABLE locations_sightings (id SERIAL PRIMARY KEY, location_id INTEGER, sighting_id INTEGER);
CREATE TABLE rangers_sightings (id SERIAL PRIMARY KEY, ranger_id INTEGER, sighting_id INTEGER);
CREATE DATABASE wildlife_tracker_test WITH TEMPLATE wildlife_tracker;
\q
# Licence
MIT
# COPYRIGHT
Gladys
