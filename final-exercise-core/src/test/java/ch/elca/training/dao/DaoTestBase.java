package ch.elca.training.dao;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import ch.elca.training.dom.Project;

@TransactionConfiguration
@Transactional
public class DaoTestBase extends AbstractTransactionalJUnit4SpringContextTests {
	
	@Autowired
	protected ProjectDao projectDao;
	
	@Autowired
	protected List<Project> dummyProjects;
	
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
