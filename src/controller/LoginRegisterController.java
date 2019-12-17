package controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import beans.User;
import beans.UserCredentials;
import business.CredentialsBusinessInterface;
/**
 * @Authors Holland Aucoin and Bryce Schmisseur
 * Description: Login and Registration Controller to pass the users to the respective view, and navigates appropriately.
 */

@ManagedBean
@ViewScoped
public class LoginRegisterController {
	
	@Inject
	private CredentialsBusinessInterface service;
	
	/**
	 * Check to see is user is in the database, and if so send to home page
	 * @param user - UserCredential: The user's login credentials to enter the website
	 * @return String (navigates to next View)
	 */
	public String loginUser(UserCredentials user) {
		
		try {
			// Call the authenticateUser method in the CredentialsBusinessInterface to verify if the user is in the database
			if (service.authenticateUser(user)) {
				// Send user to HomePage
				FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("welcomeMessage", "Welcome back, " + user.getUsername() + "!");
				return "HomePage.xhtml";
			}
			else {
				// Information was invalid, send user back to the LoginPage
				FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("invalidMessage", "Sorry, that information was invalid. Please try again.");
				return "LoginPage.xhtml";
			}
		}
		// Catch exceptions and send to ErrorPage
		catch(Exception e) {
			return "ErrorPageIndex.xhtml";
		}
		
	}
	
	/**
	 * Add user to database, and sending the user back to the home page with welcome message
	 * @param user - User: The user's account information from registering
	 * @return String (navigates to next View)
	 */
	public String registerUser(User user) {
		
		try {
			// Call the registerUser method in the CredentialsBusinessInterface to add user to database
			service.registerUser(user);
			
			// Send user to HomePage
			FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("welcomeMessage", "Welcome, " + user.getUsercredentials().getUsername() + "!");
			return "HomePage.xhtml";
		}
		// Catch exceptions and send to ErrorPage
		catch(Exception e) {
			return "ErrorPageIndex.xhtml";
		}
	}
	
	/**
	 * Setter for the credentialsBusinessInterface
	 * @param service - CredentialsBusinessInterface
	 */
	public void setCredentialsBusinessInterface(CredentialsBusinessInterface service) {
		
		this.service = service;
	}
}
