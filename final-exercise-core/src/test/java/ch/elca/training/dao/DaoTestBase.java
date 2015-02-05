package ch.elca.training.dao;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;

import ch.elca.training.dom.Project;

public class DaoTestBase {
	
	@Autowired
	private ProjectDao projectDao;
	
	@Autowired
	private List<Project> dummyProjects;
	
	@Before
	public void initTestData() throws Exception {
		projectDao.deleteAll();
		
		for (Project project : dummyProjects) {
			projectDao.saveOrUpdate(project);
		}
	}

	@After
	public void clearTestData() throws Exception {
		projectDao.deleteAll();
	}
}
