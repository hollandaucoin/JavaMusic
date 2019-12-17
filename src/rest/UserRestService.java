package rest;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import beans.User;
import business.CredentialsBusinessInterface;

/**
 * @Authors Holland Aucoin and Bryce Schmisseur
 * Description: This is a class that is used to access the business service and return JSON formatted data to the user
 */

@RequestScoped
@Path("/users")
public class UserRestService {
	
	@Inject
	CredentialsBusinessInterface service; 
	
	/**
	 * Method to return all users in JSON format
	 * @return users - List<User>: A lists of all users
	 */
	@GET
	@Path("/getAll")
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> getAllUsers() {
		List<User> users = service.viewAllUsers();
		return users;
	}
}
