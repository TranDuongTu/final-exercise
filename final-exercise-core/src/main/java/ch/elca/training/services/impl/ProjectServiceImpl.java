package ch.elca.training.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import ch.elca.training.dom.Project;
import ch.elca.training.services.ProjectService;
import ch.elca.training.services.searching.ProjectSearchCriteria;

@Service(ProjectService.BEAN_NAME)
public class ProjectServiceImpl implements ProjectService {
	
	/**
	 * {@inheritDoc}
	 */
	public List<Project> searchProject(ProjectSearchCriteria criteria) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void updateProject(Project project) {
		// TODO Auto-generated method stub
		
	}
}
