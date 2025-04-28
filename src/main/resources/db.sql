CREATE TABLE User (
                      userID INT PRIMARY KEY AUTO_INCREMENT,
                      username VARCHAR(50) NOT NULL UNIQUE,
                      password VARCHAR(100) NOT NULL,
                      email VARCHAR(100) UNIQUE,
                      role VARCHAR(20) CHECK (role IN ('admin', 'teacher', 'student', 'parent'))
);

CREATE TABLE Parent (
                        parentID INT PRIMARY KEY AUTO_INCREMENT,
                        name VARCHAR(100),
                        contact VARCHAR(20),
                        email VARCHAR(100),
                        address VARCHAR(255)
);

CREATE TABLE Student (
                         studentID INT PRIMARY KEY AUTO_INCREMENT,
                         userID INT,
                         parentID INT,
                         name VARCHAR(100),
                         grade VARCHAR(10),
                         section VARCHAR(10),
                         dateOfBirth DATE,
                         address VARCHAR(255),
                         FOREIGN KEY (userID) REFERENCES User(userID),
                         FOREIGN KEY (parentID) REFERENCES Parent(parentID)
);

CREATE TABLE Teacher (
                         teacherID INT PRIMARY KEY AUTO_INCREMENT,
                         userID INT,
                         name VARCHAR(100),
                         qualification VARCHAR(100),
                         joinDate DATE,
                         contact VARCHAR(20),
                         FOREIGN KEY (userID) REFERENCES User(userID)
);

CREATE TABLE Course (
                        courseID INT PRIMARY KEY AUTO_INCREMENT,
                        courseName VARCHAR(100),
                        description TEXT,
                        grade VARCHAR(10),
                        teacherID INT,
                        FOREIGN KEY (teacherID) REFERENCES Teacher(teacherID)
);

CREATE TABLE Attendance (
                            attendanceID INT PRIMARY KEY AUTO_INCREMENT,
                            studentID INT,
                            courseID INT,
                            attendanceDate DATE,
                            status VARCHAR(10) CHECK (status IN ('Present', 'Absent')),
                            FOREIGN KEY (studentID) REFERENCES Student(studentID),
                            FOREIGN KEY (courseID) REFERENCES Course(courseID)
);

CREATE TABLE Exam (
                      examID INT PRIMARY KEY AUTO_INCREMENT,
                      examName VARCHAR(100),
                      courseID INT,
                      examDate DATE,
                      startTime TIME,
                      endTime TIME,
                      FOREIGN KEY (courseID) REFERENCES Course(courseID)
);

CREATE TABLE Grade (
                       gradeID INT PRIMARY KEY AUTO_INCREMENT,
                       studentID INT,
                       examID INT,
                       score FLOAT,
                       letter VARCHAR(2),
                       FOREIGN KEY (studentID) REFERENCES Student(studentID),
                       FOREIGN KEY (examID) REFERENCES Exam(examID)
);

CREATE TABLE Fee (
                     feeID INT PRIMARY KEY AUTO_INCREMENT,
                     studentID INT,
                     amount FLOAT,
                     dueDate DATE,
                     paymentDate DATE,
                     status VARCHAR(20) CHECK (status IN ('Paid', 'Unpaid', 'Pending')),
                     FOREIGN KEY (studentID) REFERENCES Student(studentID)
);

-- If Admin needs a table (though role in User already distinguishes it)
CREATE TABLE Admin (
                       adminID INT PRIMARY KEY AUTO_INCREMENT,
                       userID INT,
                       FOREIGN KEY (userID) REFERENCES User(userID)
);

-- Optional: Notification Service Log
CREATE TABLE Notification (
                              notificationID INT PRIMARY KEY AUTO_INCREMENT,
                              userID INT,
                              type VARCHAR(20),
                              message TEXT,
                              createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                              FOREIGN KEY (userID) REFERENCES User(userID)
);