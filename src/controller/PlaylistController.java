package controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import beans.Playlist;
import business.PlaylistBusinessInterface;
import business.SongBusinessInterface;

/**
 * @Authors Holland Aucoin and Bryce Schmisseur
 * Description: Playlist Controller to pass the playlist to the respective view, and navigates appropriately.
 */

@ManagedBean
@ViewScoped
public class PlaylistController {
	
	@Inject
	private PlaylistBusinessInterface service;

	@Inject
	private SongBusinessInterface songService;
	
	/**
	 * Method to save a playlist
	 * @param playlist - Playlist: A full playlist
	 * @return String (navigates to next View)
	 */
	public String savePlaylist(Playlist playlist) {
		
		try {
			// Call the createPlaylist method in the PlaylistBusinessInterface to write playlist to database
			service.createPlaylist(playlist);
			// Send user to PlaylistPage
			return "PlaylistPage.xhtml";
		}
		// Catch exceptions and send to ErrorPage
		catch(Exception e) {
			return "ErrorPage.xhtml";
		}
	}

	/**
	 * Method to view a playlist
	 * @param playlist - Playlist: A full playlist
	 * @return String (navigates to next view)
	 */
	public String viewPlaylist(Playlist playlist) {
		
		try{
			// Create session variable and set attribute of currentPlaylistID
			HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
			session.setAttribute("currentPlaylistID", service.getPlaylistID(playlist));
			
			// Putting attribute of playlist inside the page to view it
			FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("playlist", service.viewPlaylist());
			
			// Send user to viewPlaylist page
			return "ViewPlaylist.xhtml";
		}
		// Catch exceptions and send to ErrorPage
		catch(Exception e) {
			return "ErrorPage.xhtml";
		}
	}
	
	/**
	 * Method to update a playlist
	 * @param playlist - Playlist: A full playlist
	 * @return String (navigates to next view)
	 */
	public String updatePlaylist(Playlist playlist) {
		
		try{
			// Create session variable and getting attribute of currentPlaylistID
			HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
			
			// Call updatePlaylist method in the PlaylistBusinessInterface, passing the playlist and retrieved attribute of currentPlaylistID
			service.updatePlaylist(playlist, (int) session.getAttribute("currentPlaylistID"));
			
			// Send user to PlaylistPage
			return "PlaylistPage.xhtml";
		}
		// Catch exceptions and send to ErrorPage
		catch(Exception e) {
			return "ErrorPage.xhtml";
		}
	}
	
	/**
	 * Method to fill in the playlist fields of edit page
	 * @return String (navigates to next view)
	 */
	public String fillEditPage() {
		
		try{
			// Putting attribute of playlist inside the page to view it 
			FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("playlist", service.viewPlaylist());
			
			// Send user to EditPlaylistPage
			return "EditPlaylistPage";
		}
		// Catch exceptions and send to ErrorPage
		catch(Exception e) {
			return "ErrorPage.xhtml";
		}
	}
	
	/**
	 * Method to delete a playlist
	 * @return String (navigates to next view)
	 */
	public String deletePlaylist() {
		try {
			// Create session variable and getting attribute of currentPlaylistID
			HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
			
			// Call deletePlaylist method in the PlaylistBusinessInterface, passing the retrieved attribute of currentPlaylistID
			service.deletePlaylist((int) session.getAttribute("currentPlaylistID"));
			
			// Send user to PlaylistPage
			return "PlaylistPage.xhtml";
		}
		// Catch exceptions and send to ErrorPage
		catch(Exception e) {
			return "ErrorPage.xhtml";
		}
	}
	
	/**
	 * Method to view a playlist
	 * @param playlist - Playlist: A full playlist
	 * @return String (navigates to next view)
	 */
	public String selectPlaylist(Playlist playlist) {
		
		try{
			// Create session variable and set attribute of currentPlaylistID
			HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
			session.setAttribute("currentPlaylistID", service.getPlaylistID(playlist));

			// Call addToPlaylist method in the SongBusinessInterface, passing the retrieved attributes of currentSongId and currentPlaylistID
			songService.addToPlaylist((int) session.getAttribute("currentSongID"), (int) session.getAttribute("currentPlaylistID"));

			// Send user to PublicLibrary
			return "PublicLibrary.xhtml";
		}
		// Catch exceptions and send to ErrorPage
		catch(Exception e)
		{
			return "ErrorPage.xhtml";
		}
	}
	
	/**
	 * Getter for the PlaylistBusinessInterface
	 * @param service - PlaylistBusinessInterface
	 */
	public PlaylistBusinessInterface getService() {
		return service;
	}
	
	/**
	 * Setter for the PlaylistBusinessInterface
	 * @return service - PlaylistBusinessInterface
	 */
	public void setService(PlaylistBusinessInterface service) {
		this.service = service;
	}

}
