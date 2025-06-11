package repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import model.Tweet;

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
		String query = "SELECT t.id, t.user_id, t.post_datetime, t.content, u.username " +
						"FROM Tweet t " +
						"INNER JOIN Users u ON t.user_id = u.id " +
						"WHERE t.user_id = ? " +
						"ORDER BY t.post_datetime DESC " +
						"LIMIT ?,?;";

		try (PreparedStatement statement = db.prepareStatement(query)) {
			statement.setInt(1, uid);
			statement.setInt(2, start);
			statement.setInt(3, end);
			try (ResultSet rs = statement.executeQuery()) {
				while (rs.next()) {
					Tweet tweet = new Tweet();
					tweet.setId(rs.getInt("id"));
					tweet.setUid(rs.getInt("user_id"));
					tweet.setPostDateTime(rs.getTimestamp("post_datetime"));
					tweet.setContent(rs.getString("content"));
					tweet.setUname(rs.getString("username"));
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
