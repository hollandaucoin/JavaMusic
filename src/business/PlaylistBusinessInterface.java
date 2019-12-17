package business;

import java.util.List;

import javax.ejb.Local;

import beans.Playlist;

/**
 * @Authors Holland Aucoin and Bryce Schmisseur
 * Description: This is a business service interface that reflects a playlist business service
 */

@Local
public interface PlaylistBusinessInterface {
	
	/**
	 * @see PlaylistBusinessService.create
	 */
	public int createPlaylist(Playlist playlist);
	
	/**
	 * @see PlaylistBusinessService.viewAllPlaylists
	 */
	public List<Playlist> viewAllPlaylists();
	
	/**
	 * @see PlaylistBusinessService.viewPlaylist
	 */
	public Playlist viewPlaylist();
	
	/**
	 * @see PlaylistBusinessService.getPlaylistID
	 */
	public int getPlaylistID(Playlist playlist);
	
	/**
	 * @see PlaylistBusinessService.updatePlaylist
	 */
	public int updatePlaylist(Playlist playlist, int playlistId);
	
	/**
	 * @see PlaylistBusinessService.deletePlaylist
	 */
	public int deletePlaylist(int playlistId);
	
}
