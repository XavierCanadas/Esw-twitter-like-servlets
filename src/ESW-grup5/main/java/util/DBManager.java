package util;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBManager implements AutoCloseable {

    private static final Logger LOGGER = Logger.getLogger(DBManager.class.getName());
	private Connection connection = null;
	
	public DBManager() throws Exception {
        // WITHOUT POOL
        String user = "mysql";
        String password = "prac";
        String db = "epaw";
        String host = System.getenv().getOrDefault("DB_HOST", "localhost");

        String jdbcUrl = "jdbc:mysql://" + host + ":3306/" + db + "?serverTimezone=UTC&user=" + user + "&password=" + password;

        LOGGER.info("Attempting to connect to database: " + jdbcUrl);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            LOGGER.info("MySQL Driver loaded successfully");

            connection = DriverManager.getConnection(jdbcUrl);

            if (connection != null && !connection.isClosed()) {
                LOGGER.info("Database connection established successfully");
                LOGGER.info("Connection details: " + connection.getMetaData().getURL());

            } else {
                LOGGER.severe("Connection is null or closed after initialization");
            }
        } catch (ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, "MySQL Driver not found", e);
            throw e;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to connect to database: " + e.getMessage(), e);
            throw e;
        }
	}
	
	public PreparedStatement prepareStatement(String query) throws SQLException{
		// Note that this is done using https://www.arquitecturajava.com/jdbc-prepared-statement-y-su-manejo/
		return connection.prepareStatement(query);
	}

    @Override
    public void close() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
            LOGGER.info("Database connection closed");
        }
    }


}