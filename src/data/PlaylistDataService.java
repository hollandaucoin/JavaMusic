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
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import beans.Playlist;
import exceptions.DatabaseException;

/**
 * @Authors Holland Aucoin and Bryce Schmisseur
 * Description: This is a data service that performs CRUD operations to playlists
 */

@Stateless
@Local(DataAccessInterface.class)
@LocalBean
public class PlaylistDataService implements DataAccessInterface<Playlist>{
	
	/**
	 * CRUD: Method to add a playlist
	 * @param playlist - Playlist: A full playlist
	 * @return returnNum - Integer: The number of rows affected by the create
	 */
	@Override
	public int create(Playlist playlist) {
		
		// Create integer to return
		int returnNum;

		// Select row with given playlist name (if any)
		String sqlCheckPlaylist = String.format("SELECT * FROM 235_Database.PLAYLIST_TABLE WHERE NAME = '%s'", playlist.getPlaylistName());
		
		Connection conn = null;
		
		try {
			// Connect to the database
			DatabaseConnection connection = new DatabaseConnection();
			conn = connection.getConnection();
			
			// Create statement, execute SQL Query
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sqlCheckPlaylist);
			
			// Create session variable
			HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
			
			// If the playlist name isn't already in database
			if (rs.next() == false) {
				
				// Insert playlist information
				String sql = String.format("INSERT INTO 235_Database.PLAYLIST_TABLE(ID, NAME, DESCRIPTION, USER_TABLE_ID) VALUES (NULL, '%s', '%s', %d)", 
				playlist.getPlaylistName(), playlist.getDescription(), session.getAttribute("userId"));
				
				// Execute SQL Query
				stmt.execute(sql);
				
				// Set returnNum to 1 to show success
				returnNum = 1;
			}
			else {
				// Playlist name is already in database, set returnNum to 0 showing failure
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
	 * CRUD: Method to view all playlists of the user
	 * @return allPlaylists - List<Playlist>: A list of all a user's playlists
	 */
	@Override
	public List <Playlist> viewAll() {
		
		// Create arraylist
		List <Playlist> allPlaylists = new ArrayList <Playlist>();
		String sql;
		
		try {
			// Create session variable, get attribute of userId and set it in integer variable
			HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
			int userId = (int) session.getAttribute("userId");
			
			// Select row with given playlist ID
			sql = String.format("SELECT * FROM PLAYLIST_TABLE WHERE USER_TABLE_ID = %d ORDER BY NAME ASC", userId);
		}
		catch(Exception e) {
			sql = "SELECT * FROM PLAYLIST_TABLE";
		}
		
		Connection conn = null;
		
		try {
			// Connect to the database
			DatabaseConnection connection = new DatabaseConnection();
			conn = connection.getConnection();
			
			// Create statement, execute SQL Query
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			// Add playlists from result set into arraylist
			while (rs.next()) {
				allPlaylists.add(new Playlist(rs.getString("NAME"), rs.getString("DESCRIPTION")));
			}
			
			// Close statement
			stmt.close();
		} 
		
		// Catch the SQL and connection errors, print stack trace, and throw custom exception
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
				// Catch the SQL and connection errors, print stack trace, and throw custom exception
				catch (SQLException e){
					e.printStackTrace();
					throw new DatabaseException(e);
				}
			}
		}
		// Return arraylist of playlists
		return allPlaylists;
	}
	
	/**
	 * CRUD: Method to update a playlist
	 * @param playlist - Playlist: A full playlist
	 * @param playlistId - Integer: The ID of a given playlist
	 * @return returnNum - Integer: The number of rows affected by the update
	 */
	@Override
	public int update(Playlist playlist, int playlistId) {
		
		// Create integer to return
		int returnNum;
	
		// Select row with given playlist ID
		String sql = String.format("UPDATE PLAYLIST_TABLE SET NAME = '%s', DESCRIPTION = '%s' WHERE ID = %d", 
				playlist.getPlaylistName(), playlist.getDescription(), playlistId);
		
		Connection conn = null;
		
		try {
			// Connect to the database
			DatabaseConnection connection = new DatabaseConnection();
			conn = connection.getConnection();
			
			// Create statement, execute SQL Query, setting returnNum to the number of rows affected
			Statement stmt = conn.createStatement();
			returnNum = stmt.executeUpdate(sql);
			
			// Close statement
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
	 * CRUD: Method to delete a playlist
	 * @param playlistId - Integer: The ID of a given playlist
	 * @return returnNum - Integer: The number of rows affected by the deletion
	 */
	@Override
	public int delete(int playlistId) {

		// Create integer to return
		int returnNum;
		
		// Select row with given playlist ID
		String sql = String.format("DELETE FROM PLAYLIST_TABLE WHERE ID = %d", playlistId);
		String sqlDeleteAll = String.format("DELETE FROM PLAYLISTSONGS_TABLE WHERE PLAYLIST_TABLE_ID = %d", playlistId);
		
		Connection conn = null;
		
		try {
			// Connect to the database
			DatabaseConnection connection = new DatabaseConnection();
			conn = connection.getConnection();
			
			// Create statement, execute SQL Querys to delete playlist and all songs within it, setting returnNum to the number of rows affected
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(sqlDeleteAll);
			returnNum = stmt.executeUpdate(sql);
			
			// Close statement
			stmt.close();
		} 
		
		// Catch the SQL and connection errors, and print stack trace
		catch (SQLException e) {
			e.printStackTrace();
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
	 * Method to get the playlistId
	 * @param playlist - Playlist: A full playlist
	 * @return playlistID - Integer: The ID of a given playlist
	 */
	@Override
	public int getID(Playlist playlist) {
		
		// Create integer of playlistId to return
		int playlistID = 0;
		
		// Select row with given playlist name and description
		String sql = String.format("SELECT ID FROM PLAYLIST_TABLE WHERE NAME='%s' AND DESCRIPTION='%s'", playlist.getPlaylistName(), playlist.getDescription());
		
		Connection conn = null;
		
		try {	
			
			// Connect to the database
			DatabaseConnection connection = new DatabaseConnection();
			conn = connection.getConnection();
			
			// Create statement, execute SQL Query
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			// Go through result set getting the ID
			while (rs.next()) {
				playlistID = rs.getInt("ID");
			}
			
			// Close statement and result set
			stmt.close();
			rs.close();
		}
		
		// Catch the SQL and connection errors, print stack trace, and throw custom exception
		catch(SQLException e) {
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
		// Return the integer of playlistID
		return playlistID;
	}

	
	/**
	 * Method to view a playlist
	 * @return currentPlaylist - Playlist: A full playlist
	 */
	@Override
	public Playlist view() {
		
		// Create Playlist variable
		Playlist currentPlaylist = null;
		
		// Create session variable, get attribute of currentPlaylistID and set it in integer variable
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		int currentPlaylistID = (int) session.getAttribute("currentPlaylistID");
		
		// Select row with given playlist ID
		String sql = String.format("SELECT * FROM PLAYLIST_TABLE WHERE ID = %d", currentPlaylistID);
		
		Connection conn = null;
		
		try {
			// Connect to the database
			DatabaseConnection connection = new DatabaseConnection();
			conn = connection.getConnection();
			
			// Create statement, execute SQL Query
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			// Create new playlist from result set and set to current playlist
			while (rs.next()) {
				currentPlaylist = new Playlist(rs.getString("NAME"), rs.getString("DESCRIPTION"));
			}
			
			// Close statement
			stmt.close();
		} 
		
		// Catch the SQL and connection errors, print stack trace, and throw custom exception
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
				// Catch the SQL and connection errors, print stack trace, and throw custom exception
				catch (SQLException e){
					e.printStackTrace();
					throw new DatabaseException(e);
				}
			}
		}
		// Return the current playlist
		return currentPlaylist;
	}
}
