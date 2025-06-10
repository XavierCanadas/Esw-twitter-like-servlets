CREATE DATABASE IF NOT EXISTS epaw;

USE epaw;

SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS LikeTweet;
DROP TABLE IF EXISTS `follows`;
DROP TABLE IF EXISTS `tweets`;
DROP TABLE IF EXISTS Users;
DROP TABLE IF EXISTS Polis;

SET FOREIGN_KEY_CHECKS = 1;


CREATE TABLE IF NOT EXISTS Polis
(
    id   INT         NOT NULL AUTO_INCREMENT,
    name VARCHAR(20) NOT NULL,

    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS Users
(
    id             INT                                                    NOT NULL AUTO_INCREMENT,
    username       VARCHAR(20)                                            NOT NULL,
    password       VARCHAR(30)                                            NOT NULL,
    email          VARCHAR(20)                                            NOT NULL,
    gender         ENUM ('male', 'female', 'non-binary', 'undeterminate') NOT NULL,
    birthday 	   DATE                                                   NOT NULL,
    socialCredit   INT          DEFAULT 0,
    is_admin       BOOLEAN      DEFAULT FALSE,
    picture        VARCHAR(100) DEFAULT 'https://www.w3schools.com/w3images/avatar2.png', -- default image set by copilot
    polis_id       INT                                                    NOT NULL,

    PRIMARY KEY (id),
    FOREIGN KEY (polis_id) REFERENCES Polis (id)
);

CREATE TABLE IF NOT EXISTS `tweets` (
  `id` int NOT NULL AUTO_INCREMENT,
  `uid` int DEFAULT NULL,
  `postdatetime` timestamp NULL DEFAULT NULL,
  `content` varchar(100) DEFAULT NULL,
  `pid` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `tweets_users_fk` (`uid`),
  KEY `tweets_tweets_fk` (`pid`),
  CONSTRAINT `tweets_tweets_fk` FOREIGN KEY (`pid`) REFERENCES `tweets` (`id`) ON DELETE CASCADE,
  CONSTRAINT `tweets_users_fk` FOREIGN KEY (`uid`) REFERENCES Users (`id`) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS `follows` (
  `uid` int NOT NULL,
  `fid` int NOT NULL,
  PRIMARY KEY (`uid`,`fid`),
  KEY `fid_users_fk` (`fid`),
  CONSTRAINT `fid_users_fk` FOREIGN KEY (`fid`) REFERENCES `users` (`id`) ON DELETE CASCADE,
  CONSTRAINT `uid_users_fk` FOREIGN KEY (`uid`) REFERENCES `users` (`id`) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS LikeTweet
(
    tweet_id INT NOT NULL,
    user_id  INT NOT NULL,

    PRIMARY KEY (user_id, tweet_id),
    FOREIGN KEY (user_id) REFERENCES Users (id),
    FOREIGN KEY (tweet_id) REFERENCES `tweets` (id)
);



INSERT INTO Polis (name)
VALUES ('Atenas');

SELECT *
FROM Polis;

SELECT *
FROM Users;