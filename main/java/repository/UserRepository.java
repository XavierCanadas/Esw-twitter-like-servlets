package repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import model.Polis;
import model.User;

public class UserRepository extends BaseRepository {
	
	public boolean existsByUsername(String username) {
		try (PreparedStatement statement = db.prepareStatement("SELECT COUNT(*) FROM users WHERE name = ?")) {
            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // Check if count > 0
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
	}

    public boolean checkLogin(User user) {

        String query = "SELECT id,picture from users where username=? AND password=?";
        try (PreparedStatement statement = db.prepareStatement(query)) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    user.setId(rs.getInt("id"));
                    user.setPicture(rs.getString("picture"));
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;

    }
	
    // Save a new user into the database
    public void save(User user) {
        String query = "INSERT INTO users (username, password, email, gender, birthday, is_admin, picture, polis_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = db.prepareStatement(query)) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getEmail() != null ? user.getEmail() : "");
            statement.setString(4, user.getGender());
            statement.setDate(5, user.getBirthdate());
            statement.setBoolean(6, false); // todo: modify this to set the correct value
            statement.setString(7, user.getPicture() != null ? user.getPicture() : "");
            statement.setInt(8, user.getPolis() != null ? user.getPolis().getId() : 1); // Default to ID 1 if null
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
	
	// Find a user by their name
    public Optional<User> findByName(String name) {
        String query = "SELECT u.id, u.username, u.password, u.email, u.gender, u.birthdayString, u.socialCredit, u.is_admin, u.picture, u.polis_id, p.name as polis_name " +
                "FROM users u JOIN polis p ON u.polis_id = p.id WHERE u.name = ?";
        try (PreparedStatement statement = db.prepareStatement(query)) {
            statement.setString(1, name);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                user.setGender(rs.getString("gender"));
                user.setBirthdate(rs.getDate("birthday"));
                user.setPicture(rs.getString("picture"));

                // todo: implement this
                // user.setSocialCredit(rs.getInt("socialCredit"));
                // user.setAdmin(rs.getBoolean("is_admin"));



                Polis polis = new Polis();
                polis.setId(rs.getInt("polis_id"));
                polis.setName(rs.getString("polis_name"));
                user.setPolis(polis);

                return Optional.of(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
    
    // Retrieve all users from the database
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        String query = "SELECT u.id, u.username, u.password, u.email, u.gender, u.birthday, u.socialCredit, u.is_admin, u.picture, u.polis_id, p.name as polis_name " +
                "FROM users u JOIN polis p ON u.polis_id = p.id";

        try (PreparedStatement statement = db.prepareStatement(query)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                user.setGender(rs.getString("gender"));
                user.setBirthdate(rs.getDate("birthday"));
                user.setPicture(rs.getString("picture"));

                // todo: implement this
                // user.setSocialCredit(rs.getInt("socialCredit"));
                // user.setAdmin(rs.getBoolean("is_admin"));

                Polis polis = new Polis();
                polis.setId(rs.getInt("polis_id"));
                polis.setName(rs.getString("polis_name"));
                user.setPolis(polis);

                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

}