CREATE DATABASE IF NOT EXISTS epaw;

USE epaw;

CREATE TABLE IF NOT EXISTS Polis (
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(20) NOT NULL,

    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS Users
(
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(20) NOT NULL,
    email VARCHAR(20) NOT NULL,
    gender ENUM('male', 'female', 'non-binary', 'undeterminate') NOT NULL,
    birthday DATE NOT NULL,
    polis_id INT NOT NULL,
    password VARCHAR(30) NOT NULL,

    PRIMARY KEY (id),
    FOREIGN KEY (polis_id) REFERENCES Polis(id)
);