package business;

import java.util.List;

import javax.ejb.Local;

import beans.Song;

/**
 * @Authors Holland Aucoin and Bryce Schmisseur
 * Description: This is a business service interface that reflects a song business service
 */

@Local
public interface SongBusinessInterface {
	
	/**
	 * @see SongBusinessService.addToPlaylist
	 */
	public int addToPlaylist(int songId, int playlistId);
	
	/**
	 * @see SongBusinessService.viewSong
	 */
	public Song viewSong();
	
	/**
	 * @see SongBusinessService.viewAllSongs
	 */
	public List<Song> viewAllSongs();
	
	/**
	 * @see SongBusinessService.viewAllUsersSongs
	 */
	public List<Song> viewAllUsersSongs();
	
	/**
	 * @see SongBusinessService.viewAllPlaylistSongs
	 */
	public List<Song> viewAllPlaylistSongs();
	
	/**
	 * @see songBusinessService.deletesong
	 */
	public int deleteFromPlaylist(int songId, int playlistId);
	
	/**
	 * @see songBusinessService.deleteFromUser
	 */
	public int deleteFromUser(int songId);
	
	/**
	 * @see songBusinessService.getsongID
	 */
	public int getID(Song song);
	
	/**
	 * @see songBusinessService.searchPublicSongs
	 */
	public List<Song> searchPublicSongs();
	
	/**
	 * @see songBusinessService.searchUserSongs
	 */
	public List<Song> searchUserSongs();
	
	/**
	 * @see songBusinessService.searchPlaylistSongs
	 */
	public List<Song> searchPlaylistSongs();
	
	/**
	 * @see songBusinessService.songListRange
	 */
	public List<Song> songListRange(int lowID, int highID);
	
}
