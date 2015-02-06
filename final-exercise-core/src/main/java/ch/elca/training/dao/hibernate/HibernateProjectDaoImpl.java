package ch.elca.training.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
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
			Project result = (Project) getSessionFactory().getCurrentSession()
					.createCriteria(getType()).add(criterionNumberMatch(number))
					.uniqueResult();
			
			if (result == null) {
				throw new DaoObjectNotFoundException(
						String.format("No Project with number: %d", number));
			}
			
			return result;
		} catch (Exception e) {
			throw new DaoOperationException(String.format(
					"Cannot get Project by number because: %s", e.getMessage()));
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	public List<Project> findProjectsWithNamePattern(String name) 
			throws DaoOperationException {
		
		try {
			List<?> rawList = getSessionFactory().getCurrentSession()
					.createCriteria(getType()).add(criterionNameMatch(name))
					.list();
			
			List<Project> result = new ArrayList<Project>();
			for (Object object : rawList) {
				result.add((Project) object);
			}
			
			return result;
		} catch (Exception e) {
			throw new DaoOperationException(String.format(
					"Cannot find Project by name pattern because: %s", 
					e.getMessage()));
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	public List<Project> findProjectsWithCustomerPattern(String customer) 
			throws DaoOperationException {
		
		try {
			List<?> rawList = getSessionFactory().getCurrentSession()
					.createCriteria(getType())
					.add(criterionCustomerMatch(customer)).list();
			
			List<Project> result = new ArrayList<Project>();
			for (Object object : rawList) {
				result.add((Project) object);
			}
			
			return result;
		} catch (Exception e) {
			throw new DaoOperationException(String.format(
					"Cannot find Project by customer pattern because: %s", 
					e.getMessage()));
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	public List<Project> findProjectsWithStatus(Status status) 
			throws DaoOperationException {
		
		try {
			List<?> rawList = getSessionFactory().getCurrentSession()
					.createCriteria(getType()).add(criterionStatusMatch(status))
					.list();
			
			List<Project> result = new ArrayList<Project>();
			for (Object object : rawList) {
				result.add((Project) object);
			}
			
			return result;
		} catch (Exception e) {
			throw new DaoOperationException(String.format(
					"Cannot find Project by status because: %s", e.getMessage()));
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	public java.util.List<Project> findProjectsMatchPatterns(
			String name, String customer, Status status) throws DaoOperationException {
		
		try {
			List<?> rawList = getSessionFactory().getCurrentSession()
					.createCriteria(getType())
					.add(criterionForMultiplePatternsMatching(name, customer, status))
					.list();
			
			List<Project> result = new ArrayList<Project>();
			for (Object object : rawList) {
				result.add((Project) object);
			}
			
			return result;
		} catch (Exception e) {
			throw new DaoOperationException(String.format(
					"Cannot find Project by patterns because: %s", e.getMessage()));
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
}
