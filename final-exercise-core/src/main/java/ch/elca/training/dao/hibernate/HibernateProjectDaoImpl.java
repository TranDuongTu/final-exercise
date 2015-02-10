package ch.elca.training.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import ch.elca.training.dao.ProjectDao;
import ch.elca.training.dao.exceptions.DaoObjectNotFoundException;
import ch.elca.training.dao.exceptions.DaoOperationException;
import ch.elca.training.dom.Project;
import ch.elca.training.dom.Status;

/**
 * Hibernate implementation for Project DAO.
 * 
 * @author DTR
 */
public class HibernateProjectDaoImpl 
		extends HibernateGenericDaoImpl<Project> implements ProjectDao {

	/**
	 * Constructor.
	 */
	public HibernateProjectDaoImpl() {
		super(Project.class);
	}
	
	/**
	 * {@inheritDoc}
	 */
	public Project getProjectByNumber(int number) 
			throws DaoObjectNotFoundException, DaoOperationException {
		
		try {
			logger.debug("Attempt to get Project with number: " + number);
			
			Project result = (Project) getSessionFactory().getCurrentSession()
					.createCriteria(getType()).add(criterionNumberMatch(number))
					.uniqueResult();
			
			if (result == null) {
				throw new DaoObjectNotFoundException(
						String.format("No Project with number %d found", number));
			}
			
			logger.debug("Retrieved Project: " + result.toString());
			return result;
		} catch (DaoObjectNotFoundException e) {
			logger.debug("The return Project is null");
			throw e;
		} catch (Exception e) {
			logger.debug("Unexpected error occurred: " + e.getMessage());
			throw new DaoOperationException(e.getMessage());
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	public List<Project> findProjectsWithNamePattern(String name) 
			throws DaoOperationException {
		try {
			logger.debug("Attempt to find Project with name pattern: " + name);
			
			Criteria criteria = getSessionFactory().getCurrentSession()
					.createCriteria(getType()).add(criterionNameMatch(name));
			List<Project> result = findProjects(criteria);
			
			logger.debug("Query return " + result.size() + " projects");

			return result;
		} catch (Exception e) {
			logger.debug("Unexpected error: " + e.getMessage());
			throw new DaoOperationException(e.getMessage());
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	public List<Project> findProjectsWithCustomerPattern(String customer) 
			throws DaoOperationException {
		try {
			logger.debug("Attempt to find Project with customer pattern: " + customer);
			
			Criteria criteria = getSessionFactory().getCurrentSession()
					.createCriteria(getType())
					.add(criterionCustomerMatch(customer));
			List<Project> result = findProjects(criteria);
			
			logger.debug("Criteria query return " + result.size() + " projects");

			return result;
		} catch (Exception e) {
			logger.debug("Unexpected error: " + e.getMessage());
			throw new DaoOperationException(e.getMessage());
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	public List<Project> findProjectsWithStatus(Status status) 
			throws DaoOperationException {
		try {
			logger.debug("Attempt to find Projects with Status: " + status);
			
			Criteria criteria = getSessionFactory().getCurrentSession()
					.createCriteria(getType()).add(criterionStatusMatch(status));
			List<Project> result = findProjects(criteria);
			
			logger.debug("Criteria query return " + result.size() + " projects");

			return result;
		} catch (Exception e) {
			logger.debug("Unexpected error: " + e.getMessage());
			throw new DaoOperationException(e.getMessage());
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	public java.util.List<Project> findProjectsMatchPatterns(
			String name, String customer, Status status) throws DaoOperationException {
		
		try {
			logger.debug(String.format("Attempt to find Projects with name pattern: %s;"
					+ "customer pattern: %s; Status: %s", name, customer, status));
			
			Criteria criteria = getSessionFactory().getCurrentSession()
					.createCriteria(getType())
					.add(criterionForMultiplePatternsMatching(name, customer, status));
			List<Project> result = findProjects(criteria);
			
			logger.debug("Criteria query return " + result.size() + " projects");
			
			return result;
		} catch (Exception e) {
			logger.debug("Unexpected error: " + e.getMessage());
			throw new DaoOperationException(e.getMessage());
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	public java.util.List<Project> findProjectsMatchPatterns(
			String name, String customer, Status status, int start, int length) 
					throws DaoOperationException {
		
		try {
			logger.debug(String.format("Attempt to find Projects with name pattern: %s;"
					+ "customer pattern: %s; Status: %s. Start at: %d, length: %d", 
						name, customer, status, start, length));
			
			Criteria criteria = getSessionFactory().getCurrentSession()
					.createCriteria(getType())
					.add(criterionForMultiplePatternsMatching(name, customer, status))
					.addOrder(Order.asc(Project.PROPERTY_NUMBER))
					.setFirstResult(start)
					.setMaxResults(length);
			List<Project> result = findProjects(criteria);
			
			logger.debug("Criteria query return " + result.size() + " projects");

			return result;
		} catch (Exception e) {
			logger.debug("Unexpected error: " + e.getMessage());
			throw new DaoOperationException(e.getMessage());
		}
	}
	
	// ============================================================================================
	// PRIVATE HELPERs
	// ============================================================================================
	
	private Criterion criterionForMultiplePatternsMatching(String name, String customer, Status status) {
		Criterion nameMatch = criterionNameMatch(name);
		Criterion customerMatch = criterionCustomerMatch(customer);
		Criterion statusMatch = criterionStatusMatch(status);
		
		return Restrictions.and(nameMatch, Restrictions.and(customerMatch, statusMatch));
	}
	
	private Criterion criterionNumberMatch(int number) {
		return Restrictions.eq(Project.PROPERTY_NUMBER, number);
	}
	
	private Criterion criterionNameMatch(String name) {
		return Restrictions.like(Project.PROPERTY_NAME, "%" + name + "%");
	}
	
	private Criterion criterionCustomerMatch(String customer) {
		return Restrictions.like(Project.PROPERTY_CUSTOMER, "%" + customer + "%");
	}
	
	private Criterion criterionStatusMatch(Status status) {
		if (status != null) {
			return Restrictions.eq(Project.PROPERTY_STATUS, status);
		} else {
			return Restrictions.sqlRestriction("(0 = 0)");
		}
	}
	
	private List<Project> findProjects(Criteria criteria) {
		List<?> rawList = criteria.list();
		
		List<Project> result = new ArrayList<Project>();
		for (Object object : rawList) {
			result.add((Project) object);
		}
		
		return result;
	}
}
