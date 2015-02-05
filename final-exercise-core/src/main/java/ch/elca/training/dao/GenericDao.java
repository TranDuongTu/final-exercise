package ch.elca.training.dao;

import java.util.List;

import ch.elca.training.dao.exceptions.DaoCannotCountException;
import ch.elca.training.dao.exceptions.DaoCannotDeleteException;
import ch.elca.training.dao.exceptions.DaoCannotSaveException;
import ch.elca.training.dao.exceptions.DaoObjectNotFoundException;
import ch.elca.training.dao.exceptions.DaoOperationException;
import ch.elca.training.dom.BaseDom;

/**
 * Generic DAO applied for all persistence objects.
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
	int count() throws DaoCannotCountException, DaoOperationException;

	/**
	 * Get the object instance from its id.
	 */
	T get(long id) throws DaoObjectNotFoundException, DaoOperationException;

	/**
	 * Get all persistence objects.
	 */
	List<T> getAll() throws DaoOperationException;

	/**
	 * Insert new transient object (or update persistence one).
	 */
	void saveOrUpdate(T object) throws DaoCannotSaveException;
	
	/**
	 * Delete an persistence object.
	 */
	void delete(T object) throws DaoCannotDeleteException;

	/**
	 * Delete all persistence objects.
	 */
	void deleteAll() throws DaoCannotDeleteException;
}
