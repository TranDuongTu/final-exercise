package ch.elca.training.services;

import java.util.List;

import ch.elca.training.dom.Project;
import ch.elca.training.services.searching.ProjectSearchCriteria;

public interface ProjectService extends Service {
	public static final String BEAN_NAME = "projectService";
	
	/**
	 * Search projects based on given criteria.
	 */
	List<Project> searchProject(ProjectSearchCriteria criteria);
	
	/**
	 * Update the modified project.
	 */
	void updateProject(Project project);
}
