CREATE DATABASE IF NOT EXISTS epaw;

USE epaw;

DROP TABLE IF EXISTS Users;
DROP TABLE IF EXISTS Polis;
DROP TABLE IF EXISTS Tweet;
DROP TABLE IF EXISTS Follow;
DROP TABLE IF EXISTS LikeTweet;

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
    is_admin BOOLEAN DEFAULT FALSE,
    polis_id INT NOT NULL ,

    PRIMARY KEY (id),
    FOREIGN KEY (polis_id) REFERENCES Polis(id)
);

CREATE TABLE IF NOT EXISTS Tweet
(
    id INT NOT NULL AUTO_INCREMENT,
    content VARCHAR(140) NOT NULL,
    created_at DATETIME NOT NULL,
    user_id INT NOT NULL,
    parent_id INT,

    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES Users(id),
    FOREIGN KEY (parent_id) REFERENCES Tweet(id)
);

CREATE TABLE IF NOT EXISTS Follow
(
    follower_id INT NOT NULL,
    followed_id INT NOT NULL,

    PRIMARY KEY (follower_id, followed_id),
    FOREIGN KEY (follower_id) REFERENCES Users(id),
    FOREIGN KEY (followed_id) REFERENCES Users(id)
);

CREATE TABLE IF NOT EXISTS LikeTweet
(
    tweet_id INT NOT NULL,
    user_id INT NOT NULL,

    PRIMARY KEY (user_id, tweet_id),
    FOREIGN KEY (user_id) REFERENCES Users(id),
    FOREIGN KEY (tweet_id) REFERENCES Tweet(id)
);




INSERT INTO Polis (name) VALUES ('Atenas');

SELECT * FROM Polis;

SELECT * FROM Users;