package ch.elca.training.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
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
			return getByNumber(number);
		} catch (DaoObjectNotFoundException e) {
			String message = "Project not found with number " + number + ": " + e;
			logger.debug(message);
			throw new DaoObjectNotFoundException(message);
		} catch (Exception e) {
			String message = "Unexpected error when getting Project: " + e;
			logger.debug(message);
			throw new DaoOperationException(message);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	public List<Project> findProjectsWithNamePattern(String name) 
			throws DaoOperationException {
		try {
			logger.debug("Attempt to find Project with name: " + name);
			
			Criteria criteria = getSessionFactory().getCurrentSession()
					.createCriteria(getType())
					.add(criterionNameMatch(name));
			List<Project> result = findProjects(criteria);
			
			logger.debug("Query return " + result.size() + " projects");

			return result;
		} catch (Exception e) {
			String message = "Unexpected error when finding with name: " + e;
			logger.debug(message);
			throw new DaoOperationException(message);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	public List<Project> findProjectsWithCustomerPattern(String customer) 
			throws DaoOperationException {
		try {
			logger.debug("Attempt to find Project with customer: " + customer);
			
			Criteria criteria = getSessionFactory().getCurrentSession()
					.createCriteria(getType())
					.add(criterionCustomerMatch(customer));
			List<Project> result = findProjects(criteria);
			
			logger.debug("Criteria query return " + result.size() + " projects");

			return result;
		} catch (Exception e) {
			String message = "Unexpected error when finding with customer: " + e;
			logger.debug(message);
			throw new DaoOperationException(message);
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
					.createCriteria(getType())
					.add(criterionStatusMatch(status));
			List<Project> result = findProjects(criteria);
			
			logger.debug("Criteria query return " + result.size() + " projects");

			return result;
		} catch (Exception e) {
			String message = "Unexpected error when finding with status: " + e;
			logger.debug(message);
			throw new DaoOperationException(message);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	public List<Project> findProjectsMatchPatterns(
			String name, String customer, Status status) throws DaoOperationException {
		
		try {
			logger.debug(String.format("Attempt to find Projects with "
					+ "name: %s;"
					+ "customer: %s; "
					+ "Status: %s", 
					name, customer, status));
			
			Criteria criteria = getSessionFactory().getCurrentSession()
					.createCriteria(getType())
					.add(criterionForMultiplePatternsMatching(name, customer, status));
			List<Project> result = findProjects(criteria);
			
			logger.debug("Criteria query return " + result.size() + " projects");
			
			return result;
		} catch (Exception e) {
			String message = "Unexpected error when finding with 3 patterns: " + e;
			logger.debug(message);
			throw new DaoOperationException(message);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	public List<Project> findProjectsMatchPatterns(
			String name, String customer, Status status, int start, int length) 
					throws DaoOperationException {
		
		try {
			logger.debug(String.format("Attempt to find Projects with "
					+ "name: %s;"
					+ "customer: %s; "
					+ "Status: %s. "
					+ "Start at: %d, "
					+ "length: %d", 
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
			String message = "Unexpected error when finding with patterns and paging: " + e;
			logger.debug(message);
			throw new DaoOperationException(message);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	public int countProjectsMatchPatterns(
			String name, String customer, Status status) throws DaoOperationException {
		try {
			logger.debug(String.format("Attempt to count Projects with "
					+ "name: %s;"
					+ "customer: %s; "
					+ "Status: %s", 
					name, customer, status));
			
			int result = ((Long) getSessionFactory().getCurrentSession()
					.createCriteria(getType())
					.add(criterionForMultiplePatternsMatching(name, customer, status))
					.setProjection(Projections.rowCount())
					.uniqueResult()).intValue();
			
			logger.debug("Criteria query return " + result + " projects");
			
			return result;
		} catch (Exception e) {
			String message = "Unexpected error when counting with patterns: " + e;
			logger.debug(message);
			throw new DaoOperationException(message);
		}
	}
	
	// ============================================================================================
	// PRIVATE HELPERs
	// ============================================================================================
	
	/**
	 * Get Project with number.
	 */
	private Project getByNumber(int number) throws DaoObjectNotFoundException {
		logger.debug("Attempt to get Project with number: " + number);
		
		Project result = (Project) getSessionFactory().getCurrentSession()
				.createCriteria(getType()).add(criterionNumberMatch(number))
				.uniqueResult();
		
		if (result == null) {
			String message = "No Project with number " + number + " found";
			throw new DaoObjectNotFoundException(message);
		}
		
		logger.debug("Retrieved Project: " + result);
		return result;
	}
	
	/**
	 * Criterion for finding with multiple patterns.
	 */
	private Criterion criterionForMultiplePatternsMatching(String name, String customer, Status status) {
		Criterion nameMatch = criterionNameMatch(name);
		Criterion customerMatch = criterionCustomerMatch(customer);
		Criterion statusMatch = criterionStatusMatch(status);
		
		return Restrictions.and(nameMatch, Restrictions.and(customerMatch, statusMatch));
	}
	
	/**
	 * Criterion for finding with number pattern.
	 */
	private Criterion criterionNumberMatch(int number) {
		return Restrictions.eq(Project.PROPERTY_NUMBER, number);
	}
	
	/**
	 * Criterion for finding with name pattern.
	 */
	private Criterion criterionNameMatch(String name) {
		return Restrictions.like(Project.PROPERTY_NAME, "%" + name + "%");
	}
	
	/**
	 * Criterion for finding with customer pattern.
	 */
	private Criterion criterionCustomerMatch(String customer) {
		return Restrictions.like(Project.PROPERTY_CUSTOMER, "%" + customer + "%");
	}
	
	/**
	 * Criterion for finding with status.
	 */
	private Criterion criterionStatusMatch(Status status) {
		if (status != null) {
			return Restrictions.eq(Project.PROPERTY_STATUS, status);
		} else {
			return Restrictions.sqlRestriction("(0 = 0)");
		}
	}
	
	/**
	 * Finding matched objects and convert to list of projects.
	 */
	private List<Project> findProjects(Criteria criteria) {
		List<?> rawList = criteria.list();
		
		List<Project> result = new ArrayList<Project>();
		for (Object object : rawList) {
			result.add((Project) object);
		}
		
		return result;
	}
}
