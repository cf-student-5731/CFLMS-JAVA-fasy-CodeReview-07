--Create a SQL statement that reports all the student of a specific class when you know a
--specific class ID (aka something like “show all students of the class 1b, which has the ID=2”)

SELECT * FROM students
INNER JOIN attends
ON attends.fk_student = students.id
WHERE attends.fk_class = 14;

------------------------------------------------------------------------------------------------------

--Create a SQL statement that reports all the student of a specific class when you know a
--specific class name (aka something like “show all students of the class ‘1b’; I don’t know the students ID”)

SELECT * FROM students
INNER JOIN attends
ON attends.fk_student = students.id
INNER JOIN classes
ON classes.id = attends.fk_class
WHERE classes.name = "5b";

------------------------------------------------------------------------------------------------------

--Example 1 (n-to-m):
--Add n-to-m relation to database that saves the data of type “a single teacher can teach
--several classes” and a class can be taught by several teachers (n-to-m). Connect the tables accordingly.

CREATE TABLE teaches(
	id int,
	fk_teacher int NOT NULL,
	fk_class int NOT NULL,
	PRIMARY KEY (id),
	FOREIGN KEY (fk_teacher) REFERENCES teachers (id),
	FOREIGN KEY (fk_class) REFERENCES classes (id)
);

------------------------------------------------------------------------------------------------------

--Example 2, (1-to-n):
--Add 1-to-n relation to database that saves the data of type “a single student can attend
--only one class” and a class can be attended by several students (1-to-n). Connect the tables accordingly.

CREATE TABLE attends(
	id int,
	fk_student int UNIQUE NOT NULL,
	fk_class int NOT NULL,
	PRIMARY KEY (id),
	FOREIGN KEY (fk_student) REFERENCES students (id),
	FOREIGN KEY (fk_class) REFERENCES classes (id)
);

