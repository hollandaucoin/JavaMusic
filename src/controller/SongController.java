package controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import beans.Search;
import beans.Song;
import business.PlaylistBusinessInterface;
import business.SongBusinessInterface;

/**
 * @Authors Holland Aucoin and Bryce Schmisseur
 * Description: Song Controller to pass the song to the respective view, and navigates appropriately.
 */

@ManagedBean
@ViewScoped
public class SongController {
	
	@Inject
	private SongBusinessInterface service;
	
	@Inject
	private PlaylistBusinessInterface playlistService;

	/**
	 * Method to view a public song
	 * @param song - Song: A full song
	 * @return String (navigates to next view)
	 */
	public String viewPublicSong(Song song) {
		
		try{
			// Create session variable and set attribute of currentSongID
			HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
			session.setAttribute("currentSongID", service.getID(song));
			
			// Putting attribute of song inside the page to view it
			FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("song", service.viewSong());
			
			// Send user to ViewPublicSong
			return "ViewPublicSong.xhtml";
		}
		// Catch exceptions and send to ErrorPage
		catch(Exception e) {
			return "ErrorPage.xhtml";
		}
	}
	
	/**
	 * Method to view a user's song
	 * @param song - Song: A full song
	 * @return String (navigates to next view)
	 */
	public String viewUsersSong(Song song) {
		
		try{
			// Create session variable and set attribute of currentSongID
			HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
			session.setAttribute("currentSongID", service.getID(song));
			
			// Putting attribute of song inside the page to view it
			FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("song", service.viewSong());
			
			// Send user to ViewUsersSong
			return "ViewUsersSong.xhtml";
		}
		// Catch exceptions and send to ErrorPage
		catch(Exception e) {
			return "ErrorPage.xhtml";
		}
	}
	
	/**
	 * Method to view a user's song from a playlist
	 * @param song - Song: A full song
	 * @return String (navigates to next view)
	 */
	public String viewPlaylistSong(Song song) {
		
		try{
			// Create session variable and set attribute of currentSongID
			HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
			session.setAttribute("currentSongID", service.getID(song));
			
			// Putting attribute of song inside the page to view it
			FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("song", service.viewSong());
			
			// Send user to ViewPlaylistSong
			return "ViewPlaylistSong.xhtml";
		}
		// Catch exceptions and send to ErrorPage
		catch(Exception e) {
			return "ErrorPage.xhtml";
		}
	}
	
	
	/**
	 * Method to delete a song from a playlist
	 * @return String (navigates to next view)
	 */
	public String deleteSong() {
		
		try {
			// Create session variable and getting attribute of currentPlaylistID
			HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
			
			// Call deletePlaylist method in the ProductBusinessInterface, passing the retrieved attribute of currentPlaylistID
			service.deleteFromPlaylist((int) session.getAttribute("currentSongID"),(int) session.getAttribute("currentPlaylistID"));

			// Send user to ViewPlaylist
			return "ViewPlaylist.xhtml";
		}
		// Catch exceptions and send to ErrorPage
		catch(Exception e) {
			return "ErrorPage.xhtml";
		}
	}
	
	/**
	 * Method to delete a song from a users library
	 * @return String (navigates to next view)
	 */
	public String deleteSongFromUser() {
		
		try {
			// Create session variable and getting attribute of currentPlaylistID
			HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
			
			// Call deletePlaylist method in the ProductBusinessInterface, passing the retrieved attribute of currentSongID
			service.deleteFromUser((int) session.getAttribute("currentSongID"));
			
			// Send user to SongPage
			return "SongPage.xhtml";
		}
		// Catch exceptions and send to ErrorPage
		catch(Exception e) {
			return "ErrorPage.xhtml";
		}
	}
	
	/**
	 * Method to search for songs
	 * @param input - String: The search parameter being used
	 * @param page - String: The page that the search is occuring on
	 * @return String (navigates to next view)
	 */
	public String searchSongs(Search input, String page){
		
		try {
			// Create session variable and set attribute of searchParam
			HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
			session.setAttribute("searchParam", input.getInput());
			
			// If the page search is occuring on is the PublicLibrary
			if(page.equals("Public")){
				// Putting attribute of song inside the page to view it
				FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("str", input);
				
				// Send user to SearchResultsPublic
				return "SearchResultsPublic.xhtml";
			}
			
			// If the page search is occuring on is the UserLibrary
			else if(page.equals("User")){
				// Putting attribute of song inside the page to view it
				FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("str", input);
				
				// Send user to SearchResultsUser
				return "SearchResultsUser.xhtml";
			}
			
			// If the page search is occuring on is a Playlist
			else if(page.equals("Playlist")){
				// Putting attribute of song inside the page to view it
				FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("str", input);	
				FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("playlist", playlistService.viewPlaylist());
				
				// Send user to SearchResultsPlaylist
				return "SearchResultsPlaylist.xhtml";
			}
			// Else go to ErrorPage
			else {
				return "ErrorPage.xhtml";
			}
		}
		// Catch exceptions and send to ErrorPage
		catch(Exception e) {
			return "ErrorPage.xhtml";
		}
	}
	
	/**
	 * Getter for the songBusinessInterface
	 * @return service - SongBusinessInterface
	 */
	public SongBusinessInterface getService() {
		return service;
	}
	
	/**
	 * Setter for the songBusinessInterface
	 * @param service - SongBusinessInterface
	 */
	public void setService(SongBusinessInterface service) {
		this.service = service;
	}

}
