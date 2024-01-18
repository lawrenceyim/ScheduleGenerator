drop database if exists schedule_generator;
create database if not exists schedule_generator;

use schedule_generator;

CREATE TABLE teachers (
  id serial,
  first_name VARCHAR(45) NOT NULL,
  last_name VARCHAR(45) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE subjects (
  id serial,
  name VARCHAR(40) NOT NULL,
  teacher_id BIGINT UNSIGNED UNIQUE NOT NULL,
  room INT NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (teacher_id) REFERENCES teachers(id)
);

CREATE TABLE students (
  id serial,
  first_name VARCHAR(45) NOT NULL,
  last_name VARCHAR(45) NOT NULL,
  group_id LONG NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE student_groups (
  id serial,
  PRIMARY KEY(id)
);

CREATE TABLE classes (
  id serial,
  group_id BIGINT UNSIGNED NOT NULL,
  subject_id BIGINT UNSIGNED NOT NULL,
  time_slot INT NOT NULL,
  day_of_week VARCHAR(20) NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (group_id) REFERENCES student_groups(id),
  FOREIGN KEY(subject_id) REFERENCES subjects(id)
);

insert into teachers(first_name, last_name) values
('Ron', 'Swanson'),
('Jim', 'Peters'),
('Henry', 'Simmons');

insert into subjects(name, teacher_id, room) values
('Physics', 1, 100),
('Chemistry', 2, 200),
('Calculus', 3, 100);

insert into student_groups(id) values
(1),
(2);

insert into students(first_name, last_name, group_id) values
('Bob', 'Smith', 1),
('Tom', 'Hansen', 2),
('Billy', 'Joe', 1);

insert into classes(group_id, subject_id, time_slot, day_of_week) values
(1, 1, 1, 'MONDAY'),
(1, 2, 2, 'MONDAY'),
(2, 3, 1, 'MONDAY');