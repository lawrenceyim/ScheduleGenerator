CREATE TABLE Subjects (
  id INT NOT NULL,
  name VARCHAR(40) NOT NULL,
  room INT NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE Students (
  id INT NOT NULL,
  first_name VARCHAR(45) NOT NULL,
  last_name VARCHAR(45) NOT NULL,
  group_id INT NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE StudentGroups (
  id INT NOT NULL,
  PRIMARY KEY(id)
);

CREATE TABLE Classes (
  id INT NOT NULL,
  group_id INT NOT NULL,
  subject_id INT NOT NULL,
  time_slot INT NOT NULL,
  day_of_week VARCHAR(20) NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (group_id) REFERENCES StudentGroups(id),
  FOREIGN KEY(subject_id) REFERENCES Subjects(id)
);