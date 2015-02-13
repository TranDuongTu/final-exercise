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
	
	private Logger logger = Logger.getLogger(getClass());
	
	/**
	 * {@inheritDoc}
	 */
	public ProjectDao getProjectDao() {
		return projectDao;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public int countProjects() throws ServiceOperationException {
		try {
			logger.debug("Attempt to count total no. Projects");
			return projectDao.count();
		} catch (DaoOperationException e) {
			String message = "Error in DAO when counting";
			logger.debug(message + ": " + e);
			throw new ServiceOperationException(message, e);
		} catch (Exception e) {
			String message = "Unexpected error";
			logger.debug(message + ": " + e);
			throw new ServiceOperationException(message, e);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	public Project getProjectByNumber(int number) 
			throws ServiceProjectNotExistsException, ServiceOperationException {
		try {
			logger.debug("Attempt to get Project with number: " + number);
			return projectDao.getProjectByNumber(number);
		} catch (DaoObjectNotFoundException e) {
			String message = "Error Project not found in DAO with number " + number;
			logger.debug(message + ": " + e);
			throw new ServiceProjectNotExistsException(message, e);
		} catch (DaoOperationException e) {
			String message = "Error in DAO when getting Project";
			logger.debug(message + ": " + e);
			throw new ServiceOperationException(message, e);
		} catch (Exception e) {
			String message = "Unexpected error";
			logger.debug(message + ": " + e);
			throw new ServiceOperationException(message, e);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	public List<Project> searchProject(ProjectQuery projectQuery) 
			throws ServiceInvalidInputException, ServiceOperationException {
		
		try {
			logger.debug("Attempt to find Projects with criteria: " + projectQuery);
			validateSearchCriteria(projectQuery);
			
			Integer projectNumber = projectQuery.getProjectNumber();
			if (projectNumber != null) {
				return findProjectWithNumber(projectNumber);
			}
			
			return findProjectsMatchOthers(projectQuery);
		} catch (DaoOperationException e) {
			String message = "Error in DAO when finding Projects";
			logger.debug(message + ": " + e);
			throw new ServiceOperationException(message, e);
		} catch (Exception e) {
			String message = "Unexpected error";
			logger.debug(message + ": " + e);
			throw new ServiceOperationException(message, e);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	public int countProjectMatchExcludePaging(ProjectQuery projectQuery)
			throws ServiceOperationException {
		
		try {
			logger.debug("Attempt to count Projects match (exclude paging): " + projectQuery);
			validateSearchCriteria(projectQuery);
			
			Integer projectNumber = projectQuery.getProjectNumber();
			if (projectNumber != null) {
				return countProjectsWithNumber(projectNumber);
			}
			
			return countProjectsMatchOthers(projectQuery);
		} catch (DaoOperationException e) {
			String message = "Error in DAO when trying to count with patterns";
			logger.debug(message + ": " + e);
			throw new ServiceOperationException(message, e);
		} catch (Exception e) {
			String message = "Unexpected error";
			logger.debug(message + ": " + e);
			throw new ServiceOperationException(message, e);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void saveOrUpdateProject(Project project) 
			throws ServiceInvalidInputException, ServiceOperationException {
		try {
			logger.debug("Attempt to save or update Project: " + project);
			validateProject(project);
			
			projectDao.saveOrUpdate(project);
		} catch (DaoOperationException e) {
			String message = "Error in DAO when saving or updating Project";
			logger.debug(message + ": " + e);
			throw new ServiceOperationException(message, e);
		} catch (Exception e) {
			String message = "Unexpected error";
			logger.debug(message + ": " + e);
			throw new ServiceOperationException(message, e);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void deleteProject(Project project) 
			throws ServiceProjectNotExistsException, ServiceOperationException {
		try {
			logger.debug("Attempt to delete Project: " + project);
			validateProject(project);
			
			checkProjectNumberExist(project.getNumber());
			
			projectDao.delete(project);
		} catch (DaoOperationException e) {
			String message = "Error in DAO when checking and deleting Project";
			logger.debug(message + ": " + e);
			throw new ServiceOperationException(message, e);
		} catch (Exception e) {
			String message = "Unexpected error";
			logger.debug(message + ": " + e);
			throw new ServiceOperationException(message, e);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void deleteProjectNumber(int number) throws ServiceOperationException {
		try {
			logger.debug("Attemp to delete Project with number: " + number);
			Project project = projectDao.getProjectByNumber(number);
			logger.debug("Attempt to delete Project: " + project);
			projectDao.delete(project);
		} catch (DaoObjectNotFoundException e) {
			String message = "Project not exist";
			logger.debug(message + ": " + e);
			throw new ServiceOperationException(message, e);
		}catch (DaoOperationException e) {
			String message = "Error in DAO when checking and deleting Project";
			logger.debug(message + ": " + e);
			throw new ServiceOperationException(message, e);
		} catch (Exception e) {
			String message = "Unexpected error";
			logger.debug(message + ": " + e);
			throw new ServiceOperationException(message, e);
		}
	}
	
	// ==================================================================================
	// PRIVATE HELPERs
	// ==================================================================================
	
	/**
	 * Validate search criteria.
	 */
	private void validateSearchCriteria(ProjectQuery criteria) 
			throws ServiceInvalidInputException {
		logger.debug("Validating ProjectQuery...");
		if (criteria.getProjectName() == null
				|| (criteria.getProjectNumber() != null && criteria.getProjectNumber() < 0)
				|| criteria.getCustomer() == null
				|| (criteria.getProjectName().isEmpty()
						&& criteria.getProjectNumber() == null
						&& criteria.getCustomer().isEmpty()
						&& criteria.getProjectStatus() == null)) {
			throw new ServiceInvalidInputException(
					"Invalid search ProjectQuery: " + criteria);
		}
		logger.debug("ProjectQuery validated");
	}
	
	/**
	 * Validating Project.
	 */
	private void validateProject(Project project) throws ServiceInvalidInputException {
		logger.debug("Validating project...");
		if (project.getCustomer() == null 
				|| project.getEndDate().getTime() < project.getStartDate().getTime()
				|| project.getName() == null
				|| project.getName().length() > Project.CONSTRAINT_NAME_LENGTH
				|| project.getNumber() == null
				|| project.getNumber() < 1
				|| project.getStartDate() == null
				|| project.getStatus() == null) {
			throw new ServiceInvalidInputException(
					"Invalid search Project: " + project);
		}
		logger.debug("Project validated");
	}
	
	/**
	 * Search result by number.
	 */
	private List<Project> findProjectWithNumber(int projectNumber) throws DaoOperationException {
		logger.debug("Number present, find Project with number: " + projectNumber);
		Project onlyOneProject = null;
		try {
			onlyOneProject = projectDao.getProjectByNumber(projectNumber);
		} catch (DaoObjectNotFoundException e) {
			logger.debug("Cannot find Project with number: " + projectNumber);
		}
		
		/* At most one Project will be returned */
		List<Project> result = new ArrayList<Project>();
		if (onlyOneProject != null) {
			result.add(onlyOneProject);
		}
		logger.debug("Return at most one Project: " + result);
		return result;
	}
	
	/**
	 * Search result by intersecting other patterns.
	 */
	private List<Project> findProjectsMatchOthers(ProjectQuery projectQuery) 
			throws DaoOperationException {
		logger.debug("Find Projects that matches 3 rest patterns");
		return projectDao.findProjectsMatchPatterns(
				projectQuery.getProjectName(), 
				projectQuery.getCustomer(), 
				projectQuery.getProjectStatus(), 
				projectQuery.getStart(), 
				projectQuery.getMax());
	}
	
	/**
	 * Count projects if number present.
	 */
	private int countProjectsWithNumber(int projectNumber) throws DaoOperationException {
		logger.debug("Number present, count with number: " + projectNumber);
		return isNumberExist(projectNumber) ? 1 : 0;
	}
	
	/**
	 * Count projects by intersecting other patterns.
	 */
	private int countProjectsMatchOthers(ProjectQuery projectQuery) 
			throws DaoOperationException {
		logger.debug("Count Projects that matches 3 rest patterns");
		return projectDao.countProjectsMatchPatterns(
				projectQuery.getProjectName(), 
				projectQuery.getCustomer(), 
				projectQuery.getProjectStatus());
	}
	
	/**
	 * Check if Project exists.
	 */
	private void checkProjectNumberExist(int projectNumber) 
			throws ServiceProjectNotExistsException, DaoOperationException {
		if (!isNumberExist(projectNumber)) {
			String message = "Project not exists";
			throw new ServiceProjectNotExistsException(message);
		}
	}
	
	/**
	 * Check project number exist.
	 */
	private boolean isNumberExist(int projectNumber) throws DaoOperationException {
		try {
			projectDao.getProjectByNumber(projectNumber);
			logger.debug("Number exist: " + projectNumber);
			return true;
		} catch (DaoObjectNotFoundException e) {
			logger.debug("Number not exist: " + projectNumber);
			return false;
		}
	}
}
