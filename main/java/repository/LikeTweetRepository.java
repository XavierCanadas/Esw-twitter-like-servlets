package repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import model.Tweet;
import model.User;

public class LikeTweetRepository extends BaseRepository {

    public void likeTweet(Integer tweetId, Integer userId) {
        String query = "INSERT INTO LikeTweet (tweet_id, user_id) VALUES (?,?);";

        try (PreparedStatement statement = db.prepareStatement(query)) {
            statement.setInt(1, tweetId);
            statement.setInt(2, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeLike(Integer tweetId, Integer userId) {
        String query = "DELETE FROM LikeTweet WHERE tweet_id = ? AND user_id = ?;";

        try (PreparedStatement statement = db.prepareStatement(query)) {
            statement.setInt(1, tweetId);
            statement.setInt(2, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isLiked(Integer tweetId, Integer userId) {
        String query = "SELECT COUNT(*) FROM LikeTweet WHERE tweet_id = ? AND user_id = ?;";
        try (PreparedStatement statement = db.prepareStatement(query)) {
            statement.setInt(1, tweetId);
            statement.setInt(2, userId);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Optional<List<Tweet>> findLikedTweetsByUser(Integer userId, Integer start, Integer end) {
        List<Tweet> tweets = new ArrayList<Tweet>();

        String query = "SELECT t.*, author.username " +
                "FROM Tweet t " +
                "JOIN LikeTweet lt ON t.id = lt.tweet_id " +
                "JOIN Users author ON lt.user_id = author.id " +
                "WHERE lt.user_id = ? " +
                "ORDER BY t.post_datetime DESC " +
                "LIMIT ?, ?;";

        try (PreparedStatement statement = db.prepareStatement(query)) {
            statement.setInt(1, userId);
            statement.setInt(2, start);
            statement.setInt(3, end);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    Tweet tweet = new Tweet();
                    tweet.setId(rs.getInt("id"));
                    tweet.setUid(rs.getInt("user_id"));
                    tweet.setPostDateTime(rs.getTimestamp("post_datetime"));
                    tweet.setContent(rs.getString("content"));
                    tweet.setUsername(rs.getString("username"));
                    tweets.add(tweet);
                }
                return Optional.of(tweets);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public Integer getLikesCount(Integer tweetId) {
        String query = "SELECT COUNT(*) FROM LikeTweet WHERE tweet_id = ?;";
        try (PreparedStatement statement = db.prepareStatement(query)) {
            statement.setInt(1, tweetId);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
