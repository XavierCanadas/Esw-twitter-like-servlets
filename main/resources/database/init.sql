CREATE DATABASE IF NOT EXISTS epaw;

USE epaw;

DROP TABLE IF EXISTS Users;
DROP TABLE IF EXISTS Polis;

CREATE TABLE IF NOT EXISTS Polis (
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(20) NOT NULL,

    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS Users
(
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(20) NOT NULL,
    password VARCHAR(30) NOT NULL,
    email VARCHAR(20) NOT NULL,
    gender ENUM('male', 'female', 'non-binary', 'undeterminate') NOT NULL,
    birthdayString VARCHAR(20) NOT NULL,
    socialCredit INT DEFAULT 0,
    polis_id INT NOT NULL ,


    PRIMARY KEY (id),
    FOREIGN KEY (polis_id) REFERENCES Polis(id)
);

INSERT INTO Polis (name) VALUES ('Atenas');

SELECT * FROM Polis;

SELECT * FROM Users;