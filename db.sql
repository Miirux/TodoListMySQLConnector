CREATE DATABASE todolist IF NOT EXISTS

CREATE TABLE account(Id INTEGER PRIMARY KEY AUTO_INCREMENT,FullName VARCHAR(255),Type VARCHAR(255),Email VARCHAR(255),Password VARCHAR(255));
CREATE TABLE todoitem(Id INTEGER PRIMARY KEY AUTO_INCREMENT,Title VARCHAR(255),Description VARCHAR(255),idAccount int, FOREIGN KEY (idAccount) REFERENCES account(Id));

INSERT INTO account(FullName,Type,Email,Password) VALUES("admin", "admin", "admin@admin.com","123");