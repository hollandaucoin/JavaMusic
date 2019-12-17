package rest;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import beans.Playlist;
import business.PlaylistBusinessInterface;

/**
 * @Authors Holland Aucoin and Bryce Schmisseur
 * Description: This is a class that is used to access the business service and return JSON formatted data to the user
 */

@RequestScoped
@Path("/playlist")
public class PlaylistRestService {
	
	@Inject
	PlaylistBusinessInterface service; 
	
	/**
	 * Method to return all playlists in JSON format
	 * @return playlists - List<Playlist>: A lists of all playlists
	 */
	@GET
	@Path("/getAll")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Playlist> getAllPlaylists() {
		List<Playlist> playlists = service.viewAllPlaylists();
		return playlists;
	}
}
