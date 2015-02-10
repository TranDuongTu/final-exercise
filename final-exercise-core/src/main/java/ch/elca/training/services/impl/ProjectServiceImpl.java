package ch.elca.training.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.elca.training.dao.ProjectDao;
import ch.elca.training.dao.exceptions.DaoObjectNotFoundException;
import ch.elca.training.dao.exceptions.DaoOperationException;
import ch.elca.training.dom.Project;
import ch.elca.training.services.ProjectService;
import ch.elca.training.services.exceptions.ServiceInvalidInputException;
import ch.elca.training.services.exceptions.ServiceOperationException;
import ch.elca.training.services.exceptions.ServiceProjectNotExistsException;
import ch.elca.training.services.searching.ProjectQuery;

/**
 * Implementation for {@link ProjectService}
 * 
 * @author DTR
 */
@Service(ProjectService.BEAN_NAME)
public class ProjectServiceImpl implements ProjectService {
	
	@Autowired
	private ProjectDao projectDao;
	
	private Logger logger;
	
	public ProjectServiceImpl() {
		logger = Logger.getLogger(getClass());
	}
	
	/**
	 * {@inheritDoc}
	 */
	public int countProjects() throws ServiceOperationException {
		try {
			logger.debug("Attempt to count no. Projects");
			return projectDao.count();
		} catch (DaoOperationException e) {
			logger.debug("Some thing bad occurred in DAO: " + e.getMessage());
			throw new ServiceOperationException(e.getMessage());
		} catch (Exception e) {
			logger.debug("Some thing unexpected: " + e.getMessage());
			throw new ServiceOperationException(e.getMessage());
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	public Project getProjectByNumber(int number) 
			throws ServiceProjectNotExistsException, ServiceOperationException {
		try {
			logger.debug("Attempt to find Project with number: " + number);
			return projectDao.getProjectByNumber(number);
		} catch (DaoObjectNotFoundException e) {
			logger.debug("Cannot find Project with number: " + number);
			throw new ServiceProjectNotExistsException(e.getMessage());
		} catch (DaoOperationException e) {
			logger.debug("Some thing bad occurred in DAO: " + e.getMessage());
			throw new ServiceOperationException(e.getMessage());
		} catch (Exception e) {
			logger.debug("Some thing unexpected: " + e.getMessage());
			throw new ServiceOperationException(e.getMessage());
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	public List<Project> searchProject(ProjectQuery criteria, int start, int max) 
			throws ServiceInvalidInputException, ServiceOperationException {
		logger.debug("Validating criteria: " + criteria);
		validateSearchCriteria(criteria);
		
		try {
			logger.debug("Attempt to find Project with criteria: " + criteria);
			Integer projectNumber = criteria.getProjectNumber();
			if (projectNumber != null) {
				logger.debug("Number present, find Project with number: " + projectNumber);
				Project onlyOneProject = null;
				try {
					onlyOneProject = projectDao.getProjectByNumber(projectNumber);
				} catch (DaoObjectNotFoundException e) {
					logger.debug("Cannot find Project with number: " + projectNumber);
				}
				
				List<Project> result = new ArrayList<Project>();
				if (onlyOneProject != null) {
					result.add(onlyOneProject);
				}
				logger.debug("Return at most one Project: " + result);
				return result;
			}
			
			logger.debug("Find Projects that matches 3 rest patterns");
			return projectDao.findProjectsMatchPatterns(
					criteria.getProjectName(), 
					criteria.getCustomer(), 
					criteria.getProjectStatus(), 
					start, max);
		} catch (DaoOperationException e) {
			logger.debug("Some thing occurred in DAO: " + e.getMessage());
			throw new ServiceOperationException(e.getMessage());
		} catch (Exception e) {
			logger.debug("Some thing unexpected: " + e.getMessage());
			throw new ServiceOperationException(e.getMessage());
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	public int countProjectMatch(ProjectQuery criteria)
			throws ServiceOperationException {
		logger.debug("Validating criteria: " + criteria);
		validateSearchCriteria(criteria);
		
		try {
			logger.debug("Attempt to count Projects match: " + criteria);
			Integer projectNumber = criteria.getProjectNumber();
			if (projectNumber != null) {
				logger.debug("Number present, count with number: " + projectNumber);
				Project onlyOneProject;
				try {
					onlyOneProject = projectDao.getProjectByNumber(projectNumber);
				} catch (DaoObjectNotFoundException e) {
					logger.debug("Cannot find Project with number: " + projectNumber);
					onlyOneProject = null;
				}

				return onlyOneProject == null ? 0 : 1;
			}
			
			logger.debug("Find Projects that matches 3 rest patterns");
			return projectDao.countProjectsMatchPatterns(
					criteria.getProjectName(), 
					criteria.getCustomer(), 
					criteria.getProjectStatus());
		} catch (DaoOperationException e) {
			logger.debug("Some thing occurred in DAO: " + e.getMessage());
			throw new ServiceOperationException(e.getMessage());
		} catch (Exception e) {
			logger.debug("Some thing unexpected: " + e.getMessage());
			throw new ServiceOperationException(e.getMessage());
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void saveOrUpdateProject(Project project) 
			throws ServiceInvalidInputException, ServiceOperationException {
		try {
			logger.debug("Attempt to save or update Project: " + project);
			projectDao.saveOrUpdate(project);
		} catch (DaoOperationException e) {
			logger.debug("Some thing bad in DAO " + e.getMessage());
			throw new ServiceOperationException(e.getMessage());
		} catch (Exception e) {
			logger.debug("Some thing unexpected: " + e.getMessage());
			throw new ServiceOperationException(e.getMessage());
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void deleteProject(Project project)throws ServiceOperationException {
		try {
			logger.debug("Attempt to delete Project; " + project);
			projectDao.delete(project);
		} catch (DaoOperationException e) {
			logger.debug("Some thing bad in DAO " + e.getMessage());
			throw new ServiceOperationException(e.getMessage());
		} catch (Exception e) {
			logger.debug("Some thing unexpected: " + e.getMessage());
			throw new ServiceOperationException(e.getMessage());
		}
	}
	
	// ==================================================================================
	// PRIVATE HELPERs
	// ==================================================================================
	
	private void validateSearchCriteria(ProjectQuery criteria) 
			throws ServiceInvalidInputException {
		logger.debug("Validating criteria: " + criteria);
		if (criteria.getProjectName() == null
				|| (criteria.getProjectNumber() != null && criteria.getProjectNumber() < 0)
				|| criteria.getCustomer() == null
				|| (criteria.getProjectName().isEmpty()
						&& criteria.getProjectNumber() == null
						&& criteria.getCustomer().isEmpty()
						&& criteria.getProjectStatus() == null)) {
			throw new ServiceInvalidInputException(
					"Invalid search criteria: %s" + criteria.toString());
		}
	}
}
