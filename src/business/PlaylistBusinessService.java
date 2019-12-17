package business;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;

import beans.Playlist;
import data.DataAccessInterface;

/**
 * @Authors Holland Aucoin and Bryce Schmisseur
 * Description: This is a business service that creates, views, updates, and deletes playlists, as well as retrieve the ID
 */

@Stateless
@Local(PlaylistBusinessInterface.class)
public class PlaylistBusinessService implements PlaylistBusinessInterface{

	@Inject
	private DataAccessInterface<Playlist> dataService;
	
	/**
	 * Method to create a new playlist
	 * @param playlist - Playlist: A full playlist
	 * @return created - Integer: The number of rows affected by the creation
	 */
	@Override
	public int createPlaylist(Playlist playlist) {

		// Call create method in the DataAccessInterface
		int created = dataService.create(playlist);
		return created;
	}
	
	/**
	 * Method to view a single playlist
	 * @return playlist - Playlist: A full playlist
	 */
	@Override
	public Playlist viewPlaylist() {
		
		// Call view method in the DataAccessInterface
		Playlist playlist = dataService.view();
		return playlist;
	}

	/**
	 * Method to view all playlists of a user
	 * @return playlists - List: A full list of all the user's playlists
	 */
	@Override
	public List<Playlist> viewAllPlaylists() {
		
		// Call viewAll method in the DataAccessInterface
		List<Playlist> playlists = dataService.viewAll();
		return playlists;
	}
	
	/**
	 * Method to update a playlist, given the playlist and the corresponding ID
	 * @param playlist - Playlist: A full playlist
	 * @param playlistId - Integer: The ID number of a given playlist
	 * @return updated - Integer: The number of rows affected by the update
	 */
	@Override
	public int updatePlaylist(Playlist playlist, int playlistId) {
		
		// Call update method in the DataAccessInterface
		int updated = dataService.update(playlist, playlistId);
		return updated;
	}

	/**
	 * Method to delete a playlist, given the playlistId
	 * @param playlistId - Integer: The ID number of a given playlist
	 * @return deleted - Integer: The number of rows affected by the deletion
	 */
	@Override
	public int deletePlaylist(int playlistId) {
		
		// Call delete method in the DataAccessInterface
		int deleted = dataService.delete(playlistId);
		return deleted;
	}

	/**
	 * Method to get the playlistID of a given playlist
	 * @param playlist - Playlist: A full playlist
	 * @return playlistId - Integer: The ID number of a given playlist
	 */
	@Override
	public int getPlaylistID(Playlist playlist) {
		
		// Call getID method in the DataAccessInterface
		int playlistId = dataService.getID(playlist);
		return playlistId;
	}

}
