package dbConnector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnector {
	private Connection connection;

	public DbConnector() {
//		connect to database on initialization
		this.connection = connect();
	}

//	getter for the connection
	public Connection getConnection() {
		return connection;
	}


//	connect to database
	public Connection connect() {
		SECRET secret = new SECRET();
		try {
			connection = DriverManager.getConnection(secret.getUrl(), secret.getUser(), secret.getPassword());
			connection.setAutoCommit(false);
			System.out.printf("%n%s","Database connection established successfully!");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}


//	disconnect from database
	public void disConnect() {
		try {
			connection.close();
			System.out.printf("%n%s", "Database connection closed successfully!");
		} catch (SQLException e) {
			System.out.printf("%n%s", "ERROR occurred while closing database connection!");
			e.printStackTrace();
		} catch (NullPointerException e){
			System.out.printf("%n%s", "No database connection to close found!");
			e.getMessage();
			e.printStackTrace();
		}
	}
}
