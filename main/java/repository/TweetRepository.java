package repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import model.Tweet;
//import service.LikeTweetService;

public class TweetRepository extends BaseRepository {

    public void save(Tweet tweet) {
        String query = "INSERT INTO Tweet (user_id, post_datetime, content) VALUES (?,?,?)";
        try (PreparedStatement statement = db.prepareStatement(query)) {
            statement.setInt(1, tweet.getUid());
            statement.setTimestamp(2, tweet.getPostDateTime());
            statement.setString(3, tweet.getContent());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveComment(Tweet tweet) {
        String query = "INSERT INTO Tweet (user_id, post_datetime, content, parent_id) VALUES (?,?,?,?)";
        try (PreparedStatement statement = db.prepareStatement(query)) {
            statement.setInt(1, tweet.getUid());
            statement.setTimestamp(2, tweet.getPostDateTime());
            statement.setString(3, tweet.getContent());
            statement.setObject(4, tweet.getParentId(), java.sql.Types.INTEGER); // Use setObject for nullable parent_id
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /* Delete existing tweet */
    public void delete(Integer id, Integer uid) {
        String query = "DELETE FROM Tweet WHERE id = ? AND user_id=?";
        try (PreparedStatement statement = db.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.setInt(2, uid);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /* Get tweets from a user given start and end */
    public Optional<List<Tweet>> findByUser(Integer uid, Integer start, Integer end) {
        List<Tweet> tweets = new ArrayList<Tweet>();
        String query = "SELECT t.id, t.user_id, t.post_datetime, t.content, t.parent_id, u.username, u.picture as user_picture, COUNT(lt.user_id) AS like_count, " +
                "EXISTS (SELECT 1 FROM LikeTweet ltu WHERE ltu.tweet_id = t.id AND ltu.user_id = ?) AS liked_by_current_user " +
                "FROM Tweet t " +
                "INNER JOIN Users u ON t.user_id = u.id " +
                "LEFT JOIN LikeTweet lt ON t.id = lt.tweet_id " +
                "WHERE t.user_id = ? " +
                "GROUP BY t.id " +
                "ORDER BY t.post_datetime DESC " +
                "LIMIT ?,?;";

        try (PreparedStatement statement = db.prepareStatement(query)) {
            statement.setInt(1, uid);
            statement.setInt(2, uid);
            statement.setInt(3, start);
            statement.setInt(4, end);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    Tweet tweet = new Tweet();
                    tweet.setId(rs.getInt("id"));
                    tweet.setUid(rs.getInt("user_id"));
                    tweet.setPostDateTime(rs.getTimestamp("post_datetime"));
                    tweet.setContent(rs.getString("content"));
                    tweet.setUsername(rs.getString("username"));
                    tweet.setLikesCount(rs.getInt("like_count"));
                    tweet.setParentId(rs.getObject("parent_id", Integer.class));
                    tweet.setLikedByCurrentUser(rs.getBoolean("liked_by_current_user"));
                    tweet.setProfilePictureUrl(rs.getString("user_picture"));
                    tweets.add(tweet);
                }
                return Optional.of(tweets);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    /*gets latest 20 tweets*/
    public Optional<List<Tweet>> getLatestTweets() {

        List<Tweet> tweets = new ArrayList<Tweet>();
        String query = "SELECT t.id, t.user_id, t.post_datetime, t.content, t.parent_id, u.username, u.picture as user_picture, " +
                "COUNT(lt.tweet_id) AS like_count " +
                "FROM Tweet t " +
                "JOIN Users u ON t.user_id = u.id " +
                "LEFT JOIN LikeTweet lt ON t.id = lt.tweet_id " +
                "GROUP BY t.id, t.user_id, t.post_datetime, t.content, u.username " +
                "ORDER BY t.post_datetime DESC LIMIT 20";

        try (PreparedStatement statement = db.prepareStatement(query)) {
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    Tweet tweet = new Tweet();
                    tweet.setId(rs.getInt("id"));
                    tweet.setUid(rs.getInt("user_id"));
                    tweet.setPostDateTime(rs.getTimestamp("post_datetime"));
                    tweet.setContent(rs.getString("content"));
                    tweet.setUsername(rs.getString("username"));
                    tweet.setLikesCount(rs.getInt("like_count"));
                    tweet.setParentId(rs.getObject("parent_id", Integer.class));
                    tweet.setLikedByCurrentUser(false);
                    tweet.setProfilePictureUrl(rs.getString("user_picture"));

                    tweets.add(tweet);
                }
                return Optional.of(tweets);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public Optional<List<Tweet>> getFollowingTweets(Integer uid, Integer start, Integer end) {
        List<Tweet> tweets = new ArrayList<Tweet>();
        String query = "SELECT  t.id, t.user_id, t.post_datetime, t.content, t.parent_id,  u.username, u.picture as user_picture, COUNT(lt.user_id) AS like_count, " +
                "EXISTS (SELECT 1 FROM LikeTweet ltu WHERE ltu.tweet_id = t.id AND ltu.user_id = ?) AS liked_by_current_user " +
                "FROM Tweet t " +
                "INNER JOIN Users u ON t.user_id = u.id " +
                "INNER JOIN FollowUser f ON t.user_id = f.followed_id " +
                "LEFT JOIN LikeTweet lt ON t.id = lt.tweet_id " +
                "WHERE f.user_id = ? " +
                "GROUP BY t.id, u.username, u.picture " +
                "ORDER BY  t.post_datetime DESC " +
                "LIMIT ?, ?;";

        try (PreparedStatement statement = db.prepareStatement(query)) {
            statement.setInt(1, uid);
            statement.setInt(2, uid);
            statement.setInt(3, start);
            statement.setInt(4, end);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    Tweet tweet = new Tweet();
                    tweet.setId(rs.getInt("id"));
                    tweet.setUid(rs.getInt("user_id"));
                    tweet.setPostDateTime(rs.getTimestamp("post_datetime"));
                    tweet.setContent(rs.getString("content"));
                    tweet.setUsername(rs.getString("username"));
                    tweet.setLikesCount(rs.getInt("like_count"));
                    tweet.setParentId(rs.getObject("parent_id", Integer.class));
                    tweet.setLikedByCurrentUser(rs.getBoolean("liked_by_current_user"));
                    tweet.setProfilePictureUrl(rs.getString("user_picture"));

                    tweets.add(tweet);
                }
                return Optional.of(tweets);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public Optional<List<Tweet>> getPolisTweets(Integer uid, Integer start, Integer end) {
        List<Tweet> tweets = new ArrayList<>();
        String query = "SELECT " +
                "t.id, " +
                "t.user_id, " +
                "t.post_datetime, " +
                "t.content, " +
                "t.parent_id, " +
                "u.username, " +
                "u.picture as user_picture, " +
                "COUNT(lt_all.user_id) AS like_count, " +
                "EXISTS ( " +
                "    SELECT 1 " +
                "    FROM LikeTweet lt_user " +
                "    WHERE lt_user.tweet_id = t.id " +
                "      AND lt_user.user_id = ? " +
                ") AS liked_by_current_user " +
                "FROM Tweet t " +
                "INNER JOIN Users u ON t.user_id = u.id " +
                "LEFT JOIN LikeTweet lt_all ON lt_all.tweet_id = t.id " +
                "WHERE u.polis_id = ( " +
                "    SELECT polis_id FROM Users WHERE id = ? " +
                ") " +
                "GROUP BY t.id, u.username, t.user_id, t.post_datetime, t.content " +
                "ORDER BY t.post_datetime DESC " +
                "LIMIT ?, ?;";

        try (PreparedStatement statement = db.prepareStatement(query)) {
            statement.setInt(1, uid);   // EXISTS LIKE
            statement.setInt(2, uid);   // polis_id subquery
            statement.setInt(3, start); // pagination
            statement.setInt(4, end);   // pagination

            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    Tweet tweet = new Tweet();
                    tweet.setId(rs.getInt("id"));
                    tweet.setUid(rs.getInt("user_id"));
                    tweet.setPostDateTime(rs.getTimestamp("post_datetime"));
                    tweet.setContent(rs.getString("content"));
                    tweet.setUsername(rs.getString("username"));
                    tweet.setLikesCount(rs.getInt("like_count"));
                    tweet.setParentId(rs.getObject("parent_id", Integer.class));
                    tweet.setLikedByCurrentUser(rs.getBoolean("liked_by_current_user"));
                    tweet.setProfilePictureUrl(rs.getString("user_picture"));

                    tweets.add(tweet);
                }
                return Optional.of(tweets);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    // get the comments of a tweet by its id
    public Optional<List<Tweet>> getCommentsByTweetId(Integer parentId, Integer currentUserId, Integer start, Integer end) {
        List<Tweet> tweets = new ArrayList<Tweet>();
        String query = "SELECT commentTweet.*, u.username, u.picture as user_picture, COUNT(lt.user_id) AS likes_count, " +
                "       EXISTS(SELECT 1 " +
                "              FROM LikeTweet AS lt " +
                "              WHERE lt.tweet_id = commentTweet.id AND lt.user_id = ? ) AS liked_by_current_user " +
                "FROM Tweet AS commentTweet " +
                "JOIN Tweet AS parent ON commentTweet.parent_id = parent.id " +
                "JOIN Users AS u ON commentTweet.user_id = u.id " +
                "LEFT JOIN LikeTweet lt on commentTweet.id = lt.tweet_id " +
                "WHERE parent.id = ? " +
                "GROUP BY commentTweet.id, commentTweet.post_datetime, commentTweet.content, u.username, u.picture " +
                "ORDER BY commentTweet.post_datetime DESC " +
                "LIMIT ?, ?;";

        try (PreparedStatement statement = db.prepareStatement(query)) {
            statement.setInt(1, currentUserId);
            statement.setInt(2, parentId);
            statement.setInt(3, start);
            statement.setInt(4, end);

            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    Tweet tweet = new Tweet();
                    tweet.setId(rs.getInt("id"));
                    tweet.setUid(rs.getInt("user_id"));
                    tweet.setPostDateTime(rs.getTimestamp("post_datetime"));
                    tweet.setContent(rs.getString("content"));
                    tweet.setUsername(rs.getString("username"));
                    tweet.setLikesCount(rs.getInt("likes_count"));
                    tweet.setParentId(rs.getObject("parent_id", Integer.class));
                    tweet.setLikedByCurrentUser(rs.getBoolean("liked_by_current_user"));

                    tweet.setProfilePictureUrl(rs.getString("user_picture"));

                    tweets.add(tweet);
                }
                return Optional.of(tweets);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public Optional<Tweet> getTweetById(Integer tweetId, Integer currentUserId) {
        Tweet tweet = null;

        String query = "SELECT currentTweet.*, " +
                "       u.username," +
                "       u.picture                    as user_picture, " +
                "       COUNT(lt.user_id)            AS likes_count, " +
                "       EXISTS(SELECT 1 " +
                "              FROM LikeTweet AS lt " +
                "              WHERE lt.tweet_id = currentTweet.id " +
                "                AND lt.user_id = ?) AS liked_by_current_user " +
                "FROM Tweet AS currentTweet " +
                "         JOIN Users AS u ON currentTweet.user_id = u.id " +
                "         LEFT JOIN LikeTweet lt on currentTweet.id = lt.tweet_id " +
                "WHERE currentTweet.id = ? " +
                "GROUP BY currentTweet.id, currentTweet.post_datetime, currentTweet.content, u.username, u.picture;";

        try (PreparedStatement statement = db.prepareStatement(query)) {
            statement.setInt(1, currentUserId);
            statement.setInt(2, tweetId);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    tweet = new Tweet();
                    tweet.setId(rs.getInt("id"));
                    tweet.setUid(rs.getInt("user_id"));
                    tweet.setPostDateTime(rs.getTimestamp("post_datetime"));
                    tweet.setContent(rs.getString("content"));
                    tweet.setUsername(rs.getString("username"));
                    tweet.setLikesCount(rs.getInt("likes_count"));
                    tweet.setLikedByCurrentUser(rs.getBoolean("liked_by_current_user"));
                    tweet.setParentId(rs.getObject("parent_id", Integer.class));
                    tweet.setProfilePictureUrl(rs.getString("user_picture"));
                }
                return Optional.ofNullable(tweet);
            }

        }  catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

}



