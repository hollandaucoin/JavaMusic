package exceptions;

/**
 * @Authors Holland Aucoin and Bryce Schmisseur
 * Description: This is a Data Exception class that creates a custom exception
 */

public class DatabaseException extends RuntimeException {

	private static final long serialVersionUID = -1845773185335215452L;

	/**
	 * Construtor for custom database exception
	 * @param e - Throwable: Description of error occuring
	 */
	public DatabaseException(Throwable e) {
		super(e);
	}
}
