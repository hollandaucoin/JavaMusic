package beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 * @Authors Holland Aucoin and Bryce Schmisseur
 * Description: This is a model that stores a search input, with getters and setters to access that information
 */

@ManagedBean
@ViewScoped
public class Search {

	// Variable to store the attributes of a song, set to empty
	String input = "";
	String page = "";
	
	/**
	 * Constructor for a search object
	 * @param input - String: The search parameter input by the user
	 * @param page - String: The page where the search function is being used
	 */
	public Search(String input, String page) {
		this.input = input;
		this.page = page;
	}
	
	/**
	 * Empty constructor for a search object
	 */
	public Search() {
		this.input = "";
		this.page = "";
	}

	/**
	 * Getter for search input
	 * @return input - String: The input search parameter
	 */
	public String getInput() {
		return input;
	}

	/**
	 * Setter for search input
	 * @param input - String: The input search parameter
	 */
	public void setInput(String input) {
		this.input = input;
	}

	/**
	 * Getter for page
	 * @return page - String: The page where search function is being used
	 */
	public String getPage() {
		return page;
	}

	/**
	 * Setter for page
	 * @param page - String: The page where search function is being used
	 */
	public void setPage(String page) {
		this.page = page;
	}
	
}
