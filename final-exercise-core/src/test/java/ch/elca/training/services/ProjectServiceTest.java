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

/**
 * Test ProjectService.
 * 
 * @author DTR
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:**/*test.xml"})
public class ProjectServiceTest {
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private List<Project> dummyProjects;
	
	@Before
	public void initData() throws Exception {
		for (Project project : dummyProjects) {
			projectService.saveOrUpdateProject(project);
		}
	}
	
	@After
	public void clearData() throws Exception {
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
		Project newProject = new Project();
		newProject.setCustomer("ABC");
		newProject.setEndDate(new Date());
		newProject.setName("DEF");
		newProject.setNumber(999);
		newProject.setStartDate(new Date());
		newProject.setStatus(Status.VAL);
		
		projectService.saveOrUpdateProject(newProject);
		
		List<Project> result = projectService.searchProject(
				createCriteria("", newProject.getNumber(), "", null));
		
		Assert.assertEquals(1, result.size());
		Assert.assertEquals(newProject.getNumber(), result.get(0).getNumber());
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
}
