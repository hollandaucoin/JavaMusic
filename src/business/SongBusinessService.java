package business;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import beans.Song;
import data.SongDataInterface;

/**
 * @Authors Holland Aucoin and Bryce Schmisseur
 * Description: This is a business service that adds, views, deletes, and searches for songs, as well as retrieve the ID
 */

@Stateless
@Local(SongBusinessInterface.class)
public class SongBusinessService implements SongBusinessInterface{

	@Inject
	private SongDataInterface<Song> dataService;

	/**
	 * Method to add a song to a playlist
	 * @return songAdded - Integer: The number of rows effected by an add
	 */
	@Override
	public int addToPlaylist(int songId, int playlistId) {

		// Call getSongID method in the SongDataAccessInterface
		int songAdded = dataService.addToPlaylist(songId, playlistId);
		return songAdded;
	}
	
	/**
	 * Method to view a single song
	 * @return song - Song: A full song
	 */
	@Override
	public Song viewSong() {
		
		// Call viewSong method in the SongDataAccessInterface
		Song song = dataService.viewSong();
		return song;
	}
	
	/**
	 * Method to view all songs in the database
	 * @return allSongs - List<Song>: A full list of all songs
	 */
	@Override
	public List<Song> viewAllSongs() {
		
		// Call viewAllSongs method in the SongDataAccessInterface
		List<Song> allSongs = dataService.viewAllSongs();
		return allSongs;
	}
	
	/**
	 * Method to view all songs of a certain user
	 * @return usersSongs - List<Song>: A full list of all songs of a user
	 */
	@Override
	public List<Song> viewAllUsersSongs() {
		
		// Call viewAllUsersSongs method in the SongDataAccessInterface
		List<Song> usersSongs = dataService.viewAllUsersSongs();
		return usersSongs;
	}
	
	/**
	 * Method to view all songs in a certain playlist
	 * @return playlistSongs - List<Song>: A full list of all songs in a playlist
	 */
	@Override
	public List<Song> viewAllPlaylistSongs() {
		
		// Call viewAllPlaylistSongs method in the SongDataAccessInterface
		List<Song> playlistSongs = dataService.viewAllPlaylistSongs();
		return playlistSongs;
	}
	
	/**
	 * Method to delete a song from a playlist, given the songId and playlistId
	 * @param songId - Integer: The ID number of a given song
	 * @param playlistId - Integer: The ID number of a given playlist
	 * @return deleted - Integer: The number of rows affected by the deletion
	 */
	@Override
	public int deleteFromPlaylist(int songId, int playlistId) {
		
		// Call deleteFromPlaylist method in the SongDataAccessInterface
		int deleted = dataService.deleteFromPlaylist(songId, playlistId);
		return deleted;
	}
	
	/**
	 * Method to delete a song from the user library, given the songId and userId
	 * @param songId - Integer: The ID number of a given song
	 * @return deleted - Integer: The number of rows affected by the deletion
	 */
	@Override
	public int deleteFromUser(int songId) {
		
		// Call deleteFromUser method in the SongDataAccessInterface
		int deleted = dataService.deleteFromUser(songId);
		return deleted;
	}

	/**
	 * Method to get the songID of a given song
	 * @param song - Song: A full song
	 * @return songId - Integer: The ID number of a given song
	 */
	@Override
	public int getID(Song song) {
		
		// Call getID method in the SongDataAccessInterface
		int songId = dataService.getID(song);
		return songId;
	}

	/**
	 * Method to search the list of public songs
	 * @return publicSearch - List<Song>: A list of all songs in the public library matching the search parameter
	 */
	@Override
	public List<Song> searchPublicSongs() {
		
		// Create session variable for the search parameter
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		
		// Call searchPublicSongs method in the SongDataAccessInterface
		List<Song> publicSearch = dataService.searchPublicSongs((String) session.getAttribute("searchParam"));
		return publicSearch;
	}

	/**
	 * Method to search the list of a user's songs
	 * @return userSearch - List<Song>: A list of all songs in a user's library matching the search parameter
	 */
	@Override
	public List<Song> searchUserSongs() {
		
		// Create session variable for the search parameter
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		
		// Call searchUsersSongs method in the SongDataAccessInterface
		List<Song> userSearch = dataService.searchUsersSongs((String) session.getAttribute("searchParam"));
		return userSearch;
	}

	/**
	 * Method to search the list of songs within a playlist
	 * @return playlistSearch - List<Song>: A list of all songs in a playlist matching the search parameter
	 */
	@Override
	public List<Song> searchPlaylistSongs() {
		
		// Create session variable for the search parameter
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		
		List<Song> playlistSearch = dataService.searchPlaylistSongs((String) session.getAttribute("searchParam"));
		return playlistSearch;
	}

	/**
	 * Method to get a list of songs in a given range of the database
	 * @return songRange - List<Song>: A list of songs with IDs between two integers
	 */
	@Override
	public List<Song> songListRange(int lowID, int highID) {
		
		// Call songRange method in the SongDataAccessInterface
		List<Song> songRange = dataService.songRange(lowID, highID);
		return songRange;
	}

}
