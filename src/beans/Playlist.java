package beans;

import java.util.ArrayList;


import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @Authors Holland Aucoin and Bryce Schmisseur
 * Description: This is a model that stores the a playlist's information, with getters and setters to access that information
 */

@ManagedBean
@ViewScoped
public class Playlist {
	
	// Create list of songs called playlist
	List<Song> playlist = new ArrayList<Song>();
	
	// Variable to store the playlist name. Required and has size limit
	@NotNull (message = "Please enter a playlist name.")
	@Size (min=4, max=45)
	private	String playlistName;
	
	// Variable to store the playlist description. Required and has size limit
	@NotNull (message = "Please enter a playlist description.")
	@Size (min=4, max=240)
	private String description;
	
	/**
	 * Constructor for a playlist
	 * @param playlistName - String: The name of a specific playlist
	 * @param description - String: The description of a specific playlist 
	 */
	public Playlist(String playlistName, String description) {
		this.playlistName = playlistName;
		this.description = description;
	}
	
	/**
	 * Empty Constructor for a playlist
	 */
	public Playlist() {
		
	}

	/**
	 * Getter for playlist
	 * @return playlist - List<Song>: A full playlist will be returned
	 */
	public List<Song> getPlaylist() {
		return playlist;
	}

	/**
	 * Setter for playlist
	 * @param playlist - List<Song>: A full playlist
	 */
	public void setPlaylist(List<Song> playlist) {
		this.playlist = playlist;
	}
	
	
	/**
	 * Getter for playlist name
	 * @return playlistName - String: The name of a specific playlist
	 */
	public String getPlaylistName() {
		return playlistName;
	}

	/**
	 * Setter for playlist name
	 * @param playlistName - String: The name of a specific playlist
	 */
	public void setPlaylistName(String playlistName) {
		this.playlistName = playlistName;
	}

	/**
	 * Getter for playlist description
	 * @return description - String: The description of a specific playlist
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Setter for playlist description
	 * @param description - String: The description of a specific playlist
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	
	
}
