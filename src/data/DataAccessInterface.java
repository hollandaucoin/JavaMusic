package data;

import java.util.List;

import javax.ejb.Local;

/**
 * @Authors Holland Aucoin and Bryce Schmisseur
 * Description: This is a data access interface that reflects the data services
 */

@Local
public interface DataAccessInterface <T>{

	/**
	 * @see DataService.create
	 */
	public int create(T t);
	
	/**
	 * @see DataService.viewAll
	 */
	public List <T> viewAll();
	
	/**
	 * @see DataService.view
	 */
	public T view();
	
	/**
	 * @see DataService.update
	 */
	public int update(T t, int id);
	
	/**
	 * @see DataService.delete
	 */
	public int delete(int id);
	
	/**
	 * @see DataService.getID
	 */
	public int getID(T t);


}
