CREATE DATABASE IF NOT EXISTS epaw;

USE epaw;

SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS LikeTweet;
DROP TABLE IF EXISTS FollowUser;
DROP TABLE IF EXISTS Tweet;
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
    id           INT                                                   NOT NULL AUTO_INCREMENT,
    username     VARCHAR(20)                                           NOT NULL,
    password     VARCHAR(255)                                          NOT NULL,
    email        VARCHAR(100)                                          NOT NULL,
    gender       ENUM ('male', 'female', 'non-binary', 'undetermined') NOT NULL,
    birthday     DATE                                                  NOT NULL,
    socialCredit INT          DEFAULT 0,
    is_admin     BOOLEAN      DEFAULT FALSE,
    picture      VARCHAR(100) DEFAULT 'default.jpg',
    polis_id     INT                                                   NOT NULL,

    PRIMARY KEY (id),
    FOREIGN KEY (polis_id) REFERENCES Polis (id)
);

CREATE TABLE IF NOT EXISTS Tweet
(
    id            int       NOT NULL AUTO_INCREMENT,
    user_id       int            DEFAULT NULL,
    post_datetime timestamp NULL DEFAULT NULL,
    content       varchar(100)   DEFAULT NULL,
    parent_id     int            DEFAULT NULL,
    PRIMARY KEY (id),
    KEY `tweets_users_fk` (user_id),
    KEY `tweets_tweets_fk` (parent_id),
    CONSTRAINT `tweets_tweets_fk` FOREIGN KEY (parent_id) REFERENCES Tweet (id) ON DELETE CASCADE,
    CONSTRAINT `tweets_users_fk` FOREIGN KEY (user_id) REFERENCES Users (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS FollowUser
(
    user_id     int NOT NULL,
    followed_id int NOT NULL,
    PRIMARY KEY (user_id, followed_id),
    KEY fid_users_fk (followed_id),
    CONSTRAINT fid_users_fk FOREIGN KEY (followed_id) REFERENCES Users (id) ON DELETE CASCADE,
    CONSTRAINT uid_users_fk FOREIGN KEY (user_id) REFERENCES Users (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS LikeTweet
(
    tweet_id INT NOT NULL,
    user_id  INT NOT NULL,

    PRIMARY KEY (user_id, tweet_id),
    FOREIGN KEY (user_id) REFERENCES Users (id) ON DELETE CASCADE,
    FOREIGN KEY (tweet_id) REFERENCES Tweet (id) ON DELETE CASCADE
);



INSERT INTO Polis (name)
VALUES ('Atenas');

INSERT INTO Users (username, password, email, gender, birthday, polis_id)
Values ('olivia.rodrigo', 'Aaee1122@', 'olivia.rodrigo@example.com', 'female', '2002-02-18', 1),
       ('gracie.abrams', 'Aaee1122@', 'gracie.abrams@example.com', 'female', '1999-09-07', 1);

SELECT *
FROM Polis;

SELECT *
FROM Users;

SELECT *
FROM Tweet;

SELECT *
FROM LikeTweet;

EXIST (SELECT 1 FROM LikeTweet lt WHERE lt.tweet_id = )

