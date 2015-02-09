package ch.elca.training.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;

import ch.elca.training.dao.GenericDao;
import ch.elca.training.dao.exceptions.DaoObjectNotFoundException;
import ch.elca.training.dao.exceptions.DaoOperationException;
import ch.elca.training.dom.BaseDom;

/**
 * Implementation using Hibernate for generic DAO.
 * 
 * @author DTR
 */
public abstract class HibernateGenericDaoImpl<T extends BaseDom> implements GenericDao<T> {
	
	/**
	 * LOGGER for DAO concrete implementations.
	 */
	protected Logger logger;
	
	/**
	 * Type information needed to do generic query.
	 */
	private Class<T> type;
	
	public Class<T> getType() {
		return type;
	}

	public void setType(Class<T> type) {
		this.type = type;
	}

	/**
	 * {@link SessionFactory} for using Hibernate features.
	 */
	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	/**
	 * Constructor of generic need type information.
	 */
	public HibernateGenericDaoImpl(Class<T> type) {
		this.type = type;
		logger = Logger.getLogger(type.getName());
	}

	// ==================================================================================
	// IMPLEMENTED METHODS
	// ==================================================================================
	
	/**
	 * {@inheritDoc}
	 */
	public int count() throws DaoOperationException {
		try {
			logger.debug("Start counting...");
			int result = ((Long) sessionFactory.getCurrentSession()
	                .createCriteria(type)
	                .setProjection(Projections.rowCount())
	                .uniqueResult()).intValue();
			logger.debug("End counting with result: " + result);
			return result;
		} catch (Exception e) {
			logger.debug("Exception when counting: " + e.getMessage());
			throw new DaoOperationException(e.getMessage());
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public T get(long id) throws DaoObjectNotFoundException, DaoOperationException {
		try {
			logger.debug("Get object with id = " + id);
			T result = (T) sessionFactory.getCurrentSession().get(type, id);
			if (result != null) {
				logger.debug("Return object: " + result);
				return result;
			}
			
			logger.debug("Null object returned with id: " + id);
			throw new DaoObjectNotFoundException("ID = " + id);
		} catch (Exception e) {
			logger.debug("Unexpected error occurred: " + e.getMessage());
			throw new DaoOperationException(e.getMessage());
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public List<T> getAll() throws DaoOperationException {
		try {
			logger.debug("Attempt to get all");
			List<?> rawList = sessionFactory.getCurrentSession()
	                .createCriteria(type).list();
			
			logger.debug("Query by criteria return " + rawList.size() + " objects");
			List<T> result = new ArrayList<T>();
			for (Object object : rawList) {
				result.add((T) object);
			}
			return result;
		} catch (Exception e) {
			logger.debug("Unexpected error occurred: " + e.getMessage());
			throw new DaoOperationException(e.getMessage());
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void saveOrUpdate(T object) throws DaoOperationException {
		try {
			logger.debug("Try to save or update object: " + object.toString());
			sessionFactory.getCurrentSession().saveOrUpdate(object);
			logger.debug("Successfully save or update object. New ID = " + object.getId());
		} catch (Exception e) {
			logger.debug("Error when saving or updating: " + e.getMessage());
			throw new DaoOperationException(e.getMessage());
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void delete(T object) throws DaoOperationException {
		try {
			logger.debug("Attempt to delete object: " + object.toString());
			sessionFactory.getCurrentSession().delete(object);
			logger.debug("Successfully delete object " + object.toString());
		} catch (Exception e) {
			logger.debug("Error when deleting: " + e.getMessage());
			throw new DaoOperationException(e.getMessage());
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void deleteAll() throws DaoOperationException {
		try {
			logger.debug("Attempt to delete All");
			sessionFactory.getCurrentSession()
				.createQuery("DELETE FROM " + type.getName())
				.executeUpdate();
			logger.debug("Delete All completed");
		} catch (Exception e) {
			logger.debug("Error when deleting All: " + e.getMessage());
			throw new DaoOperationException(e.getMessage());
		}
	}
}
