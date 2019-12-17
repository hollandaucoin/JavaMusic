package beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @Authors Holland Aucoin and Bryce Schmisseur
 * Description: This is a model that stores the user's information, with getters and setters to access that information
 */

@ManagedBean
@ViewScoped
public class UserCredentials {

	// Variable to store the user's username, used for login. Required and ahs size limit
	@NotNull (message = "Please enter a Username. This is a required field.")
	@Size (min=6, max=100, message="Sorry, Username has to be between 6 and 100 characters.")
	private String username;
	
	// Variable to store the user's password, used for login. Required and ahs size limit
	@NotNull (message = "Please enter a Password. This is a required field.")
	@Size (min=6, max=100, message="Sorry, Password has to be between 6 and 100 characters.")
	private String password;
	
	/**
	 * Constructor for a user's credentials
	 */
	public UserCredentials() {
		username = "";
		password = "";
	}
	
	/**
	 * Constructor for a user's credentials
	 * @param username - String: The username a user has created to use to login
	 * @param password - String: The password a user has created to use to login
	 */
	public UserCredentials(String username, String password) {
		this.username = username;
		this.password = password;
	}
	

	/**
	 * Getters
	 * @return variables - String: The username and password of a user
	 * 
	 * Setters
	 * @param variables - String: The username and password of a user
	 */
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
}
