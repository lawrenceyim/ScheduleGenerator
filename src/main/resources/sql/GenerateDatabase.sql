drop database if exists schedule_generator;
create database if not exists schedule_generator;

use schedule_generator;

CREATE TABLE teachers (
  id serial,
  first_name VARCHAR(45) NOT NULL,
  last_name VARCHAR(45) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE rooms (
    id serial,
    building VARCHAR(45) NOT NULL,
    floor int NOT NULL,
    room_number int NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE subjects (
  id serial,
  name VARCHAR(40) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE student_groups (
  id BIGINT UNSIGNED UNIQUE NOT NULL,
  PRIMARY KEY(id)
);

CREATE TABLE students (
  id serial,
  first_name VARCHAR(45) NOT NULL,
  last_name VARCHAR(45) NOT NULL,
  group_id BIGINT UNSIGNED NOT NULL,
  FOREIGN KEY (group_id) REFERENCES student_groups(id),
  PRIMARY KEY (id)
);

CREATE TABLE courses (
  id serial,
  teacher_id BIGINT UNSIGNED NOT NULL,
  room_id BIGINT UNSIGNED NOT NULL,
  group_id BIGINT UNSIGNED NOT NULL,
  subject_id BIGINT UNSIGNED NOT NULL,
  FOREIGN KEY (teacher_id) REFERENCES teachers(id),
  FOREIGN KEY (room_id) REFERENCES rooms(id),
  FOREIGN KEY (group_id) REFERENCES student_groups(id),
  FOREIGN KEY (subject_id) REFERENCES subjects(id),
  PRIMARY KEY (id)
);

CREATE TABLE schedules (
  id serial,
  course_id BIGINT UNSIGNED NOT NULL,
  day_of_week SMALLINT UNSIGNED NOT NULL,
  time_slot SMALLINT UNSIGNED NOT NULL,
  FOREIGN KEY (course_id) REFERENCES courses(id),
  PRIMARY KEY (id)
);

CREATE TABLE class_periods (
   id serial,
   teacher_id BIGINT UNSIGNED NOT NULL,
   room_id BIGINT UNSIGNED NOT NULL,
   group_id BIGINT UNSIGNED NOT NULL,
   subject_id BIGINT UNSIGNED NOT NULL,
   timeslot INT UNSIGNED NOT NULL,
   FOREIGN KEY (teacher_id) REFERENCES teachers(id),
   FOREIGN KEY (room_id) REFERENCES rooms(id),
   FOREIGN KEY (group_id) REFERENCES student_groups(id),
   FOREIGN KEY (subject_id) REFERENCES subjects(id),
   PRIMARY KEY (id)
);


INSERT INTO teachers(first_name, last_name) VALUES
    ('Ron', 'Swanson'),
    ('Jim', 'Peters'),
    ('Henry', 'Simmons'),
    ( 'Hannah', 'Thompson'),
    ( 'Liam', 'Rodriguez');

INSERT INTO rooms(building, floor, room_number) VALUES
    ('Harrington Hall', 1, 100),
    ('Harrington Hall', 2, 210),
    ('Welsh Building', 1, 150),
    ('Welsh Building', 1, 180);

INSERT INTO subjects(name) VALUES
    ('Physics'),
    ('Chemistry'),
    ('Calculus'),
    ('History'),
    ('PE'),
    ('English'),
    ('Spanish');

INSERT INTO student_groups(id) VALUES
    (1),
    (2);

INSERT INTO students(first_name, last_name, group_id) VALUES
    ('Bob', 'Smith', 1),
    ('Tom', 'Hansen', 1),
    ('Billy', 'Joe', 1),
    ('Ava', 'Anderson', 2),
    ('Ethan', 'Martinez', 2),
    ('Olivia', 'Walker', 2);

INSERT INTO courses(teacher_id, room_id, group_id, subject_id) VALUES
    (1, 1, 1, 1),
    (1, 1, 2, 1),
    (2, 2, 1, 2),
    (3, 3, 2, 2);

INSERT INTO schedules(course_id, day_of_week, time_slot) VALUES
    (1, 1, 1),
    (1, 3, 1),
    (2, 2, 1),
    (2, 4, 1),
    (3, 1, 2);