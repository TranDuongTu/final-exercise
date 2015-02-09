package ch.elca.training.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import ch.elca.training.dom.Project;
import ch.elca.training.dom.Status;

/**
 * Factory of {@link Project}s. Used in Test classes.
 * 
 * @author DTR
 */
public abstract class ProjectFactory {
	
	private static final Random rand = new Random(System.currentTimeMillis());
	
	public static Project createProject(long id, int number, String name, 
			String customer, Status status, Date startDate, Date endDate) {
		Project result = new Project();
		result.setId(id);
		result.setName(name);
		result.setNumber(number);
		result.setCustomer(customer);
		result.setStartDate(startDate);
		result.setStatus(status);
		result.setEndDate(endDate);
		return result;
	}
	
	public static Status randomizeStatus() {
		switch (rand.nextInt(4)) {
		case 0: return Status.FIN;
		case 1: return Status.INV;
		case 2: return Status.TOV;
		case 3: return Status.VAL;
		}
		
		throw new RuntimeException();
	}
	
	public static List<Project> getRandomProjects(int n) {
		List<Project> result = new ArrayList<Project>();
		
		for (int i = 0; i < n; i++) {
			result.add(createProject(0, i, "Project " + i, "Customer " + i, 
					randomizeStatus(), new Date(), new Date()));
		}
		
		return result;
	}
}
