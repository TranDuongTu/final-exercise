package ch.elca.training.dao;

import java.util.List;

import ch.elca.training.dom.BaseDom;

/**
 * Generic DAO for querying all persistence objects.
 * 
 * @author DTR
 *
 * @param <T>
 *            Domain object to be queried
 */
public interface GenericDao<T extends BaseDom> {

	/**
	 * Total number of persistence objects currently exists.
	 */
	int count();

	/**
	 * Get the object instance from its id.
	 */
	T get(long id);

	/**
	 * Get all persistence objects.
	 */
	List<T> getAll();

	/**
	 * Insert new transient object (or update persistence one).
	 */
	void saveOrUpdate(T object);
	
	/**
	 * Delete an persistence object.
	 */
	void delete(T object);

	/**
	 * Delete all persistence objects.
	 */
	void deleteAll();
}
