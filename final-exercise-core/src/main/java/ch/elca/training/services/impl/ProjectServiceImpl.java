package ch.elca.training.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.elca.training.dao.ProjectDao;
import ch.elca.training.dao.exceptions.DaoOperationException;
import ch.elca.training.dom.Project;
import ch.elca.training.services.ProjectService;
import ch.elca.training.services.exceptions.ServiceInvalidInputException;
import ch.elca.training.services.exceptions.ServiceOperationException;
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
	
	/**
	 * {@inheritDoc}
	 */
	public List<Project> searchProject(ProjectQuery criteria) 
			throws ServiceInvalidInputException, ServiceOperationException {
		
		validateSearchCriteria(criteria);
		
		try {
			if (criteria.getProjectNumber() != 0) {
				Project onlyOneProject = projectDao.getProjectByNumber(criteria.getProjectNumber());
				List<Project> result = new ArrayList<Project>();
				result.add(onlyOneProject);
				return result;
			}
			
			return projectDao.findProjectsMatchPatterns(
					criteria.getProjectName(), 
					criteria.getCustomer(), 
					criteria.getProjectStatus());
		} catch (Exception e) {
			throw new ServiceOperationException(String.format(
					"Search projects failed due to: %s", e.getMessage()));
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void saveOrUpdateProject(Project project) 
			throws ServiceInvalidInputException, ServiceOperationException {
		
		validateProject(project);
		
		try {
			projectDao.saveOrUpdate(project);
		} catch (Exception e) {
			throw new ServiceOperationException(String.format(
					"Update project failed due to: %s", e.getMessage()));
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void deleteProject(Project project)throws ServiceOperationException {
		try {
			projectDao.delete(project);
		} catch (DaoOperationException e) {
			throw new ServiceOperationException(
					String.format("Service failed to delete Project: %s", e.getMessage()));
		}
	}
	
	// ==================================================================================
	// PRIVATE HELPERs
	// ==================================================================================
	
	private void validateSearchCriteria(ProjectQuery criteria) 
			throws ServiceInvalidInputException {
		if (criteria.getProjectName() == null
				|| criteria.getProjectNumber() < 0
				|| criteria.getCustomer() == null
				|| (criteria.getProjectName().isEmpty()
						&& criteria.getProjectNumber() == 0
						&& criteria.getCustomer().isEmpty())) {
			throw new ServiceInvalidInputException(
					"Invalid search criteria: %s" + criteria.toString());
		}
	}
	
	private void validateProject(Project project) throws ServiceInvalidInputException {
		
	}
}
