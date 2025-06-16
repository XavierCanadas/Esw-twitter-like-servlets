package repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import model.Polis;

public class PolisRepository extends BaseRepository {

    public List<Polis> findAll() {
        List<Polis> polisList = new ArrayList<>();
        String query = "SELECT id, name FROM Polis";

        try (PreparedStatement statement = db.prepareStatement(query)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Polis polis = new Polis();
                polis.setId(rs.getInt("id"));
                polis.setName(rs.getString("name"));
                polisList.add(polis);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return polisList;
    }

    public Optional<Polis> findById(int id) {
        String query = "SELECT id, name " +
                       "FROM Polis " +
                       "WHERE id = ?";

        try (PreparedStatement statement = db.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                Polis polis = new Polis();
                polis.setId(rs.getInt("id"));
                polis.setName(rs.getString("name"));
                return Optional.of(polis);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

}