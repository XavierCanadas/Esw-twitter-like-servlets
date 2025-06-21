/* this file is to test all the queries in the database before using them in the java code */
USE epaw;
/*
 * Tweets
 */

-- get tweet by id
SELECT currentTweet.*,
       u.username,
       u.picture                    as user_picture,
       COUNT(lt.user_id)            AS likes_count,
       EXISTS(SELECT 1
              FROM LikeTweet AS lt
              WHERE lt.tweet_id = currentTweet.id
                AND lt.user_id = ?) AS liked_by_current_user
FROM Tweet AS currentTweet
         JOIN Users AS u ON currentTweet.user_id = u.id
         LEFT JOIN LikeTweet lt on currentTweet.id = lt.tweet_id
WHERE currentTweet.id = ?
GROUP BY currentTweet.id, currentTweet.post_datetime, currentTweet.content, u.username, u.picture;

-- get comments
SELECT commentTweet.*,
       u.username,
       u.picture                    as user_picture,
       COUNT(lt.user_id)            AS likes_count,
       EXISTS(SELECT 1
              FROM LikeTweet AS lt
              WHERE lt.tweet_id = commentTweet.id
                AND lt.user_id = ?) AS liked_by_current_user
FROM Tweet AS commentTweet
         JOIN Tweet AS parent ON commentTweet.parent_id = parent.id
         JOIN Users AS u ON commentTweet.user_id = u.id
         LEFT JOIN LikeTweet lt on commentTweet.id = lt.tweet_id
WHERE parent.id = ?
GROUP BY commentTweet.id, commentTweet.post_datetime, commentTweet.content, u.username, u.picture
ORDER BY commentTweet.post_datetime DESC LIMIT ?, ?;



-- detele a parent tweet and check if the comments are deleted
INSERT INTO Tweet (id, user_id, content, post_datetime, parent_id)
VALUES (69, 1, 'This is a test tweet', '2023-10-01 12:00:00', NULL);


INSERT INTO Tweet (id, user_id, content, post_datetime, parent_id)
VALUES (70, 1, 'This is a test comment', '2023-10-01 12:00:00', 69);


SELECT *
FROM Tweet
WHERE id = 69;

SELECT *
FROM Tweet
WHERE parent_id = 69;

SELECT *
FROM Tweet
WHERE id = 70;

DELETE FROM Tweet
WHERE id = 69;


-- delete a user and check if the tweets are deleted and the comments of the tweets
INSERT INTO Users (id, username, password, email, gender, birthday, polis_id)
Values (69, 'olivia.rodrigo.prova', 'Aaee1122@', 'olivia.rodrigo@example.com', 'female', '2002-02-18', 1);

INSERT INTO Tweet (id, user_id, content, post_datetime, parent_id)
VALUES (69, 69, 'This is a test tweet', '2023-10-01 12:00:00', NULL);


INSERT INTO Tweet (id, user_id, content, post_datetime, parent_id)
VALUES (70, 1, 'This is a test comment', '2023-10-01 12:00:00', 69);

SELECT *
FROM Users
WHERE id = 69;

SELECT *
FROM Tweet
WHERE id = 69;

SELECT *
FROM Tweet
WHERE id = 70;

DELETE FROM Users
WHERE id = 69;

UPDATE Users
SET is_admin = 1
WHERE username = 'gracie.abrams';
