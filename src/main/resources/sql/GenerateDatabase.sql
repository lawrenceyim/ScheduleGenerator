DROP database IF EXISTS schedule_generator;
CREATE database IF NOT EXISTS schedule_generator;

USE schedule_generator;

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

CREATE TABLE subject_constraints (
    id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT UNIQUE,
    PRIMARY KEY (id),
    FOREIGN KEY (id) REFERENCES subjects(id)
);

CREATE TABLE teachers (
    id serial,
    first_name VARCHAR(45) NOT NULL,
    last_name VARCHAR(45) NOT NULL,
    subject_id BIGINT UNSIGNED,
    FOREIGN KEY (subject_id) REFERENCES subjects(id) ON DELETE SET NULL,
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
   FOREIGN KEY (subject_id) REFERENCES subjects(id) ON DELETE CASCADE,
   PRIMARY KEY (id)
);

INSERT INTO subjects(name) VALUES
    ('Physics'),
    ('Chemistry'),
    ('Calculus'),
    ('History'),
    ('English'),
    ('Spanish'),
    ('Biology'),
    ('Computer Science'),
    ('Art'),
    ('Economics'),
    ('Geography'),
    ('Mathematics'),
    ('Music'),
    ('Psychology'),
    ('Physical Education');

SET @physical_education_id = (
    SELECT id
    FROM subjects
    WHERE name = 'Physical Education'
);

INSERT INTO subject_constraints (id)
VALUES (@physical_education_id);


INSERT INTO teachers(first_name, last_name, subject_id) VALUES
    ('Ron', 'Swanson', 1),
    ('Jim', 'Peters', 2),
    ('Henry', 'Simmons', 3),
    ('Hannah', 'Thompson', 4),
    ('Liam', 'Rodriguez', 5),
    ('Emma', 'Jones', 6),
    ('Oliver', 'Williams', 7),
    ('Ava', 'Martinez', 8),
    ('William', 'Davis', 9),
    ('Sophia', 'Anderson', 10),
    ('Michael', 'Taylor', 11),
    ('Emily', 'Brown', 12),
    ('Daniel', 'Moore', 13),
    ('Isabella', 'Garcia', 14),
    ('Ethan', 'Miller', 15);

INSERT INTO rooms(building, floor, room_number) VALUES
    ('Harrington Hall', 1, 100),
    ('Harrington Hall', 2, 210),
    ('Welsh Building', 1, 150),
    ('Welsh Building', 1, 180),
    ('Smith Center', 3, 305),
    ('Smith Center', 2, 220),
    ('Johnson Complex', 4, 410),
    ('Johnson Complex', 2, 205),
    ('Stevens Annex', 1, 120),
    ('Stevens Annex', 3, 330),
    ('Baker Tower', 5, 510),
    ('Baker Tower', 4, 420),
    ('Jones Pavilion', 2, 230),
    ('Jones Pavilion', 3, 340),
    ('Taylor Hall', 1, 110);

INSERT INTO student_groups(id) VALUES
    (1),
    (2),
    (3),
    (4),
    (5);

INSERT INTO students(first_name, last_name, group_id) VALUES
    ('Bob', 'Smith', 1),
    ('Tom', 'Hansen', 1),
    ('Billy', 'Joe', 1),
    ('Ava', 'Anderson', 2),
    ('Ethan', 'Martinez', 2),
    ('Olivia', 'Walker', 2),
    ('Liam', 'Thomas', 3),
    ('Emma', 'White', 3),
    ('Noah', 'Taylor', 3),
    ('Sophia', 'Jones', 4),
    ('Jackson', 'Moore', 4),
    ('Mia', 'Davis', 4),
    ('Lucas', 'Garcia', 5),
    ('Isabella', 'Miller', 5),
    ('Henry', 'Brown', 5);