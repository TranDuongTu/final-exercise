package ch.elca.training.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.elca.training.dao.ProjectDao;
import ch.elca.training.dom.Project;
import ch.elca.training.services.ProjectService;
import ch.elca.training.services.exceptions.ServiceInvalidInputException;
import ch.elca.training.services.exceptions.ServiceOperationException;
import ch.elca.training.services.searching.ProjectSearchCriteria;

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
	public List<Project> searchProject(ProjectSearchCriteria criteria) 
			throws ServiceInvalidInputException, ServiceOperationException {
		
		validateSearchCriteria(criteria);
		
		try {
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
	
	// ==================================================================================
	// PRIVATE HELPERs
	// ==================================================================================
	
	private void validateSearchCriteria(ProjectSearchCriteria criteria) 
			throws ServiceInvalidInputException {

	}
	
	private void validateProject(Project project) throws ServiceInvalidInputException {
		
	}
}
