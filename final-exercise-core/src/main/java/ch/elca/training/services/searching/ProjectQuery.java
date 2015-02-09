package ch.elca.training.services.searching;

import ch.elca.training.dom.Status;

/**
 * Supported criteria for searching projects.
 * 
 * @author DTR
 */
public class ProjectQuery {
	
	/**
	 * Factory instance for getting default search criteria.
	 */
	private static ProjectQuery instance;
	
	public static ProjectQuery defaultCriteria() {
		if (instance == null) {
			instance = new ProjectQuery();
			instance.setCustomer("");
			instance.setProjectName("");
			instance.setProjectStatus(null);
		}
		
		return instance;
	}
	
	/**
	 * Object instance variables.
	 */
	public static final String PROPERTY_NUMBER = "projectNumber";
	private Integer projectNumber;
	
	public static final String PROPERTY_NAME = "projectName";
	private String projectName;
	
	public static final String PROPERTY_CUSTOMER = "customer";
	private String customer;
	
	public static final String PROPERTY_STATUS = "status";
	private Status projectStatus;
	
	public Integer getProjectNumber() {
		return projectNumber;
	}
	public void setProjectNumber(Integer projectNumber) {
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
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return String.format("{name: %s, number: %s, status: %s, customer: %s}",
				projectName, projectNumber, projectStatus, customer);
	}
}
