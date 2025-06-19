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
		String query = "SELECT t.id, t.user_id, t.post_datetime, t.content, u.username, COUNT(lt.user_id) AS like_count, " +
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
					tweet.setLikedByCurrentUser(rs.getBoolean("liked_by_current_user"));

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
	public Optional<List<Tweet>> getLatestTweets(){
		
		List<Tweet> tweets = new ArrayList<Tweet>();
	    String query = "SELECT t.id, t.user_id, t.post_datetime, t.content, u.username, " +
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
					tweet.setLikedByCurrentUser(false);

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
		String query = "SELECT  t.id, t.user_id, t.post_datetime, t.content,  u.username, COUNT(lt.user_id) AS like_count, "+
				"EXISTS (SELECT 1 FROM LikeTweet ltu WHERE ltu.tweet_id = t.id AND ltu.user_id = ?) AS liked_by_current_user "+
				"FROM Tweet t "+
				"INNER JOIN Users u ON t.user_id = u.id "+
				"INNER JOIN FollowUser f ON t.user_id = f.followed_id "+
				"LEFT JOIN LikeTweet lt ON t.id = lt.tweet_id "+
				"WHERE f.user_id = ? "+
				"GROUP BY t.id, u.username, u.picture "+
				"ORDER BY  t.post_datetime DESC "+
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
					tweet.setLikedByCurrentUser(rs.getBoolean("liked_by_current_user"));

					tweets.add(tweet);
				}
				return Optional.of(tweets);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Optional.empty();
	}
}



