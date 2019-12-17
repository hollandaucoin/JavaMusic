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

import beans.Song;
import exceptions.DatabaseException;

/**
 * @Authors Holland Aucoin and Bryce Schmisseur
 * Description: This is a data service that views, adds, deletes, and searches songs, as well as retrieve the a song's ID
 */

@Stateless
@Local(SongDataInterface.class)
@LocalBean
public class SongDataService implements SongDataInterface<Song>{
	
	/**
	 * CRUD: This method it to add a song to a playlist
	 * @param songId - Integer: The ID of the given song
	 * @param playlistId - Integer: The ID of the given playlist
	 * @return returnNum - Integer: The number of rows affected by the insert
	 */
	@Override
	public int addToPlaylist(int songId, int playlistId) {
		
		// Create integer to return
		int returnNum;
		
		Connection conn = null;
		
		try {
			// Connect to the database
			DatabaseConnection connection = new DatabaseConnection();
			conn = connection.getConnection();
			
			// Create statement, execute SQL Query
			Statement stmt = conn.createStatement();
			
			// Create session variable
			HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
			
				// Insert playlist information
				String sql = String.format("INSERT INTO `PLAYLISTSONGS_TABLE` (`SONG_TABLE_ID`, `PLAYLIST_TABLE_ID`, `USER_TABLE_ID`) "
						+ "VALUES (%d, %d, %d)", songId, playlistId, session.getAttribute("userId"));
				
				// Execute SQL Query
				stmt.execute(sql);
				
				// Set returnNum to 1 to show success
				returnNum = 1;
			
			// Close result set and statement
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
	 * CRUD: This method is to view all songs in the database
	 * @return allSongs - List<Song>: A list of all songs in the database
	 */
	@Override
	public List <Song> viewAllSongs() {
		
		// Create arraylist
		List <Song> allSongs = new ArrayList <Song>();
		
		Connection conn = null;
		
		try {
			// Connect to the database
			DatabaseConnection connection = new DatabaseConnection();
			conn = connection.getConnection();
			
			// Create statement
			Statement stmt = conn.createStatement();
			
			// Select all rows in song table
			String sql = String.format("SELECT * FROM SONG_TABLE ORDER BY NAME ASC");
			
			// Executing SQL Query
			ResultSet rs = stmt.executeQuery(sql);
			
			// Add songs from result set into arraylist
			while (rs.next()) {
				allSongs.add(new Song(rs.getString("NAME"), rs.getString("ARTIST"), rs.getString("ALBUM"), rs.getString("COVER_PHOTO")));
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
		return allSongs;
	}
	
	
	/**
	 * CRUD: This method is to view all a user's songs
	 * @return usersSongs - List<Song>: A list of all songs on the user's account
	 */
	@Override
	public List <Song> viewAllUsersSongs() {
		
		// Create arraylist
		List <Song> usersSongs = new ArrayList <Song>();
		
		Connection conn = null;
		
		try {
			// Connect to the database
			DatabaseConnection connection = new DatabaseConnection();
			conn = connection.getConnection();
			
			// Create statement
			Statement stmt = conn.createStatement();
			
			// Create session variable
			HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
			
			// Select all rows in song table
			String sql = String.format("SELECT DISTINCT SONG_TABLE.ID, SONG_TABLE.NAME, SONG_TABLE.ARTIST, SONG_TABLE.ALBUM, SONG_TABLE.COVER_PHOTO FROM "
					+ "SONG_TABLE INNER JOIN PLAYLISTSONGS_TABLE WHERE PLAYLISTSONGS_TABLE.USER_TABLE_ID = %d AND "
					+ "SONG_TABLE_ID = PLAYLISTSONGS_TABLE.SONG_TABLE_ID AND PLAYLISTSONGS_TABLE.SONG_TABLE_ID = SONG_TABLE.ID ORDER BY NAME ASC", session.getAttribute("userId"));
			
			// Executing SQL Query
			ResultSet rs = stmt.executeQuery(sql);
			
			// Add songs from result set into arraylist
			while (rs.next()) {
				usersSongs.add(new Song(rs.getString("NAME"), rs.getString("ARTIST"), rs.getString("ALBUM"), rs.getString("COVER_PHOTO")));
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
		return usersSongs;
	}
	
	
	/**
	 * CRUD: This method is to view all songs in a playlist
	 * @return playlistSongs - List<Song>: A list of all songs in a playlist
	 */
	@Override
	public List <Song> viewAllPlaylistSongs() {
		
		// Create arraylist
		List <Song> playlistSongs = new ArrayList <Song>();
		
		Connection conn = null;
		
		try {
			// Connect to the database
			DatabaseConnection connection = new DatabaseConnection();
			conn = connection.getConnection();
			
			// Create statement
			Statement stmt = conn.createStatement();
			
			// Create session variable
			HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
			
			// Select all rows in song table
			String sql = String.format("SELECT * FROM SONG_TABLE INNER JOIN PLAYLISTSONGS_TABLE WHERE PLAYLISTSONGS_TABLE.PLAYLIST_TABLE_ID = %d "
					+ "AND PLAYLISTSONGS_TABLE.SONG_TABLE_ID = SONG_TABLE.ID ORDER BY NAME ASC", session.getAttribute("currentPlaylistID"));
			
			// Executing SQL Query
			ResultSet rs = stmt.executeQuery(sql);
			
			// Add songs from result set into arraylist
			while (rs.next()) {
				playlistSongs.add(new Song(rs.getString("NAME"), rs.getString("ARTIST"), rs.getString("ALBUM"), rs.getString("COVER_PHOTO")));
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
		return playlistSongs;
	}
	
	
	/**
	 * Method to view a song
	 * @return currentSong - Song: A full song
	 */
	@Override
	public Song viewSong() {
		
		// Create Song variable
		Song currentSong = null;
		
		// Create session variable, get attribute of currentPlaylistID and set it in integer variable
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		int currentSongID = (int) session.getAttribute("currentSongID");
		
		// Select row with given song ID
		String sql = String.format("SELECT * FROM SONG_TABLE WHERE ID = %d", currentSongID);
		
		Connection conn = null;
		
		try {
			// Connect to the database
			DatabaseConnection connection = new DatabaseConnection();
			conn = connection.getConnection();
			
			// Create statement, execute SQL Query
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			// Create new song from result set and set to current song
			while (rs.next()) {
				currentSong = new Song(rs.getString("NAME"), rs.getString("ARTIST"), rs.getString("ALBUM"), rs.getString("COVER_PHOTO"));
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
		// Return the current song
		return currentSong;
	}
	
	
	/**
	 * CRUD: This is a method to delete a song in a playlist
	 * @param songId - Integer: The ID of a given song
	 * @param playlistId - Integer: The ID of a given playlist
	 * @return returnNum - Integer: The number of rows affected by the deletion
	 */
	@Override
	public int deleteFromPlaylist(int songId, int playlistId) {

		// Create integer to return
		int returnNum;
		
		// Select row with given song ID and playlist ID
		String sql = String.format("DELETE FROM PLAYLISTSONGS_TABLE WHERE SONG_TABLE_ID = %d AND PLAYLIST_TABLE_ID = %d", songId, playlistId);
		
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
	 * CRUD: This is a method to delete a song the users library
	 * @param songId - Integer: The ID of a given song
	 * @return returnNum - Integer: The number of rows affected by the deletion
	 */
	@Override
	public int deleteFromUser(int songId) {

		// Create integer to return
		int returnNum;
		
		// Create session variable
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		
		// Select row with given song ID and user ID
		String sql = String.format("DELETE FROM PLAYLISTSONGS_TABLE WHERE SONG_TABLE_ID = %d AND USER_TABLE_ID = %d", songId, session.getAttribute("userId"));

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
	 * Method to get the songId
	 * @param song - Song: A full song
	 * @return songID - Integer: The ID of a given song
	 */
	@Override
	public int getID(Song song) {
		
		// Create integer of songId to return
		int songID = 0;
		
		// Select row with given song name and description
		String sql = String.format("SELECT ID FROM SONG_TABLE WHERE NAME='%s' AND ARTIST='%s'", song.getSongName(), song.getArtist());
		
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
				songID = rs.getInt("ID");
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
		// Return the integer of songID
		return songID;
	}

	/**
	 * Method to search the public songs
	 * @param str - String: The search parameter to check for in the songs
	 * @return searchPublicSongs - List<Song>: A list of songs that contain data matching the search string
	 */
	@Override
	public List<Song> searchPublicSongs(String str) {
		
		// Create arraylist of songs
		List <Song> searchPublicSongs = new ArrayList <Song>();
		String sql;
			
		// Select rows that have data 'like' the search string
		sql = "SELECT * FROM SONG_TABLE WHERE NAME LIKE '%" + str + "%' OR ARTIST LIKE '%" + str + "%' OR ALBUM LIKE '%" + str + "%' ORDER BY NAME ASC";
		
		Connection conn = null;
		
		try {
			// Connect to the database
			DatabaseConnection connection = new DatabaseConnection();
			conn = connection.getConnection();
			
			// Create statement, execute SQL Query
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			// Add songs from result set into arraylist
			while (rs.next()) {
				searchPublicSongs.add(new Song(rs.getString("NAME"), rs.getString("ARTIST"), rs.getString("ALBUM"), rs.getString("COVER_PHOTO")));
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
		// Return arraylist of songs
		return searchPublicSongs;
		}
	
	/**
	 * Method to search the users songs
	 * @param str - String: The search parameter to check for in the songs
	 * @return searchUsersSongs - List<Song>: A list of songs that contain data matching the search string and in the user's library
	 */
	@Override
	public List<Song> searchUsersSongs(String str) {
		
		// Create arraylist of songs
		List <Song> searchUsersSongs = new ArrayList <Song>();
		String sql;
		
		// Create session variable, get attribute of currentPlaylistID and set it in integer variable
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		int userId = (int) session.getAttribute("userId");
			
		// Select rows that have data 'like' the search string
		sql = "SELECT DISTINCT SONG_TABLE.ID, SONG_TABLE.NAME, SONG_TABLE.ARTIST, SONG_TABLE.ALBUM, SONG_TABLE.COVER_PHOTO FROM SONG_TABLE "
				+ "INNER JOIN PLAYLISTSONGS_TABLE WHERE USER_TABLE_ID=" + userId + " AND SONG_TABLE_ID = SONG_TABLE.ID AND "
				+ "(NAME LIKE '%" + str + "%' OR ALBUM LIKE '%" + str + "%' OR ARTIST LIKE '%" + str + "%') ORDER BY NAME ASC";
		
		Connection conn = null;
		
		try {
			// Connect to the database
			DatabaseConnection connection = new DatabaseConnection();
			conn = connection.getConnection();
			
			// Create statement, execute SQL Query
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			// Add songs from result set into arraylist
			while (rs.next()) {
				searchUsersSongs.add(new Song(rs.getString("NAME"), rs.getString("ARTIST"), rs.getString("ALBUM"), rs.getString("COVER_PHOTO")));
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
		// Return arraylist of songs
		return searchUsersSongs;
		
	}

	/**
	 * Method to search a playlists songs
	 * @param str - String: The search parameter to check for in the songs
	 * @return searchPlaylistSongs - List<Song>: A list of songs that contain data matching the search string and in the playlist
	 */
	@Override
	public List<Song> searchPlaylistSongs(String str) {
		
		// Create arraylist of songs
		List <Song> searchPlaylistSongs = new ArrayList <Song>();
		String sql;
		
		// Create session variable, get attribute of currentPlaylistID and set it in integer variable
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		int playlistID = (int) session.getAttribute("currentPlaylistID");
			
		// Select rows with given playlist ID and that have data 'like' the search string
		sql = "SELECT * FROM SONG_TABLE INNER JOIN PLAYLISTSONGS_TABLE WHERE PLAYLIST_TABLE_ID=" + playlistID + 
				" AND SONG_TABLE_ID = SONG_TABLE.ID AND (NAME LIKE '%" + str + "%' OR ALBUM LIKE '%" + str + "%' OR ARTIST LIKE '%" + str + "%') ORDER BY NAME ASC";
		
		Connection conn = null;
		
		try {
			// Connect to the database
			DatabaseConnection connection = new DatabaseConnection();
			conn = connection.getConnection();
			
			// Create statement, execute SQL Query
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			// Add songs from result set into arraylist
			while (rs.next()) {
				searchPlaylistSongs.add(new Song(rs.getString("NAME"), rs.getString("ARTIST"), rs.getString("ALBUM"), rs.getString("COVER_PHOTO")));
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
				catch (SQLException e) {
					e.printStackTrace();
					throw new DatabaseException(e);
				}
			}
		}
		// Return arraylist of songs
		return searchPlaylistSongs;
	}

	@Override
	public List<Song> songRange(int low, int high) {
		
		// Create arraylist
		List <Song> songRange = new ArrayList <Song>();
		
		Connection conn = null;
		
		try {
			// Connect to the database
			DatabaseConnection connection = new DatabaseConnection();
			conn = connection.getConnection();
			
			// Create statement
			Statement stmt = conn.createStatement();
			
			// Select all rows in song table between the integers
			String sql = String.format("SELECT * FROM SONG_TABLE WHERE ID BETWEEN " + low + " AND " + high);
			
			// Executing SQL Query
			ResultSet rs = stmt.executeQuery(sql);
			
			// Add songs from result set into arraylist
			while (rs.next()) {
				songRange.add(new Song(rs.getString("NAME"), rs.getString("ARTIST"), rs.getString("ALBUM"), rs.getString("COVER_PHOTO")));
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
		// Return arraylist of songs
		return songRange;
		
	}
}
