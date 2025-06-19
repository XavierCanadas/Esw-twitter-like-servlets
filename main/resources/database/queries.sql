/* this file is to test all the queries in the database before using them in the java code */

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