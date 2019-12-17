package beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * @Authors Holland Aucoin and Bryce Schmisseur
 * Description: This is a model that stores the user's information, with getters and setters to access that information
 */

@ManagedBean
@ViewScoped
public class User {

	// Variable to store the user's first name. Required and has size limit
	@NotNull (message = "Please enter a First Name. This is a required field.")
	@Size (min=2, max=100, message="Sorry, First Name has to be between 2 and 100 characters.")
	private String firstName;
	
	// Variable to store the user's last name. Required and has size limit
	@NotNull (message = "Please enter a Last Name. This is a required field.")
	@Size (min=2, max=100, message="Sorry, Last Name has to be between 2 and 100 characters.")
	private String lastName;
	
	// Variable to store the user's phone number. Required and has a pattern to meet
	@NotNull (message = "Please enter a Phone Number. This is a required field.")
	@Pattern(regexp="([0-9]{3}+-[0-9]{3}+-[0-9]{4})|([0-9]{10})", message="Please enter a valid phone number.")
	private String phoneNumber;
	
	// Variable to store the user's email address. Required and has a pattern to meet
	@NotNull (message = "Please enter a Email. This is a required field.")
	@Pattern(regexp="[A-Za-z0-9._%-+]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}", message="Please enter a valid email.")
	private String email;
	
	// Create an object of UserCredentials
	UserCredentials usercredentials;
	
	
	/**
	 * Constructor for the user
	 * @param firstName - String: The user's first name
	 * @param lastName - String: The user's last name
	 * @param username - String: The user's username used to login
	 * @param password - String: The user's password used to login
	 * @param phoneNumber - String: The user's phoneNumber
	 * @param email - String: The user's email address
	 */
	public User(String firstName, String lastName, String phoneNumber, String email, String username, String password) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.email = email;
		
		UserCredentials userCred = new UserCredentials(username, password);
		this.setUsercredentials(userCred);
	}
	
	/**
	 * Empty constructor for the user
	 */
	public User() {
		this.firstName = "";
		this.lastName = "";
		this.phoneNumber = "";
		this.email = "";
		
		this.usercredentials = new UserCredentials();
	}
	
	/**
	 * Getter for first name
	 * @return firstName - String: The first name of a specific user
	 */
	public String getFirstName() {
		return firstName;
	}
	
	/**
	 * Setter for first name
	 * @param firstName - String: The first name of a specific user
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	/**
	 * Getter for last name
	 * @return lastName - String: The last name of a specific user
	 */
	public String getLastName() {
		return lastName;
	}
	
	/**
	 * Setter for last name
	 * @param lastName - String: The last name of a specific user
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	/**
	 * Getter for phone number
	 * @return phoneNumber - String: The phone number of a specific user
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	/**
	 * Setter for phone number
	 * @param phoneNumber - String: The phone number of a specific user
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	/**
	 * Getter for email
	 * @return email - String: The email of a specific user
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * Setter for email
	 * @param email - String: The email of a specific user
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Getter for a user's credentials
	 * @return usercredentials - String: The user credentials of a specific user
	 */
	public UserCredentials getUsercredentials() {
		return usercredentials;
	}

	/**
	 * Setter for a user's credentials
	 * @param usercredentials - String: The user credentials of a specific user
	 */
	public void setUsercredentials(UserCredentials usercredentials) {
		this.usercredentials = usercredentials;
	}
	
}
