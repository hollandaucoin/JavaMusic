package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import exceptions.DatabaseException;

/**
 * @Authors Holland Aucoin and Bryce Schmisseur
 * Description: This is a class to create the database connection
 */

public class DatabaseConnection {

	/**
	 * Method to connect to the database
	 * @return conn - Connection
	 */
	public Connection getConnection() {
		
		// Strings for url, username, and password for database connection
		String url = "jdbc:mysql://localhost:3306/235_Database?useSSL=false";
		String username = "root";
		String password = "root";
		
		Connection conn = null;
		
		try {
			// Connect to the database
			conn = DriverManager.getConnection(url, username, password);
		} 
		// Catch the SQL and connection errors, and print stack trace
		catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException(e);
		}
		// Return the connection
		return conn;
	}
	
}
