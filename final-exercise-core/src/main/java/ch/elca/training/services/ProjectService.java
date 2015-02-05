package ch.elca.training.services;

import java.util.List;

import ch.elca.training.dom.Project;
import ch.elca.training.services.exceptions.ServiceInvalidInputException;
import ch.elca.training.services.exceptions.ServiceOperationException;
import ch.elca.training.services.searching.ProjectSearchCriteria;

/**
 * Services for working with {@link Project}s.
 * 
 * @author DTR
 */
public interface ProjectService extends Service {
	
	public static final String BEAN_NAME = "projectService";
	
	/**
	 * Search projects based on given criteria.
	 */
	List<Project> searchProject(ProjectSearchCriteria criteria) 
			throws ServiceInvalidInputException, ServiceOperationException;
	
	/**
	 * Update the modified project.
	 */
	void saveOrUpdateProject(Project project) 
			throws ServiceInvalidInputException, ServiceOperationException;
}
