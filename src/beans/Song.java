package beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 * @Authors Holland Aucoin and Bryce Schmisseur
 * Description: This is a model that stores a songs information, with getters and setters to access that information
 */

@ManagedBean
@ViewScoped
public class Song {

	// Variable to store the attributes of a song, set to empty
	String songName = "";
	String artist = "";
	String album = "";
	String coverPhoto = "";
	
	/**
	 * Constructor for a song
	 * @param songName - String: The name of a song
	 * @param artist  - String: The name of the artist of a song
	 * @param album  - String: The album name that a song belongs to
	 * @param coverPhoto  - String: The jpg name of the cover photo of a song
	 */
	public Song(String songName, String artist, String album, String coverPhoto) {
		this.songName = songName;
		this.artist = artist;
		this.album = album;
		this.coverPhoto = coverPhoto;
	}
	
	/**
	 * Empty constructor for a song
	 */
	public Song() {
		this.songName = "";
		this.artist = "";
		this.album = "";
		this.coverPhoto = "";
	}

	/**
	 * Getter for a song name
	 * @return songName - String: The name of a specific song
	 */
	public String getSongName() {
		return songName;
	}

	/**
	 * Setter for a song name
	 * @param songName - String: The name of a specific song
	 */
	public void setSongName(String songName) {
		this.songName = songName;
	}

	/**
	 * Getter for an artist
	 * @return artist - String: The artist of a specific song
	 */
	public String getArtist() {
		return artist;
	}

	/**
	 * Setter for an artist
	 * @param artist - String: The artist of a specific song
	 */
	public void setArtist(String artist) {
		this.artist = artist;
	}

	/**
	 * Getter for an album
	 * @return album - String: The album name of a specific song
	 */
	public String getAlbum() {
		return album;
	}

	/**
	 * Setter for an album
	 * @param album - String: The album of a specific song
	 */
	public void setAlbum(String album) {
		this.album = album;
	}

	/**
	 * Getter for a cover photo
	 * @return coverPhoto - String: The cover photo of a specific song
	 */
	public String getCoverPhoto() {
		return coverPhoto;
	}

	/**
	 * Setter for a cover photo
	 * @param coverPhoto - String: The cover photo of a specific song
	 */
	public void setCoverPhoto(String coverPhoto) {
		this.coverPhoto = coverPhoto;
	}
	
}
