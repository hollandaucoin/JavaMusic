package data;

import java.util.List;

import javax.ejb.Local;

/**
 * @Authors Holland Aucoin and Bryce Schmisseur
 * Description: This is a data access interface that reflects the song data service
 */

@Local
public interface SongDataInterface <T>{

	/**
	 * @see SongDataService.addToPlaylist
	 */
	public int addToPlaylist(int songId, int playlistId);
	
	/**
	 * @see SongDataService.viewAllSongs
	 */
	public List <T> viewAllSongs();
	
	/**
	 * @see SongDataService.viewUsersAllSongs
	 */
	public List <T> viewAllUsersSongs();
	
	/**
	 * @see SongDataService.viewAllPlaylistSongs
	 */
	public List <T> viewAllPlaylistSongs();
	
	/**
	 * @see SongDataService.view
	 */
	public T viewSong();
	
	/**
	 * @see SongDataService.delete
	 */
	public int deleteFromPlaylist(int songId, int playlistId);
	
	/**
	 * @see SongDataService.deleteFromUser
	 */
	public int deleteFromUser(int songId);
	
	/**
	 * @see SongDataService.getID
	 */
	public int getID(T t);
	
	/**
	 * 
	 * @see SongDataService.searchPublicSongs
	 */
	public List<T> searchPublicSongs(String str);
	
	/**
	 * 
	 * @see SongDataService.searchUsersSongs
	 */
	public List<T> searchUsersSongs(String str);
	
	/**
	 * 
	 * @see SongDataService.searchPlaylistSongs
	 */
	public List<T> searchPlaylistSongs(String str);

	/**
	 * @see SongDataService.songRange
	 */
	public List<T> songRange(int lowID, int highID);

}
