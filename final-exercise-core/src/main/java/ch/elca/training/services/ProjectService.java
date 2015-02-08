package ch.elca.training.services;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ch.elca.training.dom.Project;
import ch.elca.training.services.exceptions.ServiceInvalidInputException;
import ch.elca.training.services.exceptions.ServiceOperationException;
import ch.elca.training.services.searching.ProjectQuery;

/**
 * Services for working with {@link Project}s.
 * 
 * @author DTR
 */
@Transactional
public interface ProjectService {
	
	public static final String BEAN_NAME = "projectService";
	
	/**
	 * Search projects based on given criteria.
	 */
	List<Project> searchProject(ProjectQuery criteria) 
			throws ServiceInvalidInputException, ServiceOperationException;
	
	/**
	 * Update the modified project.
	 */
	void saveOrUpdateProject(Project project) 
			throws ServiceInvalidInputException, ServiceOperationException;
	
	/**
	 * Delete projects
	 */
	void deleteProject(Project project) throws ServiceOperationException;
}
