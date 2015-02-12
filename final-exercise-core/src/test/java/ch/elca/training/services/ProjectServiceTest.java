package ch.elca.training.services;

import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ch.elca.training.dom.Project;
import ch.elca.training.dom.Status;
import ch.elca.training.services.searching.ProjectQuery;
import ch.elca.training.utils.ProjectFactory;

/**
 * Test ProjectService.
 * 
 * @author DTR
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:**/*test.xml"})
public class ProjectServiceTest {
	
	public static final int NO_DUMMY_PROJECTS = 100;
	
	@Autowired
	private ProjectService projectService;
	
	private List<Project> dummyProjects;
	
	@Before
	public void initTestData() throws Exception {
		dummyProjects = ProjectFactory.getRandomProjects(NO_DUMMY_PROJECTS);
		for (Project project : dummyProjects) {
			projectService.saveOrUpdateProject(project);
		}
	}

	@After
	public void clearTestData() throws Exception {
		for (Project project : dummyProjects) {
			projectService.deleteProject(project);
		}
	}
	
	@Test
	public void shouldReturnExactProjectWithNumber() throws Exception {
		Project experimentedProject = dummyProjects.get(0);
		
		ProjectQuery criteria = createCriteria(
				"", experimentedProject.getNumber(), "", null);
		
		List<Project> result = projectService.searchProject(criteria);
		Assert.assertEquals(1, result.size());
		Assert.assertEquals(experimentedProject.getNumber(), result.get(0).getNumber());
	}
	
	@Test
	public void shouldInsertNewProject() throws Exception {
		long id = getUnDuplicateId();
		Project testProject = ProjectFactory.createProject(
				0, (int) id, "Project Test", "Test Customer", 
				ProjectFactory.randomizeStatus(), new Date(), new Date());
		
		projectService.saveOrUpdateProject(testProject);
		
		List<Project> result = projectService.searchProject(
				createCriteria("", testProject.getNumber(), "", null));
		
		Assert.assertEquals(1, result.size());
		Assert.assertEquals(testProject.getNumber(), result.get(0).getNumber());
	}
	
	// ==================================================================================
	// PRIVATE HELPERs
	// ==================================================================================
	
	private ProjectQuery createCriteria(
			String name, int number, String customer, Status status) {
		ProjectQuery result = new ProjectQuery();
		result.setProjectName(name);
		result.setCustomer(customer);
		result.setProjectNumber(number);
		result.setProjectStatus(status);
		return result;
	}
	
	protected long getUnDuplicateId() {
		long max = Integer.MIN_VALUE;
		for (Project project : dummyProjects) {
			if (project.getId() > max) {
				max = project.getId();
			}
		}
		return max + 1;
	}
}
