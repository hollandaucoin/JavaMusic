package data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import beans.User;
import exceptions.DatabaseException;

/**
 * @Authors Holland Aucoin and Bryce Schmisseur
 * Description: This is a data service that performs CRUD operations to users
 */

@Stateless
@Local(DataAccessInterface.class)
@LocalBean
public class UserDataService implements DataAccessInterface<User>{
	
	/**
	 * CRUD: This is method to add a user
	 * @param user - User: The user's account information from registering
	 * @return returnNum - Integer: The number of rows affected by the create
	 */
	@Override
	public int create(User user) {
		
		// Create integer to return
		int returnNum;
		
		// Select row with given username
		String sqlCheckUsername = String.format("SELECT * FROM 235_Database.USER_CREDENTIAL_TABLE WHERE USERNAME = '%s'", user.getUsercredentials().getUsername());
		
		Connection conn = null;
		
		try {
			// Connect to the database
			DatabaseConnection connection = new DatabaseConnection();
			conn = connection.getConnection();
			
			// Create statement, execute SQL Query
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sqlCheckUsername);
			
			// If username isn't already in database
			if (rs.next() == false) {
				
				// Insert user credentials
				String sql = String.format("INSERT INTO 235_Database.USER_CREDENTIAL_TABLE(ID, USERNAME, PASSWORD) VALUES (NULL, '%s', '%s')", 
				user.getUsercredentials().getUsername(), user.getUsercredentials().getPassword());
				
				// Execute SQL Query
				stmt.execute(sql);
				
				// Get auto-increment PK back
				String sqlQuery = "SELECT LAST_INSERT_ID() AS LAST_ID FROM USER_CREDENTIAL_TABLE";
				rs = stmt.executeQuery(sqlQuery);
				rs.next();
				int userCredentialId = Integer.parseInt(rs.getString("LAST_ID"));
				
				// Insert user information
				String sqlInsert = String.format("INSERT INTO 235_Database.USER_TABLE(ID, FIRST_NAME, LAST_NAME, EMAIL, PHONE_NUMBER, USER_CREDENTIAL_ID) VALUES (NULL, '%s', '%s', '%s', '%s', %d)", user.getFirstName(), user.getLastName(), 
				user.getEmail(), user.getPhoneNumber(), userCredentialId);
				
				// Execute SQL Query
				stmt.execute(sqlInsert);
				
				// Set returnNum to 1 to show success
				returnNum = 1;
			}
			else {
				// Username is already in database, set returnNum to 0 showing failure
				returnNum = 0;
			}
			
			// Close result set and statement
			rs.close();
			stmt.close();
		} 
		
		// Catch the SQL and connection errors, and print stack trace
		catch (SQLException e) {
			e.printStackTrace();
			
			// Set returnNum to -1 to show error
			returnNum = -1;
			
			// Throw custom exception
			throw new DatabaseException(e);
		}
		
		finally {
			// Cleanup database
			if (conn != null) {
				try {
					conn.close();
				}
				// Catch the SQL and connection errors, print stack trace, and throw custom exception
				catch (SQLException e){
					e.printStackTrace();
					throw new DatabaseException(e);
				}
			}
		}
		// Returning number based on success, failure, or error
		return returnNum;
	}

	/**
	 * CRUD: This is a method to view a user
	 * @return users - List<User>:  A list of all users
	 */
	@Override
	public List<User> viewAll() {
		// Create arraylist
		List<User> users = new ArrayList<User>();
		
		// Select all from user table and user credentials table
		String sql = "SELECT * FROM USER_TABLE INNER JOIN USER_CREDENTIAL_TABLE WHERE USER_TABLE.USER_CREDENTIAL_ID = USER_CREDENTIAL_TABLE.ID";
		
		Connection conn = null;
		
		try {
			// Connect to the database
			DatabaseConnection connection = new DatabaseConnection();
			conn = connection.getConnection();
			
			// Create statement, execute SQL Query
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
				
			// Add users from result set into arraylist
			while (rs.next()) {
				users.add(new User(rs.getString("FIRST_NAME"), 
						rs.getString("LAST_NAME"), 
						rs.getString("PHONE_NUMBER"), 
						rs.getString("EMAIL"), 
						rs.getString("USERNAME"), 
						rs.getString("PASSWORD")));
			}
			
			// Close statement and result set
			rs.close();
			stmt.close();
		} 
		
		// Catch the SQL and connection errors, print stack trace, and throw custom exception
		catch (Exception e) {
			e.printStackTrace();
			throw new DatabaseException(e);
		}
		
		finally {
			// Cleanup database
			if (conn != null) {
				try {
					conn.close();
				}
				// Catch the SQL and connection errors, print stack trace, and throw custom exception
				catch (SQLException e){
					e.printStackTrace();
					throw new DatabaseException(e);
				}
			}
		}
		// Return arralist of users
		return users;
	}
	
	/**
	 * CRUD: This is a method to view a user - ** Would be implemented if there was an account page **
	 * @return user - User: The user's account information from registering
	 */
	@Override
	public User view() {
		
		// Create user to return
		User user = new User();
		
		return user;
	}
	
	/**
	 * CRUD: This is a method to update a user - ** Would be implemented if there was an account page **
	 * @param user - User: The user's account information from registering
	 * @param userId - Integer: The ID of a given user
	 * @return returnNum - Integer: The number of rows affected by the update
	 */
	@Override
	public int update(User user, int userId) {
		
		// Create integer to return
		int returnNum = 0;
		
		return returnNum;
	}
	
	/**
	 * CRUD: This is amethod to delete a user - ** Would be implemented if there was an account page **
	 * @param user - User: The user's account information from registering
	 * @return returnNum - Integer: The number of rows affected by the deletion
	 */
	@Override
	public int delete(int userId) {
		
		// Create integer to return
		int returnNum = 0;
		
		return returnNum;
	}


	/**
	 * Method to return user's ID
	 * @param user - User: The user's account information from registering
	 * @return userId - Integer: The ID of a given user
	 */
	@Override
	public int getID(User user) {
		
		// Create integer for userId to return
		int userId = 0;
		
		// Select row with given username
		String sql = String.format("SELECT ID FROM USER_TABLE WHERE USER_CREDENTIAL_ID = (SELECT ID FROM USER_CREDENTIAL_TABLE WHERE USERNAME = '%s')", user.getUsercredentials().getUsername());
		
		Connection conn = null;
		
		try {
			// Connect to the database
			DatabaseConnection connection = new DatabaseConnection();
			conn = connection.getConnection();
					
			// Create statement, execute SQL Query
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			rs.next();
			 
			// Set userId to ID in result set
			userId = rs.getInt("ID");
			
			// Close statement and result set
			rs.close();
			stmt.close();
		} 
		
		// Catch the SQL and connection errors, and print stack trace
		catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException(e);
		}
		
		finally {
			// Cleanup database
			if (conn != null) {
				try {
					conn.close();
				}
				catch (SQLException e){
					e.printStackTrace();
					throw new DatabaseException(e);
				}
			}
		}
		// Return ID of user
		return userId;
	}

}
