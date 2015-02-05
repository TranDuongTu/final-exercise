package ch.elca.training.services.searching;

import ch.elca.training.dom.Status;

/**
 * Supported criteria for searching projects.
 * 
 * @author DTR
 */
public class ProjectSearchCriteria {
	
	private int projectNumber;
	private String projectName;
	private String customer;
	private Status projectStatus;
	
	public int getProjectNumber() {
		return projectNumber;
	}
	public void setProjectNumber(int projectNumber) {
		this.projectNumber = projectNumber;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	public Status getProjectStatus() {
		return projectStatus;
	}
	public void setProjectStatus(Status projectStatus) {
		this.projectStatus = projectStatus;
	}
}
