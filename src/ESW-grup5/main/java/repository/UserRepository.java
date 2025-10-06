package repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import model.Polis;
import model.Tweet;
import model.User;

public class UserRepository extends BaseRepository {
	
	public boolean existsByUsername(String username) {

        String query = "SELECT COUNT(*) " +
                       "FROM Users u " +
                       "WHERE u.username = ?";

		try (PreparedStatement statement = db.prepareStatement(query)) {
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

        String query = "SELECT u.*, p.name as polis_name, p.id as polis_id " +
                        "FROM Users u " +
                        "JOIN Polis p ON u.polis_id = p.id " +
                        "WHERE u.username=? AND u.password=?;";

        try (PreparedStatement statement = db.prepareStatement(query)) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    user.setId(rs.getInt("id"));
                    user.setUsername(rs.getString("username"));
                    user.setPassword(rs.getString("password"));
                    user.setEmail(rs.getString("email"));
                    user.setGender(rs.getString("gender"));
                    user.setBirthdate(rs.getDate("birthday"));
                    user.setPicture(rs.getString("picture"));
                    user.setSocialCredit(rs.getInt("socialCredit"));

                    // polis
                    Polis polis = new Polis();
                    polis.setId(rs.getInt("polis_id"));
                    polis.setName(rs.getString("polis_name"));
                    user.setPolis(polis);

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
        String query = "INSERT INTO Users (username, password, email, gender, birthday, is_admin, polis_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        if (user.getPicture() != null) {
            query = "INSERT INTO Users (username, password, email, gender, birthday, is_admin, polis_id, picture) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        }

        try (PreparedStatement statement = db.prepareStatement(query)) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getEmail() != null ? user.getEmail() : "");
            statement.setString(4, user.getGender());
            statement.setDate(5, user.getBirthdate());
            statement.setBoolean(6, false); // todo: modify this to set the correct value
            statement.setInt(7, user.getPolis() != null ? user.getPolis().getId() : 1); // Default to ID 1 if null

            if (user.getPicture() != null) {
                statement.setString(8, user.getPicture());
            }

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Update an existing user in the database
    // TODO: handle the picture update
    public void update(User user) {
        String query = "UPDATE Users " +
                        "SET username = ?, password = ?, email = ?, gender = ?, birthday = ?, polis_id = ? " +
                        "WHERE id = ?";

        try (PreparedStatement statement = db.prepareStatement(query)) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getGender());
            statement.setDate(5, user.getBirthdate());
            statement.setInt(6, user.getPolis().getId());
            //statement.setString(7, user.getPicture());
            statement.setInt(7, user.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
	
	// Find a user by their name
    public Optional<User> findByUsername(String name) {
        String query = "SELECT u.*, p.name as polis_name " +
                       "FROM Users u " +
                       "JOIN Polis p ON u.polis_id = p.id " +
                       "WHERE u.username = ?";

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
                user.setSocialCredit(rs.getInt("socialCredit"));


                // polis
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

    // Find a user by their ID
    public Optional<User> findById(Integer userId) {
        String query = "SELECT u.*, p.name as polis_name " +
                "FROM Users u " +
                "JOIN Polis p ON u.polis_id = p.id " +
                "WHERE u.id = ?";

        try (PreparedStatement statement = db.prepareStatement(query)) {
            statement.setInt(1, userId);
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
                user.setSocialCredit(rs.getInt("socialCredit"));


                // polis
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
                        "FROM Users u " +
                        "JOIN Polis p ON u.polis_id = p.id";

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
    
 // Follow a user
 	public void followUser(Integer uid, Integer fid) {
 		String query = "INSERT INTO FollowUser (user_id, followed_id) " +
                       "VALUES (?, ?)";

 		try (PreparedStatement statement = db.prepareStatement(query)) {
 			statement.setInt(1, uid);
 			statement.setInt(2, fid);
 			statement.executeUpdate();
 		} catch (SQLException e) {
 			e.printStackTrace();
 		}
 	}
 // Unfollow a user
 	public void unfollowUser(Integer uid, Integer fid) {
 		String query = "DELETE FROM FollowUser " +
                       "WHERE user_id = ? AND followed_id = ?";

 		try (PreparedStatement statement = db.prepareStatement(query)) {
 			statement.setInt(1, uid);
 			statement.setInt(2, fid);
 			statement.executeUpdate();
 		} catch (SQLException e) {
 			e.printStackTrace();
 		}
 	} 
    public Optional<List<User>> findFollowed(Integer id, Integer start, Integer end) {
		String query = "SELECT u.id, u.username ,u.picture " +
                       "FROM Users u, FollowUser f " +
                       "WHERE u.id = f.followed_id AND f.user_id = ? " +
                       "ORDER BY u.username LIMIT ?,?;";


		try (PreparedStatement statement = db.prepareStatement(query)) {
			statement.setInt(1, id);
			statement.setInt(2, start);
			statement.setInt(3, end);
			try (ResultSet rs = statement.executeQuery()) {
				List<User> users = new ArrayList<User>();
				while (rs.next()) {
					User user = new User();
					user.setId(rs.getInt("id"));
					user.setUsername(rs.getString("username"));
					user.setPicture(rs.getString("picture"));
					users.add(user);
				}
				return Optional.of(users);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Optional.empty();
	}
    
    //followers user
    public Optional<List<User>> findFollowers(Integer id, Integer start, Integer end) {
        String query = "SELECT u.id, u.username, u.picture " +
                       "FROM Users u, FollowUser f " +
                       "WHERE u.id = f.user_id AND f.followed_id = ? " +
                       "ORDER BY u.username LIMIT ?, ?;";

        try (PreparedStatement statement = db.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.setInt(2, start);
            statement.setInt(3, end);

            try (ResultSet rs = statement.executeQuery()) {
                List<User> users = new ArrayList<>();
                while (rs.next()) {
                    User user = new User();
                    user.setId(rs.getInt("id"));
                    user.setUsername(rs.getString("username"));
                    user.setPicture(rs.getString("picture"));
                    users.add(user);
                }
                return Optional.of(users);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    //Numero de seguidores
    public int countFollowers(Integer id) {
        String query = "SELECT COUNT(*) AS total FROM FollowUser WHERE followed_id = ?";

        try (PreparedStatement statement = db.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("total");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

  //Numero de siguiendo
    public int countFollowing(Integer id) {
        String query = "SELECT COUNT(*) AS total FROM FollowUser WHERE user_id = ?";

        try (PreparedStatement statement = db.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("total");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    public Optional<List<User>> findNotFollowed(Integer id, Integer start, Integer end) {
		String query = "SELECT id,username,picture FROM Users WHERE id NOT IN (SELECT id FROM Users,FollowUser WHERE id = followed_id AND user_id = ?) AND id <> ? ORDER BY username LIMIT ?,?;";
		try (PreparedStatement statement = db.prepareStatement(query)) {
			statement.setInt(1, id);
			statement.setInt(2, id);
			statement.setInt(3, start);
			statement.setInt(4, end);
			try (ResultSet rs = statement.executeQuery()) {
				List<User> users = new ArrayList<User>();
				while (rs.next()) {
					User user = new User();
					user.setId(rs.getInt("id"));
					user.setUsername(rs.getString("username"));
					user.setPicture(rs.getString("picture"));
					users.add(user);
				}
				return Optional.of(users);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Optional.empty();
	}

    
    public Optional<List<User>> getMostPopularUsers(){
		String query = "SELECT * FROM Users ORDER BY socialCredit DESC LIMIT 20;";
		try (PreparedStatement statement = db.prepareStatement(query)) {
			try (ResultSet rs = statement.executeQuery()) {
				List<User> users = new ArrayList<User>();
				while (rs.next()) {
					User user = new User();
					user.setId(rs.getInt("id"));
					user.setUsername(rs.getString("username"));
					user.setPicture(rs.getString("picture"));
					users.add(user);
				}
				return Optional.of(users);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Optional.empty();
    }
    
    public boolean isUserAdmin(String username) {
        String query = "SELECT is_admin FROM Users WHERE username = ?";
        try (PreparedStatement statement = db.prepareStatement(query)) {
            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();
            
            // Verifica si hay resultados antes de acceder
            if (rs.next()) {
                return rs.getBoolean("is_admin");
            } else {
                return false; // Usuario no encontrado
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    /*delete user from username*/
    public void deleteUserFromUsername(String username) {
        String query = "DELETE FROM Users WHERE username = ?";
        try (PreparedStatement statement = db.prepareStatement(query)) {
            statement.setString(1, username);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}