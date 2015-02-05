package ch.elca.training.dao;

import java.util.List;

import ch.elca.training.dao.exceptions.DaoObjectNotFoundException;
import ch.elca.training.dao.exceptions.DaoOperationException;
import ch.elca.training.dom.Project;
import ch.elca.training.dom.Status;

/**
 * Specific actions for {@link Project} persistence objects.
 * 
 * @author DTR
 */
public interface ProjectDao extends GenericDao<Project> {
	
	/**
	 * Return {@link Project} with given number.
	 */
	Project getProjectByNumber(int number) throws DaoObjectNotFoundException, DaoOperationException;
	
	/**
	 * Return projects match name pattern.
	 */
	List<Project> findProjectsWithNamePattern(String name) throws DaoOperationException;
	
	/**
	 * Return projects match customer name pattern.
	 */
	List<Project> findProjectsWithCustomerPattern(String customer) throws DaoOperationException;
	
	/**
	 * Return projects with given {@link Status}.
	 */
	List<Project> findProjectsWithStatus(Status status) throws DaoOperationException;
	
	/**
	 * Return projects that match all given patterns.
	 */
	List<Project> findProjectsMatchPatterns(String name, String customer, Status status) 
			throws DaoOperationException;
}
