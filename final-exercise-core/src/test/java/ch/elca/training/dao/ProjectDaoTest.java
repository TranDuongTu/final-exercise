package ch.elca.training.dao;

import java.util.List;

import org.junit.Test;

import ch.elca.training.dom.Project;

/**
 * Test {@link ProjectDao}
 * 
 * @author DTR
 */

public class ProjectDaoTest extends DaoTestBase {
	
	@Test
	public void shouldQueryTheSameAsDummyObjects() throws Exception {
		List<Project> projects = projectDao.getAll();
	}
}
