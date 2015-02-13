package ch.elca.training.services;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ch.elca.training.dao.ProjectDao;
import ch.elca.training.dom.Project;
import ch.elca.training.services.exceptions.ServiceDuplicateProjectException;
import ch.elca.training.services.exceptions.ServiceInvalidInputException;
import ch.elca.training.services.exceptions.ServiceOperationException;
import ch.elca.training.services.exceptions.ServiceProjectNotExistsException;
import ch.elca.training.services.searching.ProjectQuery;

/**
 * Services for working with {@link Project}s.
 * 
 * @author DTR
 */
@Transactional(rollbackFor = ServiceOperationException.class)
public interface ProjectService {

	public static final String BEAN_NAME = "projectService";
	
	/**
	 * Get ProjectDao.
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	ProjectDao getProjectDao();

	/**
	 * Count total of {@link Project} currently in database.
	 */
	int countProjects() throws ServiceOperationException;

	/**
	 * Get {@link Project} by Number.
	 * 
	 * @throws ServiceProjectNotExistsException
	 *             if cannot find one
	 * @return Project found (never return null or uninitialized Project).
	 */
	Project getProjectByNumber(int number)
			throws ServiceProjectNotExistsException, ServiceOperationException;

	/**
	 * Search projects based on given criteria.
	 * 
	 * @throws ServiceInvalidInputException
	 *             if the given query object is invalid
	 * @return List of Projects match (empty list if not found any)
	 */
	List<Project> searchProject(ProjectQuery criteria)
			throws ServiceInvalidInputException, ServiceOperationException;

	/**
	 * Count number of Projects that match given query object but not include
	 * paging information in query.
	 */
	int countProjectMatchExcludePaging(ProjectQuery projectQuery)
			throws ServiceOperationException;

	/**
	 * Update the given Project.
	 */
	void saveOrUpdateProject(Project project) throws ServiceInvalidInputException, 
			ServiceDuplicateProjectException, ServiceOperationException;

	/**
	 * Delete the given Project.
	 * 
	 * @throws ServiceProjectNotExistsException
	 *             if given Project not exists.
	 */
	void deleteProject(Project project)
			throws ServiceProjectNotExistsException, ServiceOperationException;

	/**
	 * Delete Project with Number.
	 * 
	 * @throws ServiceProjectNotExistsException
	 *             if given number not exists.
	 */
	void deleteProjectNumber(int number)
			throws ServiceProjectNotExistsException, ServiceOperationException;
}
