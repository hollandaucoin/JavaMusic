package business;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import beans.User;
import beans.UserCredentials;
import data.DataAccessInterface;

/**
 * @Authors Holland Aucoin and Bryce Schmisseur
 * Description: This is a business service that authenticates a returning user, or registers new users
 */

@Stateless
@Local(CredentialsBusinessInterface.class)
public class CredentialBusinessService implements CredentialsBusinessInterface {
	
	@Inject
	private DataAccessInterface<User> dataService;
	
	/**
	 * Method to authenticate a user. Checks for match in database, returns true or false
	 * @param userCred - UserCredentials: The user's login credentials to enter the website
	 * @return validUser - Boolean: Checks the validity of a user (if they are in database)
	 */
	@Override
	public boolean authenticateUser(UserCredentials userCred) {		
		// Create list of users, calling viewAll method in the DataAccessInterface
		List<User> userlist = dataService.viewAll();
		
		// Boolean to determine if the user is valid, set to false
		boolean validUser = false;
		
		// Verify is the user is in the database
		for (int i = 0; i < userlist.size(); i++) {
			if (userCred.getUsername().equals(userlist.get(i).getUsercredentials().getUsername()) && userCred.getPassword().equals(userlist.get(i).getUsercredentials().getPassword())) {
				
				// Set boolean of valid user to true
				validUser = true;
				
				// Create user and setting user credentials in order to pass user into getID
				User user = new User();
				user.setUsercredentials(userCred);
				
				// Create session variable and set attribute of userId
				HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
				session.setAttribute("userId", dataService.getID(user));
				break;
			}
		}
			// Information was invalid, returns false boolean, send user back to login page
			return validUser;
	}
	
	/**
	 * Method to register a new user
	 * @param user - User: The user's account information from registering
	 * @return registered - Integer: The number of rows affected by a user registration
	 */
	@Override
	public int registerUser(User user) {
		
		// Call create method in the DataAccessInterface
		int registered = dataService.create(user);
	
		// Create session variable and set attribute of userId
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		session.setAttribute("userId", dataService.getID(user));
		
		return registered;
	}

	/**
	 * Method to view all users
	 * @return users - List<User>: A list of users
	 */
	@Override
	public List<User> viewAllUsers() {
		// Create a list of users, calling viewAll method in the DataAccessInterface
		List<User> users = dataService.viewAll();
		
		return users;
	}
	
}
