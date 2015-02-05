package ch.elca.training.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;

import ch.elca.training.dao.GenericDao;
import ch.elca.training.dao.exceptions.DaoCannotCountException;
import ch.elca.training.dao.exceptions.DaoCannotDeleteException;
import ch.elca.training.dao.exceptions.DaoCannotSaveException;
import ch.elca.training.dao.exceptions.DaoObjectNotFoundException;
import ch.elca.training.dao.exceptions.DaoOperationException;
import ch.elca.training.dom.BaseDom;

/**
 * Implementation using Hibernate for generic DAO.
 * 
 * @author DTR
 */
public class HibernateGenericDaoImpl<T extends BaseDom> implements GenericDao<T> {
	
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
	}

	// ==================================================================================
	// IMPLEMENTED METHODS
	// ==================================================================================
	
	/**
	 * {@inheritDoc}
	 */
	public int count() throws DaoCannotCountException {
		try {
			return ((Long) sessionFactory.getCurrentSession()
	                .createCriteria(type)
	                .setProjection(Projections.rowCount())
	                .uniqueResult()).intValue();
		} catch (Exception e) {
			throw new DaoCannotCountException(
					String.format("Cannot count because: %s", e.getMessage()));
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public T get(long id) throws DaoObjectNotFoundException, DaoOperationException {
		try {
			T result = (T) sessionFactory.getCurrentSession().get(type, id);
			if (result != null) {
				return result;
			}
			throw new DaoObjectNotFoundException(
					String.format("Cannot find object %s with id: %d", type.getName(), id));
		} catch (Exception e) {
			throw new DaoOperationException(
					String.format("Cannot query object %s with id: %d because: %s", 
							type.getName(), id, e.getMessage()));
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public List<T> getAll() throws DaoOperationException {
		try {
			List<?> rawList = sessionFactory.getCurrentSession()
	                .createCriteria(type)
	                .list();
			List<T> result = new ArrayList<T>();
			for (Object object : rawList) {
				result.add((T) object);
			}
			return result;
		} catch (Exception e) {
			throw new DaoOperationException(
					String.format("Cannot get all because: %s", e.getMessage()));
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void saveOrUpdate(T object) throws DaoCannotSaveException {
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(object);
		} catch (Exception e) {
			throw new DaoCannotSaveException(String.format("Cannot save %s because: %s", 
					type.getName(), e.getMessage()));
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void delete(T object) throws DaoCannotDeleteException {
		try {
			sessionFactory.getCurrentSession().delete(object);
		} catch (Exception e) {
			throw new DaoCannotDeleteException(String.format("Cannot delete %s because: %s", 
					type.getName(), e.getMessage()));
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void deleteAll() throws DaoCannotDeleteException {
		try {
			sessionFactory.getCurrentSession().createQuery(
					"DELETE FROM " + type.getName()).executeUpdate();
		} catch (Exception e) {
			throw new DaoCannotDeleteException(String.format("Cannot delete %s because: %s", 
					type.getName(), e.getMessage()));
		}
	}
}
