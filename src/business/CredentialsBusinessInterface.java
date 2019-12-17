package business;

import java.util.List;

import javax.ejb.Local;

import beans.User;
import beans.UserCredentials;

/**
 * @Authors Holland Aucoin and Bryce Schmisseur
 * Description: This is a business service interface that reflects a credential business service
 */

@Local
public interface CredentialsBusinessInterface {
	
	/**
	 * @see CredentialBusinessService.authenticateUser
	 */
	public boolean authenticateUser(UserCredentials user);
	
	/**
	 * @see CredentialBusinessService.registerUser
	 */
	public int registerUser(User user);
	
	/**
	 * @see CredentialBusinessService.viewAllUsers
	 */
	public List<User> viewAllUsers();
	
}
