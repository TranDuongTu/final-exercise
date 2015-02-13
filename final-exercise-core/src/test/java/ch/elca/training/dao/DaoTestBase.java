package ch.elca.training.dao;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import ch.elca.training.dom.Project;
import ch.elca.training.utils.ProjectFactory;

/**
 * Base class prepares data for test methods.
 * 
 * @author DTR
 */
@TransactionConfiguration
@Transactional
public class DaoTestBase extends AbstractTransactionalJUnit4SpringContextTests {
	
	public static final int NO_DUMMY_PROJECTS = 5;
	
	@Autowired
	protected ProjectDao projectDao;
	
	protected List<Project> dummyProjects;
	
	@Before
	public void initTestData() throws Exception {
		dummyProjects = ProjectFactory.getRandomProjects(NO_DUMMY_PROJECTS);
		for (Project project : dummyProjects) {
			projectDao.saveOrUpdate(project);
		}
	}

	@After
	public void clearTestData() throws Exception {
		projectDao.deleteAll();
	}
	
	// ==================================================================================
	// PRIVATE HELPERs
	// ==================================================================================
	
	protected int getUnDuplicateNumber() {
		int max = Integer.MIN_VALUE;
		for (Project project : dummyProjects) {
			if (project.getNumber() > max) {
				max = project.getNumber();
			}
		}
		return max + 1;
	}
}
