package ch.elca.training.dao;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

import ch.elca.training.dom.Project;
import ch.elca.training.dom.Status;
import ch.elca.training.utils.ProjectFactory;

/**
 * Test {@link ProjectDao}
 * 
 * @author DTR
 */
@ContextConfiguration(locations = {"classpath*:**/*test.xml"})
public class ProjectDaoTest extends DaoTestBase {
	
	/**
	 * Comparator that uses Project number to compare.
	 */
	private static final Comparator<Project> PROJECT_COMPARATOR = 
			new Comparator<Project>() {
		public int compare(Project o1, Project o2) {
			if (o1.getNumber() < o2.getNumber()) {
				return -1;
			} else if (o1.getNumber() == o2.getNumber()) {
				return 0;
			} else {
				return 1;
			}
		};
	};
	
	// ==================================================================================
	// TEST BASIC FUNCTIONs
	// ==================================================================================
	
	@Test
	public void testCount() throws Exception {
		int count = projectDao.count();
		Assert.assertEquals(dummyProjects.size(), count);
	}
	
	@Test
	public void shouldQueryTheSameAsDummyObjects() throws Exception {
		List<Project> projects = projectDao.getAll();
		Assert.assertEquals(dummyProjects.size(), projects.size());
		
		Set<Project> preProjects = new TreeSet<Project>(PROJECT_COMPARATOR);
		preProjects.addAll(dummyProjects);
		
		for (Project project : projects) {
			if (preProjects.contains(project)) {
				preProjects.remove(project);
			}
		}
		
		Assert.assertEquals(0, preProjects.size());
	}
	
	@Test
	public void deleteSomeProjectsShouldRemoveThem() throws Exception {
		List<Project> projects = projectDao.getAll();
		Assert.assertEquals(dummyProjects.size(), projects.size());
		
		for (Project toDeleteProject : projects) {
			projectDao.delete(toDeleteProject);
			
			List<Project> remainProjects = projectDao.getAll();
			for (Project project : remainProjects) {
				if (project.getNumber() == toDeleteProject.getNumber()) {
					Assert.assertTrue(false);
				}
			}
		}
	}
	
	@Test
	public void deleteAllShouldRemainNothing() throws Exception {
		projectDao.deleteAll();
		
		List<Project> projects = projectDao.getAll();
		Assert.assertEquals(0, projects.size());
	}
	
	@Test
	public void getProjectsByNamePatternOnExperimentedProject() throws Exception {
		Project experimentedProject = dummyProjects.get(0);
		String name = experimentedProject.getName();
		Set<Project> projects = new TreeSet<Project>(PROJECT_COMPARATOR);
		
		/* Pattern that match experimented project */
		String inclusivePattern = "%" + name.substring(
				name.length() - name.length() / 2);
		projects.addAll(projectDao.findProjectsWithNamePattern(inclusivePattern));
		Assert.assertTrue(projects.contains(experimentedProject));
		
		/* Pattern that not match experimented project */
		String exclusivePattern = name.substring(name.length() - name.length()/2) + "_";
		projects.clear();
		projects.addAll(projectDao.findProjectsWithNamePattern(exclusivePattern));
		Assert.assertTrue(!projects.contains(experimentedProject));
	}
	
	@Test
	public void getProjectsByCustomerPatternOnExperimentedProject() throws Exception {
		Project experimentedProject = dummyProjects.get(0);
		String customer = experimentedProject.getCustomer();
		Set<Project> projects = new TreeSet<Project>(PROJECT_COMPARATOR);
		
		/* Pattern that match experimented project */
		String inclusivePattern = "%" + customer.substring(
				customer.length() - customer.length() / 2);
		projects.addAll(projectDao.findProjectsWithCustomerPattern(inclusivePattern));
		Assert.assertTrue(projects.contains(experimentedProject));
		
		/* Pattern that not match experimented project */
		String exclusivePattern = customer.substring(customer.length() - customer.length()/2) + "_";
		projects.clear();
		projects.addAll(projectDao.findProjectsWithCustomerPattern(exclusivePattern));
		Assert.assertTrue(!projects.contains(experimentedProject));
	}
	
	@Test
	public void findProjectByStatus() throws Exception {
		for (Status status : Status.values()) {
			List<Project> projects = projectDao.findProjectsWithStatus(status);
			for (Project project : projects) {
				if (project.getStatus() != status) {
					Assert.assertTrue(false);
				}
			}
		}
	}
	
	@Test
	public void saveOrUpdateShouldPersistAProject() throws Exception {
		long id = getUnDuplicateId();
		Project testProject = ProjectFactory.createProject(
				id, (int) id, "Project Test", "Test Customer", 
				ProjectFactory.randomizeStatus(), new Date(), new Date());
		projectDao.saveOrUpdate(testProject);
		
		Assert.assertEquals(dummyProjects.size() + 1, projectDao.count());
		Project project = projectDao.get(id);
		Assert.assertEquals(testProject.getNumber(), project.getNumber());
		Assert.assertEquals(testProject.getName(), project.getName());
		Assert.assertEquals(testProject.getStatus(), project.getStatus());
		Assert.assertEquals(testProject.getCustomer(), project.getCustomer());
		
		/* Update a persistence object */
		project.setEndDate(null);
		project.setName(project.getName() + "XXX");
		projectDao.saveOrUpdate(project);
		Assert.assertEquals(dummyProjects.size() + 1, projectDao.count());
	}
}
